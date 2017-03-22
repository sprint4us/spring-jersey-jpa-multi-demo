package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoutryLanguageEndToEndTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	@SuppressWarnings("unchecked")
	public void testEndToEnd() {

		Country country = rest.postForObject("/demo/create/country",
				"Netherlands", Country.class, Collections.EMPTY_MAP);
		assertEquals("Netherlands", country.getName());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("id", String.valueOf(country.getId()));
		formData.add("l", "English");
		formData.add("p", "90");
		Country updatedCountry = rest.postForObject("/demo/update/country",
				new HttpEntity<>(formData, headers), Country.class,
				Collections.EMPTY_MAP);
		assertEquals(country.getId(), updatedCountry.getId());
		assertEquals(country.getName(), updatedCountry.getName());
		Language lang = updatedCountry.getLanguages().get(0);
		assertEquals("English", lang.getName());
		assertEquals(90, lang.getPercentage());

		String str = rest.exchange(
				UriComponentsBuilder.fromUriString("/demo/search/percentage")
						.queryParam("c", country.getName())
						.queryParam("l", lang.getName()).build().toUri(),
				HttpMethod.GET, null, String.class).getBody();
		assertEquals(String.format("%s has %d%% %s as foreign language.\n",
				country.getName(), lang.getPercentage(), lang.getName()), str);
	}
}

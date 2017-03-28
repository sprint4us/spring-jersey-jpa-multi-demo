package com.sprint4us.demo;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

// don't mess up "default" profile
@Profile("e2e")
@Component
@SuppressWarnings("unchecked")
public class CountryLanguageEndToEndBootTestImpl
		implements CountryLanguageEndToEndTestable {

	private final static String USER = "user";

	@Autowired
	private TestRestTemplate rest;

	@Value("${security.user.password}")
	private String password;

	@Override
	public Country createCountry() {

		Country country = secureRest().postForObject("/demo/create/country",
				"Netherlands", Country.class, Collections.EMPTY_MAP);

		return country;
	}

	@Override
	public Country updateCountry(Country country) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("id", String.valueOf(country.getId()));
		formData.add("l", "English");
		formData.add("p", "90");
		Country updatedCountry = secureRest().postForObject(
				"/demo/update/country", new HttpEntity<>(formData, headers),
				Country.class, Collections.EMPTY_MAP);

		return updatedCountry;
	}

	@Override
	public String searchPercentage(Country updatedCountry) {

		Language lang = updatedCountry.getLanguages().get(0);
		String str = secureRest().exchange(
				UriComponentsBuilder.fromUriString("/demo/search/percentage")
						.queryParam("c", updatedCountry.getName())
						.queryParam("l", lang.getName()).build().toUri(),
				HttpMethod.GET, null, String.class).getBody();

		return str;
	}

	private TestRestTemplate secureRest() {

		return rest.withBasicAuth(USER, password);
	}
}

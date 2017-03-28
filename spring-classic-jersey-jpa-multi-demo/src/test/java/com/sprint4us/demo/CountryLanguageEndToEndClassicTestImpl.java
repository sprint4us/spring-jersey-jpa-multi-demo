package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@Component
public class CountryLanguageEndToEndClassicTestImpl
		extends CountryLanguageJerseyTestBase
		implements CountryLanguageEndToEndTestable {

	@Override
	public Country createCountry() {

		// don't hesitate to call JerseyTest#setUp, when out of reach to override junit#setUp
		try {
			super.setUp();
		} catch (Exception e) {
			fail("can't setup Jersey test.");
		}

		Response resp = target("/demo/create/country").request()
				.post(Entity.entity("Netherlands", MediaType.TEXT_PLAIN));
		assertEquals(200, resp.getStatus());
		Country country = resp.readEntity(Country.class);

		return country;
	}

	@Override
	public Country updateCountry(Country country) {

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
		formData.add("id", String.valueOf(country.getId()));
		formData.add("l", "English");
		formData.add("p", "90");
		Response resp = target("/demo/update/country")
				.request(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON).post(Entity.form(formData));
		Country updatedCountry = resp.readEntity(Country.class);

		return updatedCountry;
	}

	@Override
	public String searchPercentage(Country updatedCountry) {

		Language lang = updatedCountry.getLanguages().get(0);
		Response resp = target("/demo/search/percentage")
				.queryParam("c", updatedCountry.getName())
				.queryParam("l", lang.getName()).request().get();
		String str = resp.readEntity(String.class);

		// pair with setUp
		try {
			super.tearDown();
		} catch (Exception e) {
			fail("can't tear down Jersey test.");
		}

		return str;
	}
}

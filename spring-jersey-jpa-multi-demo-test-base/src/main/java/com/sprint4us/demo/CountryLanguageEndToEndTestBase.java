package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

public abstract class CountryLanguageEndToEndTestBase {

	@Autowired
	private CountryLanguageEndToEndTestable endToEnd;

	@Test
	public void testEndToEnd() {

		Country country = endToEnd.createCountry();
		assertEquals("Netherlands", country.getName());

		Country updatedCountry = endToEnd.updateCountry(country);
		assertEquals(country.getId(), updatedCountry.getId());
		assertEquals(country.getName(), updatedCountry.getName());
		Language lang = updatedCountry.getLanguages().get(0);
		assertEquals("English", lang.getName());
		assertEquals(90, lang.getPercentage());

		String str = endToEnd.searchPercentage(updatedCountry);
		assertEquals(String.format("%s has %d%% %s as foreign language.\n",
				country.getName(), lang.getPercentage(), lang.getName()), str);
	}
}
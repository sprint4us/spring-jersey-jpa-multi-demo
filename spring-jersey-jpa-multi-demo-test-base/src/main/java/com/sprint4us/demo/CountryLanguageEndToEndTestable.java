package com.sprint4us.demo;

import com.sprint4us.demo.entity.Country;

public interface CountryLanguageEndToEndTestable {

	Country createCountry();

	Country updateCountry(Country country);

	String searchPercentage(Country updatedCountry);
}
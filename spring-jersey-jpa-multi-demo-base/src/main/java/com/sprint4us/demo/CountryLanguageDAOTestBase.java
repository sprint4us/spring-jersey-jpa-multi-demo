package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import com.sprint4us.demo.dao.CountryLanguageDAO;
import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

public class CountryLanguageDAOTestBase {

	@Autowired
	private CountryLanguageDAO service;

	/*
	 * According to https://en.wikipedia.org/wiki/Languages_of_{Country}, main
	 * foreign languages in some European countries exhibit below,
	 * 
	 * France: English 39%, Spanish 13%, German 8%, Italian 5%
	 * Germany: English 56%, French 15%, Russian 5%
	 * Italy: English 34%, French 16%, Spanish 11%, German 5%
	 * Spain: English 27%, French 12%, German 2%
	 * the_United_Kingdom: French 23%, German 9%, Spanish 8%
	 */

	public void testCreateOK() {

		String[] countryNames = {
				"France", "Germany", "Italy", "Spain", "UK" };
		String[][][] languageNamePercentages = {
				{
						{
								"English", "39" },
						{
								"Spanish", "13" },
						{
								"German", "8" },
						{
								"Italian", "5" } },
				{
						{
								"English", "56" },
						{
								"French", "15" },
						{
								"Russian", "5" } },
				{
						{
								"English", "34" },
						{
								"French", "16" },
						{
								"Spanish", "11" },
						{
								"German", "5" } },
				{
						{
								"English", "27" },
						{
								"French", "12" },
						{
								"German", "5" } },
				{
						{
								"French", "23" },
						{
								"German", "9" },
						{
								"Spanish", "8" } } };

		int expectedTotalCountries = countryNames.length;
		int expectedTotalLanguages = 0;

		for (int i = 0; i < expectedTotalCountries; i++) {
			Country country = new Country(countryNames[i]);
			service.create(country);

			for (String[] languageNamePercentage : languageNamePercentages[i]) {
				++expectedTotalLanguages;
				Language language = new Language(languageNamePercentage[0],
						Integer.valueOf(languageNamePercentage[1]));
				service.update(country, language);
			}
		}

		int actualTotalCountries = service.retrieveAllCountries().size();

		// check if E2E test beat the gun.
		if (service.findCountry(1L).getName().equals("Netherlands")) {
			++expectedTotalCountries;
			++expectedTotalLanguages;
		}

		assertEquals("The total countries are not equal",
				expectedTotalCountries, actualTotalCountries);

		int actualTotalLanguages = service.retrieveAllLanguages().size();
		assertEquals("The total languages are not equal",
				expectedTotalLanguages, actualTotalLanguages);
	}

	@Test(expected = JpaSystemException.class)
	public void testCreateOnException() {

		try {
			service.create(new Country("France"));
		} catch (JpaSystemException e) {
			if (e.contains(SQLIntegrityConstraintViolationException.class)) {
				throw e;
			}
		}
	}

	public void testSearchOK() {

		Country country = service.searchCountry("Spain");
		String acutalCountryName = country.getName();
		assertEquals("The country name is not equal", "Spain",
				acutalCountryName);

		int acutalNumberOfCountries = service.searchCountries("Spanish").size();
		assertEquals("The number of countries is not equal", 3,
				acutalNumberOfCountries);

		int actualPercentage = service.searchPercentage("Germany", "French");
		assertEquals("The percentage is not equal", 15, actualPercentage);
	}

	public void testUpdateOK() {

		// test anti sql injection
		int actualNumberOfUpdated = service
				.updatePercentage("German 'AND '1'='1", 99);
		assertEquals("Oops, sql injection penetrated", 0,
				actualNumberOfUpdated);

		actualNumberOfUpdated = service.updatePercentage("German", 99);
		assertEquals("The number of updated is not equal", 4,
				actualNumberOfUpdated);

		Country country = service.searchCountry("Italy");

		Language language = country.getLanguages().stream()
				.filter(l -> l.getName() == "German").findFirst().get();
		int actualPercentage = language.getPercentage();
		assertEquals("The percentage is not equal", 5, actualPercentage);

		service.syncToDB(country);
		actualPercentage = language.getPercentage();
		assertEquals("The percentage is not equal", 99, actualPercentage);
	}
}

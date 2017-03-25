package com.sprint4us.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@Repository
public class CountryLanguageDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Transactional
	public void create(Object obj) {

		em.persist(obj);
	}

	@Transactional
	public void update(Country country, Language language) {

		country.addLanguage(language);
		em.flush();
	}

	@Transactional
	public int updatePercentage(String languageName, int percentage) {

		int retVal = em
				.createQuery(
						"update Language l set l.percentage=:percent where l.name=:langName")
				.setParameter("percent", percentage)
				.setParameter("langName", languageName).executeUpdate();

		return retVal;
	}

	@Transactional
	public void syncToDB(Object obj) {

		em.refresh(obj);
	}

	public Country findCountry(Long id) {

		Country retVal = em.find(Country.class, id);

		return retVal;
	}

	public List<Country> retrieveAllCountries() {

		List<Country> retVal = null;
		try {
			retVal = em.createQuery("select c from Country c", Country.class)
					.getResultList();
		} catch (NoResultException e) {
			retVal = new ArrayList<Country>();
		}

		return retVal;
	}

	public List<Language> retrieveAllLanguages() {

		List<Language> retVal = null;
		try {
			retVal = em.createQuery("select l from Language l", Language.class)
					.getResultList();
		} catch (NoResultException e) {
			retVal = new ArrayList<Language>();
		}

		return retVal;
	}

	public Country searchCountry(String countryName) {

		Country retVal = null;
		try {
			retVal = em
					.createQuery("select c from Country c where c.name=:name",
							Country.class)
					.setParameter("name", countryName).getSingleResult();
		} catch (NoResultException e) {
			retVal = new Country();
		}

		return retVal;
	}

	public List<Country> searchCountries(String languageName) {

		List<Country> retVal = null;
		try {
			retVal = em
					.createQuery(
							"select c from Country c, Language l where l.name=:langName and l member of c.languages",
							Country.class)
					.setParameter("langName", languageName).getResultList();
		} catch (NoResultException e) {
			retVal = new ArrayList<Country>();
		}

		return retVal;
	}

	public int searchPercentage(String countryName, String languageName) {

		int retVal = 0;
		try {
			retVal = em
					.createQuery(
							"select l.percentage from Country c, Language l where c.name=:countryName and l.name=:langName and l member of c.languages",
							Integer.class)
					.setParameter("countryName", countryName)
					.setParameter("langName", languageName).getSingleResult();
		} catch (NoResultException e) {
		}

		return retVal;
	}
}

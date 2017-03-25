package com.sprint4us.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryLanguageDAOBootTest extends CountryLanguageDAOTestBase {

	@Test
	public void testCreateOK() {

		super.testCreateOK();
	}

	@Test(expected = RuntimeException.class)
	public void testCreateOnException() {

		super.testCreateOnException();
	}

	@Test
	public void testSearchOK() {

		super.testSearchOK();
	}

	@Test
	public void testUpdateOK() {

		super.testUpdateOK();
	}
}

package com.sprint4us.demo;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
		"/applicationContext.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryLanguageDAOClassicTest extends CountryLanguageDAOTestBase {

}
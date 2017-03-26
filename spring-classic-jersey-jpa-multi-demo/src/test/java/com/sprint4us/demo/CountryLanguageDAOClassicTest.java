package com.sprint4us.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
		"/applicationContext.xml" })
public class CountryLanguageDAOClassicTest extends CountryLanguageDAOTestBase {

}
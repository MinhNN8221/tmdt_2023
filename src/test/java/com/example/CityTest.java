package com.example;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.respository.InCityRes;
import com.example.respository.InCountryRes;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class CityTest {
    
	@Autowired
	private InCityRes city;
	
	@Autowired
	private InCountryRes country;
	
	@Test
	public void findByCountry() {
		city.findByCountry(country.findOneById(1)).forEach(
		   i -> System.out.println(i.getName())		 
		);
	}
}

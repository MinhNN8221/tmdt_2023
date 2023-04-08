package com.example.api.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CityEntity;
import com.example.respository.InCityRes;
import com.example.respository.InCountryRes;

@RestController
public class CityAPI {
	
	@Autowired
	private InCityRes city;
	
	@Autowired
	private InCountryRes country;
    
	@GetMapping(value = "/freshfood/api/city")
	public List<CityEntity> getcity(HttpServletRequest request) {
		Integer countryid = Integer.parseInt(request.getParameter("id"));
		List<CityEntity> c = new ArrayList<CityEntity>();
		for(CityEntity i : city.findByCountry(country.findOneById(countryid))) {
			  CityEntity e = new CityEntity(i.getId(), i.getName());
			  c.add(e);
		}
		return c;
	}
}

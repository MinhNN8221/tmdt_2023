package com.example.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.CityEntity;
import com.example.entity.CountryEntity;
import com.example.respository.InCityRes;
import com.example.service.InCityService;

@Service
public class CityService implements InCityService{

	@Autowired
	private InCityRes city;
	
	@Override
	public List<CityEntity> findByCountry(CountryEntity countryEntity) {
		// TODO Auto-generated method stub
		return city.findByCountry(countryEntity);
	}

	@Override
	public CityEntity findOneById(Integer id) {
		// TODO Auto-generated method stub
		return city.findOneById(id);
	}
	
	
   
}

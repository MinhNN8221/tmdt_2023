package com.example.service;

import java.util.List;

import com.example.entity.CityEntity;
import com.example.entity.CountryEntity;

public interface InCityService {
    
	List<CityEntity> findByCountry(CountryEntity countryEntity);
	CityEntity findOneById(Integer id);
}

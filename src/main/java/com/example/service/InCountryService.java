package com.example.service;

import java.util.List;

import com.example.entity.CountryEntity;

public interface InCountryService {
      
	List<CountryEntity> findAll();
	
	CountryEntity findOneById(Integer id);
}

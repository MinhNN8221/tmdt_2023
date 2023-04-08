package com.example.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.CountryEntity;
import com.example.respository.InCountryRes;
import com.example.service.InCountryService;

@Service
public class CountryService implements InCountryService {
    
	@Autowired
	private InCountryRes country;

	@Override
	public List<CountryEntity> findAll() {
		// TODO Auto-generated method stub
		return country.findAll();
	}

	@Override
	public CountryEntity findOneById(Integer id) {
		// TODO Auto-generated method stub
		return country.findOneById(id);
	}
	
	
}

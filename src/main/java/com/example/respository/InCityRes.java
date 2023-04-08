package com.example.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.CityEntity;
import com.example.entity.CountryEntity;

public interface InCityRes extends JpaRepository<CityEntity, Integer>{
	
	List<CityEntity> findByCountry(CountryEntity countryEntity);
	
	CityEntity findOneById(Integer id);
}

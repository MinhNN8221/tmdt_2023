package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.CountryEntity;

public interface InCountryRes extends JpaRepository<CountryEntity, Integer> {
    
	CountryEntity findOneById(Integer id);
}

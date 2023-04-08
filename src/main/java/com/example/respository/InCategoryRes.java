package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface InCategoryRes extends JpaRepository<CategoryEntity, Integer> {
	 CategoryEntity findOneById(Integer id);
}

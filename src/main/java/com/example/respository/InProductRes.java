package com.example.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.CategoryEntity;
import com.example.entity.ProductEntity;

public interface InProductRes extends JpaRepository<ProductEntity, Integer> {
	
	ProductEntity findOneById(Integer id);
	
	List<ProductEntity> findByCategory(CategoryEntity category);
	
	@Query("SELECT pro FROM ProductEntity pro WHERE pro.name LIKE %:search% OR pro.price LIKE %:search%")
	Page<ProductEntity> findAll(@Param("search") String search, Pageable pageable);
}

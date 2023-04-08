package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.CategoryEntity;

public interface InCategoryService {
	@Transactional
    void save(CategoryEntity categoryEntity);
    CategoryEntity findOneById(Integer id);
    List<CategoryEntity> findAll();
    void delete(Integer id);
    CategoryEntity update(Integer id, String newname);
}

package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.example.entity.CategoryEntity;
import com.example.entity.ProductEntity;

public interface InProductService {
     @Transactional
     ProductEntity save(ProductEntity productEntity);
     ProductEntity findOneById(Integer id);
     List<ProductEntity> findAll();
     List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
     Page<ProductEntity> findAll(int pagenumber, String search, String sortBy, String sortDir);
     void delete(Integer id);
}

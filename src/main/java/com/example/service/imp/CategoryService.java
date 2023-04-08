package com.example.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.CategoryEntity;
import com.example.respository.InCategoryRes;
import com.example.service.InCategoryService;

@Service
public class CategoryService implements InCategoryService{

	@Autowired
	private InCategoryRes category;
	
	@Override
	@Transactional
	public void save(CategoryEntity categoryEntity) {
		category.save(categoryEntity);
	}

	@Override
	public CategoryEntity findOneById(Integer id) {
		// TODO Auto-generated method stub
		return category.findOneById(id);
	}

	@Override
	public List<CategoryEntity> findAll() {
		// TODO Auto-generated method stub
		return category.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		category.deleteById(id);
	}

	@Override
	public CategoryEntity update(Integer id, String newname) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = category.findOneById(id);
		categoryEntity.setName(newname);
		return category.save(categoryEntity);
	}
    
}

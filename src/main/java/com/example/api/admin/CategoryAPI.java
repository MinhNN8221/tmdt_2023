package com.example.api.admin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CategoryEntity;
import com.example.service.InCategoryService;

@RestController
public class CategoryAPI {
      
	@Autowired
	private InCategoryService categoryService;
	
	@GetMapping(value = "/freshfood/category/getall")
	public List<CategoryEntity> findall(){
		return categoryService.findAll();
	}
	
	@PostMapping(value = "/freshfood/category/add")
	@Transactional
	public Integer themmoi(@RequestBody CategoryEntity cate) {
		categoryService.save(cate);;
		return cate.getId();
	}
	
	@DeleteMapping(value = "/freshfood/category/xoa/{id}")
	@Transactional
	public Integer xoa(@PathVariable("id") Integer id) {
		categoryService.delete(id);
		return id;
	}
	
	@PutMapping(value = "/freshfood/category/update")
	@Transactional
	public Integer capnhat(@RequestBody CategoryEntity cate) {
		categoryService.update(cate.getId(), cate.getName());
		return cate.getId();
	}
}

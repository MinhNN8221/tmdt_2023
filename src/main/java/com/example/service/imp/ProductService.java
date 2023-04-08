package com.example.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.CategoryEntity;
import com.example.entity.ProductEntity;
import com.example.respository.InProductRes;
import com.example.service.InProductService;

@Service
public class ProductService implements InProductService{

	@Autowired
	private InProductRes productres;
	
	@Override
	@Transactional
	public ProductEntity save(ProductEntity productEntity) {
		// TODO Auto-generated method stub
		return productres.save(productEntity);
	}

	@Override
	public ProductEntity findOneById(Integer id) {
		// TODO Auto-generated method stub
		return productres.findOneById(id);
	}

	@Override
	public List<ProductEntity> findAll() {
		// TODO Auto-generated method stub
		return productres.findAll();
	}

	@Override
	public List<ProductEntity> findByCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		return productres.findByCategory(categoryEntity);
	}

	@Override
	public Page<ProductEntity> findAll(int pagenumber,String search, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = null;
		if(sortBy.equals("creTime")) sort = Sort.by("createTime");
		else if(sortBy.equals("name")) sort = Sort.by("name");	
		else sort = Sort.by("price");
		sort = (sortDir.equals("asc")) ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pagenumber -1 , 12, sort);
		Page<ProductEntity> findall = (search == null) ? productres.findAll(pageable) : productres.findAll(search, pageable);
		return findall;
	}

	@Override
	public void delete(Integer id) {
		productres.deleteById(id);
	}
 
}

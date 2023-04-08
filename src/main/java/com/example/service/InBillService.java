package com.example.service;

import java.util.List;

import com.example.entity.BillEntity;

public interface InBillService {
   
	BillEntity save(BillEntity b);
	void delete(Integer id);
	List<BillEntity> findAll();
	BillEntity findOneById(Integer id);
}

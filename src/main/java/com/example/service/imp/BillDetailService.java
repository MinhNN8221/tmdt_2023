package com.example.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;
import com.example.respository.InBillDetailRes;
import com.example.service.InBillDetailService;

@Service
public class BillDetailService implements InBillDetailService{
	
	@Autowired
	private InBillDetailRes billdetail;

	@Override
	@Transactional
	public void save(BillDetailEntity billDetailEntity) {
		// TODO Auto-generated method stub
		billdetail.save(billDetailEntity);
	}

	@Override
	public List<BillDetailEntity> findByBill(BillEntity bill) {
		// TODO Auto-generated method stub
		return billdetail.findByBill(bill);
	}

	public List<BillDetailEntity> findByUser(UserEntity user){
		return billdetail.findByUser(user);
	}
}

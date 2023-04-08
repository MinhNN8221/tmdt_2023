package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.entity.BillEntity;
import com.example.respository.InBillDetailRes;
import com.example.respository.InBillRes;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BillDetailTest {
      
	@Autowired
	private InBillRes bill;
	
	@Autowired
	private InBillDetailRes billdetail;
	
	@Test
	public void findbybill() {
		BillEntity billEntity = bill.findOneById(10);
		System.out.println(billdetail.findByBill(billEntity).size());
	}
}

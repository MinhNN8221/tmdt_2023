package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.entity.BillEntity;
import com.example.entity.ProductEntity;
import com.example.respository.InBillRes;
import com.example.respository.InProductRes;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BillTest {
	@Autowired
	private InProductRes product;
	
	@Autowired
	private InBillRes bill;
	
	@Test
	@Rollback(false)
	public void addbill() {
		ProductEntity p1 = product.findOneById(2029);
		ProductEntity p2 = product.findOneById(2030);
        BillEntity b = new BillEntity();
        b.setFullname("linh");
        b.setCountry("vn");
        b.setEmail("user123@gmail.com");
        b.setProvince("hn");
        b.setTelephone("099999");
        bill.save(b);
        System.out.println("Yes");
	}
	
	@Test
	public void findallPro() {
		
	}
	
}

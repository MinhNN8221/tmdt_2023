package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;
import com.example.respository.InCartItemRes;
import com.example.respository.InProductRes;
import com.example.respository.InUserRes;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartItemTest {
    
	@Autowired
	private InCartItemRes cart;
	
	@Autowired
	private InUserRes user;
	
	@Autowired
	private InProductRes pro;
	
    @Test	
    @Rollback(false)
	public void savecart() {
		 UserEntity u = user.findOneByEmail("nguyenhoailinh2207@gmail.com");
		 ProductEntity p = pro.findOneById(2029);
		 
		 CartItemEntity cartItemEntity = new CartItemEntity();
		 cartItemEntity.setProduct(p);
		 cartItemEntity.setUser(u);
		 cartItemEntity.setQuantity(10);
		 cart.save(cartItemEntity);
	}
    
    @Test
    public void findbyproanduer() {
    	UserEntity u = user.findOneByEmail("nguyenhoailinh2207@gmail.com");
		ProductEntity p = pro.findOneById(2029);
		CartItemEntity cartItemEntity = cart.findOneByProductAndUser(p, u);
		if(cartItemEntity == null) {
			System.out.println("không có");
		}
    }
    
    @Test
    public void findOneById() {
    	System.out.println(cart.findOneById(1084).getProduct().getName());
    }
}

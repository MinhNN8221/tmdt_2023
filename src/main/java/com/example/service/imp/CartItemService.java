package com.example.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;
import com.example.respository.InCartItemRes;
import com.example.service.InCartItemService;

@Service
public class CartItemService implements InCartItemService {
	
	@Autowired
	private InCartItemRes cartitem;

	@Override
	public List<CartItemEntity> findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return cartitem.findByUser(user);
	}

	@Override
	@Transactional
	public CartItemEntity save(ProductEntity product, UserEntity user, Integer quantity) {
		// TODO Auto-generated method stub
		CartItemEntity oldcart = cartitem.findOneByProductAndUser(product, user);
		if(oldcart != null) {
			oldcart.setQuantity(quantity);
		}else {
			oldcart = new CartItemEntity();
			oldcart.setUser(user);
			oldcart.setProduct(product);
			oldcart.setQuantity(quantity);
		}
		return cartitem.save(oldcart);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		cartitem.deleteById(id);
	}

	@Override
	public CartItemEntity findOneById(Integer id) {
		// TODO Auto-generated method stub
		return cartitem.findOneById(id);
	}

	//thêm
	public CartItemEntity findIdByIdProductUser(Integer idPro, Integer idUser){
		return cartitem.findIdByIdProduct(idPro, idUser);
	}

	@Override
	@Transactional
	public void deleteByUser(Integer id) {
		// TODO Auto-generated method stub
		cartitem.deleteByUser(id);
	}
}

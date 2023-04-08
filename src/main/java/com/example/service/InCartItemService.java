package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;

public interface InCartItemService {
	
      List<CartItemEntity> findByUser(UserEntity userEntity);
      
      @Transactional
      CartItemEntity save(ProductEntity product, UserEntity user, Integer quantity);
      
      CartItemEntity findOneById(Integer id);

      CartItemEntity findIdByIdProductUser(Integer idPro, Integer idUser); //thêm

      @Transactional
      void delete(Integer id);
      
      void deleteByUser(Integer id);
}

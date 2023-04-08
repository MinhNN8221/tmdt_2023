package com.example.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;

public interface InCartItemRes extends JpaRepository<CartItemEntity, Integer> {
     
	List<CartItemEntity> findByUser(UserEntity user);
	
	CartItemEntity findOneByProductAndUser(ProductEntity product, UserEntity user);

	CartItemEntity findOneById(Integer id);

	@Query("SELECT c FROM CartItemEntity c WHERE c.product.id = :idPro and c.user.id = :idUser")//thêm
	CartItemEntity findIdByIdProduct(Integer idPro, Integer idUser);
	
	@Query("DELETE FROM CartItemEntity c WHERE c.user.id = :id")
	@Modifying
	void deleteByUser(Integer id);
}

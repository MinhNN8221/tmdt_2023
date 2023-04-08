package com.example;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.entity.CategoryEntity;
import com.example.respository.InCategoryRes;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryTest {
   
	@Autowired
	private InCategoryRes category;
	
	@Test
	@Rollback(false)
	public void createCategory() {
		CategoryEntity categoryEntity2 = new CategoryEntity("Hải sản tươi");
		CategoryEntity categoryEntity3 = new CategoryEntity("Hoa quả nhập khẩu");
		CategoryEntity categoryEntity4 = new CategoryEntity("Rau, củ sạch");
		CategoryEntity categoryEntity5 = new CategoryEntity("Thịt tươi");
		CategoryEntity categoryEntity6 = new CategoryEntity("Thực phẩm đông lạnh");
		CategoryEntity categoryEntity7 = new CategoryEntity("Trái cây tươi");
		CategoryEntity categoryEntity8 = new CategoryEntity("Đồ uống");
		category.save(categoryEntity2);
		category.save(categoryEntity3);
		category.save(categoryEntity4);
		category.save(categoryEntity5);
		category.save(categoryEntity6);
		category.save(categoryEntity7);
		category.save(categoryEntity8);
	}
	
	@Test
	public void findById() {
		System.out.println(category.findOneById(2).getName());
	}
}

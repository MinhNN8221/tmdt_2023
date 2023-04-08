package com.example.api.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.ProductEntity;
import com.example.service.InProductService;

@RestController
public class ProductAPI {
	
	@Autowired
	private InProductService proService;
          
	@PostMapping(value = "/freshfood/product/add")
	public void themmoi(@RequestBody ProductEntity pro,
			                           @RequestParam("main-img") MultipartFile mainmultipartFile,
                                       @RequestParam("ex-img") MultipartFile[] extramultipartFiles,
                                       HttpServletRequest request){
         System.out.println(pro.getName()+" "+pro.getPrice());
	}
	
	@DeleteMapping("/freshfood/product/xoa/{id}")
	public String xoa(@PathVariable("id") Integer id) {
		proService.delete(id);
		return id.toString();
	}
}

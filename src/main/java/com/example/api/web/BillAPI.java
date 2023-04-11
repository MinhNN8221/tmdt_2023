package com.example.api.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;
import com.example.entity.BillForm;
import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;
import com.example.service.InBillDetailService;
import com.example.service.InBillService;
import com.example.service.InCartItemService;
import com.example.service.InCityService;
import com.example.service.InCountryService;
import com.example.service.InProductService;
import com.example.service.InUserService;

@RestController
public class BillAPI {
    
	@Autowired
	private InCountryService countryService;
	
	@Autowired
	private InCityService cityService;
	
	@Autowired
	private InUserService userservice;
	
	@Autowired
	private InCartItemService cartitemservice;
	
	@Autowired
	private InBillService billService;
	
	@Autowired
	private InBillDetailService billdetailService;
	
	@Autowired
	private InProductService productService;
	
	
	@PostMapping(value = "/freshfood/bill/add")
	public void add(@RequestBody BillForm billForm, HttpServletRequest request) {
		BillEntity b = new BillEntity();
		b.setFullname(billForm.getFirstname());
		b.setCountry(countryService.findOneById(billForm.getCountry_id()).getName());
		b.setProvince(cityService.findOneById(Integer.valueOf(billForm.getZone_id())).getName());
		b.setTelephone(billForm.getTelephone());
		b.setEmail(billForm.getEmail());
		b.setWard(billForm.getWard_id());
		b.setMess(billForm.getComment());
		b.setShipaddress(billForm.getAddress_1());
		b.setCreate_time(new Date());
		
	    BillEntity newbill = billService.save(b);
		
		UserEntity user = userservice.getLoggingInUsser();
		if (user != null) {
			List<CartItemEntity> cart = cartitemservice.findByUser(user);
			for (CartItemEntity c: cart) {
				BillDetailEntity billDetail = new BillDetailEntity();
				billDetail.setPro_id(c.getProduct().getId());
				billDetail.setBill(newbill);
				billDetail.setQuantity(c.getQuantity());
				billDetail.setStatus("Chưa giao");
				billDetail.setUser(user);
				billdetailService.save(billDetail);

				//thêm
				ProductEntity product=c.getProduct();
				product.setQuantity(product.getQuantity()-c.getQuantity());
				productService.save(product);
			}
			cartitemservice.deleteByUser(user.getId());
		}else {
			HttpSession session = request.getSession();
			Map<Integer, Integer> cart = (Map<Integer, Integer>)session.getAttribute("cart");
			for (Entry<Integer, Integer> e: cart.entrySet()) {
				BillDetailEntity billDetail = new BillDetailEntity();
				billDetail.setPro_id(productService.findOneById(e.getKey()).getId());
				billDetail.setBill(newbill);
				billDetail.setQuantity(e.getValue());
				billDetail.setStatus("Chưa giao");
				billDetail.setUser(null);
				billdetailService.save(billDetail);

				ProductEntity product=productService.findOneById(e.getKey());
				product.setQuantity(product.getQuantity()-e.getValue());
				productService.save(product);
			}
			session.removeAttribute("cart");
		}

	}
	
	@GetMapping(value = "/freshfood/bill/all")
	public List<Map<String, String>> findall(){
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();
		for (BillEntity b: billService.findAll()) {
			Map<String, String> billitem = new LinkedHashMap<String, String>();
			billitem.put("name", b.getFullname());
			billitem.put("phone", b.getTelephone());
			billitem.put("bill-id", b.getId()+"");
			List<BillDetailEntity> billitems =  billdetailService.findByBill(b);
			long tongtien = 0l;
            for (BillDetailEntity i: billitems) {
				tongtien += productService.findOneById(i.getPro_id()).getPrice() * i.getQuantity();
			}			
			billitem.put("tongtien", tongtien+"");
			billitem.put("solg", billitems.size()+"");
			billitem.put("time", new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(b.getCreate_time()));
			maps.add(billitem);
		}
		return maps;
	}
	
	@GetMapping(value = "/freshfood/billitem/{billid}")
	public List<Map<String, String>> findallbillitem(@PathVariable("billid") Integer billid){
		List<Map<String, String>> m = new ArrayList<Map<String,String>>();
		List<BillDetailEntity> billitems =  billdetailService.findByBill(billService.findOneById(billid));
		for (BillDetailEntity bitem : billitems) {
			Map<String, String> a = new LinkedHashMap<String, String>();
			ProductEntity p = productService.findOneById(bitem.getPro_id());
			a.put("image", p.getProductExtraImagePath1());
			a.put("name",p.getName());
			a.put("price", p.getPrice()+"");
			a.put("solg", bitem.getQuantity()+"");
			a.put("tonggia", (bitem.getQuantity() * p.getPrice())+"");
			a.put("status", bitem.getStatus());
			m.add(a);
		}
		return m;
	}
}

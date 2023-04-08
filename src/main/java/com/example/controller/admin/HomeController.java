package com.example.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.ProductEntity;
import com.example.service.InBillService;
import com.example.service.InCategoryService;
import com.example.service.InProductService;
import com.example.utils.FileUploadUtils;

@Controller(value = "Controller_Of_Admin")
@RequestMapping(value = "/freshfood")
public class HomeController {

	@Autowired
	private InProductService productservice;
	
	@Autowired
	private InCategoryService categoryservice;
	
	@Autowired
	private InBillService billservice;
	
	
	@RequestMapping(value = "/admin/trang-chu", method = RequestMethod.GET)
	public ModelAndView trangchu() {
		ModelAndView mv = new ModelAndView("admin/trang-chu-admin");
		return mv;
	}
	
	@GetMapping(value = "/admin/san-pham")
	public String sanpham(Model model, 
			              @RequestParam(name = "page", required = false) Integer pagenumber, 
			              @RequestParam(name = "search", required = false) String search) {
		int curentpage = (pagenumber == null) ? 1: pagenumber;
		Page<ProductEntity> pages = productservice.findAll(curentpage, search, "creTime", "asc");
		List<ProductEntity> productlist = pages.getContent();
		model.addAttribute("productList", productlist);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("currentPage", curentpage);        
        return "admin/san-pham";
	}
	
	@RequestMapping(value = "/admin/them-moi", method = RequestMethod.GET)
	public ModelAndView themmoi(@RequestParam(value = "id", required = false) Integer id) {
		ModelAndView mv = new ModelAndView("admin/them-moi");
		ProductEntity product = (id != null) ? productservice.findOneById(id) : new ProductEntity();
		mv.addObject("product", product);
		mv.addObject("category", categoryservice.findAll());
		return mv;
	}
	
	@RequestMapping(value = "/admin/them-moi", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute("product") ProductEntity productEntity, 
			                        @RequestParam("main-img") MultipartFile mainmultipartFile,
			                        @RequestParam("ex-img") MultipartFile[] extramultipartFiles,
			                        HttpServletRequest request) throws IOException {
		ModelAndView mv = new ModelAndView("redirect:/freshfood/admin/san-pham");
		ProductEntity newProduct = null;
		
		Integer id = request.getParameter("id").equals("") ? null: Integer.valueOf(request.getParameter("id"));
				
		if(id != null) {
			newProduct = productservice.findOneById(id);
		}else {
			newProduct = new ProductEntity();
		}
		
		newProduct.setCreateTime(new Date());
		newProduct.setName(productEntity.getName());
		newProduct.setPrice(productEntity.getPrice());
		newProduct.setQuantity(productEntity.getQuantity());
	    
		String mainIngName = StringUtils.cleanPath(mainmultipartFile.getOriginalFilename()); //Lấy tên file ảnh đc gửi về
	    if (!mainIngName.equals("")) {
			newProduct.setImg(mainIngName);
		}
	    
	    int c = 1;
		for(MultipartFile m : extramultipartFiles) {
			String extraImgName = StringUtils.cleanPath(m.getOriginalFilename());
            if (!extraImgName.equals("")) {
            	if(c == 1) newProduct.setExtra_img1(extraImgName);
                else newProduct.setExtra_img2(extraImgName);
            }
            c++;
		}
		
		newProduct.setCategory(categoryservice.findOneById(Integer.parseInt(request.getParameter("category"))));
		ProductEntity savedProduct = productservice.save(newProduct);
		//tạo thư mục chứa ảnh
		String uploadDir = "./image/san-pham/"+savedProduct.getId();
		//tạo đối tượng path chứ đg dẫn trong uploadDir
		if (!mainIngName.equals("")) {
			FileUploadUtils.saveFile(mainmultipartFile, mainIngName, uploadDir);
		}
		
		for(MultipartFile m : extramultipartFiles) {
			String extraImgName = StringUtils.cleanPath(m.getOriginalFilename());
			if (!extraImgName.equals("")) {
				FileUploadUtils.saveFile(m,extraImgName,uploadDir);
			}
		}
		
		return mv;
	}
	
	@GetMapping(value = "/admin/danh-muc")
	public String category() {
		return "/admin/danh-muc-admin";
	}
	
	@GetMapping(value = "/admin/don-hang")
	public String bill() {
		return "/admin/don-hang";
	}
	
	@GetMapping(value = "/admin/don-hang-chi-tiet")
	public String billdetail(@RequestParam("id") Integer id, Model model) {
		model.addAttribute("bill", billservice.findOneById(id));
		return "/admin/chi-tiet-don-hang";
	}
}

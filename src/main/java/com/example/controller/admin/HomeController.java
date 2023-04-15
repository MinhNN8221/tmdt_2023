package com.example.controller.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;
import com.example.service.InBillDetailService;
import com.lowagie.text.DocumentException;
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

	@Autowired
	private InBillDetailService billDetailService;
	
	
	@RequestMapping(value = "/admin/trang-chu", method = RequestMethod.GET)
	public ModelAndView trangchu() throws Exception{
		ModelAndView mv = new ModelAndView("admin/trang-chu-admin");
		DecimalFormat formatter = new DecimalFormat("#,### đ");

		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date start = smf.parse("01/01/"+2023+" 00:00:00");
		Date end = smf.parse("31/12/"+2023+" 23:59:59");
		List<Float> datalines=billDetailService.getChartData(start, end);
		List<Integer> pieData=billDetailService.getPieChartData(start, end);

		float[] dataTNY=billDetailService.getChartDataTNY(start, end);
		float percent=(dataTNY[0]/dataTNY[1])*100;
		String dataP=percent>100?"+"+df.format(percent):"-"+df.format(Math.abs(100-percent));
		mv.addObject("datalines", datalines);
		mv.addObject("dataToday", formatter.format(dataTNY[0]));
		mv.addObject("percent", dataP+"%");
		mv.addObject("datapies", pieData);
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

	@GetMapping(value = "/admin/don-hang-confirm")
	public String confirm(@RequestParam("id") String id, Model model) {
		String[] str=id.split("/");
		int id_bill=Integer.valueOf(str[0]);
		int indexBillDetail=Integer.valueOf(str[1]);
		model.addAttribute("bill", billservice.findOneById(id_bill));
		List<BillDetailEntity> billDetailEntities=billDetailService.findByBill(billservice.findOneById(id_bill));
		billDetailEntities.get(indexBillDetail).setStatus("Đang giao");
		billDetailService.save(billDetailEntities.get(indexBillDetail));
		return "/admin/chi-tiet-don-hang.html";
	}

	@GetMapping("/admin/don-hang-export")
	public void exportToPDF(@RequestParam("id") Integer id, HttpServletResponse response) throws DocumentException, IOException, org.dom4j.DocumentException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=bill_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		BillEntity bill=billservice.findOneById(id);
		List<ProductEntity> productEntities=new ArrayList<>();
		for(BillDetailEntity billDetail:bill.getBilldetailList()){
			productEntities.add(productservice.findOneById(billDetail.getPro_id()));
		}
		FileExport exporter=new FileExport();
		exporter.export(bill, productEntities, response);

	}
}

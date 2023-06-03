package com.example.controller.web;

import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.config.VNPayService;
import com.example.entity.BillDetailEntity;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.CartItemEntity;
import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;

@Controller(value = "Controller_Of_Web")
@RequestMapping("/freshfood")
public class HomeController {

	@Autowired
	private VNPayService vnPayService;
	@Autowired
	private InCategoryService category;
	
	@Autowired
	private InCountryService country;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Autowired
	private InProductService product;
	
	@Autowired
	private InUserService userservice;

	@Autowired
	private InProductService productService;
	@Autowired
	private InBillDetailService billDetailService;
	@Autowired
	private InRoleService role;
	
	@Autowired
	private InCartItemService cartservice;
	
    @RequestMapping(value = "/trang-chu",method = RequestMethod.GET)
    public ModelAndView trangchu() {
    	ModelAndView mv = new ModelAndView("web/trang-chu");
    	mv.addObject("category", category.findAll());
    	mv.addObject("hoaQuaNhapKhau", product.findByCategory(category.findOneById(4)));
    	mv.addObject("rauCuSach", product.findByCategory(category.findOneById(5)));
    	mv.addObject("traiCayTuoi", product.findByCategory(category.findOneById(8)));
    	mv.addObject("haiSanTuoi", product.findByCategory(category.findOneById(3)));
    	mv.addObject("thitTuoi", product.findByCategory(category.findOneById(6)));
    	mv.addObject("doUong", product.findByCategory(category.findOneById(9)));
    	return mv;
    }
    @RequestMapping(value = "/gioi-thieu",method = RequestMethod.GET)
    public ModelAndView gioithieu() {
    	ModelAndView mv = new ModelAndView("web/gioi-thieu");
    	return mv;
    }
    
    @RequestMapping(value = "/san-pham",method = RequestMethod.GET)
    public ModelAndView sanpham(HttpServletRequest request) {
    	ModelAndView mv = new ModelAndView("web/san-pham");
 	    String search = (request.getParameter("search") == null) ? null : request.getParameter("search");
 	    int currentPage = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));
 	    String sortBy = (request.getParameter("sort") == null)? "creTime" : request.getParameter("sort");
 	    String sortDir = (request.getParameter("sortdir") == null) ? "asc" : request.getParameter("sortdir");
        Integer cateId = (request.getParameter("id") == null) ? null : Integer.parseInt(request.getParameter("id"));
 	    
 	    List<ProductEntity> productList = new ArrayList<ProductEntity>();
        Page<ProductEntity> pages = product.findAll(currentPage, search, sortBy, sortDir);
        if (cateId == null) {
            productList = pages.getContent();
		}else {
	        productList = product.findByCategory(category.findOneById(cateId));
	        mv.addObject("cateName", category.findOneById(cateId).getName());
		}
        
        int totalPages = pages.getTotalPages();
        long totalItems = pages.getTotalElements();
        
        String sortName = "";
        if(sortBy.equals("creTime")) sortName = "Mặc định";
        else if(sortBy.equals("name") && sortDir.equals("asc")) sortName = "Tên (A-Z)";
        else if(sortBy.equals("name") && sortDir.equals("desc")) sortName = "Tên (Z-A)";
        else if(sortBy.equals("price") && sortDir.equals("asc")) sortName = "Giá (Thấp-Cao)";
        else if(sortBy.equals("price") && sortDir.equals("desc")) sortName = "Giá (Cao-Thấp)";


        mv.addObject("search", search);
        mv.addObject("sortBy", sortBy);
        mv.addObject("sortDir", sortDir);
        mv.addObject("sortName", sortName);
        mv.addObject("totalItems", totalItems);
        mv.addObject("totalPages", totalPages);
        mv.addObject("currentPage", currentPage);
    	mv.addObject("productList", productList);
    	mv.addObject("category", category.findAll());
    	return mv;
    }
    
    @RequestMapping(value = "/dang-ki", method = RequestMethod.GET)
    public ModelAndView register() {
    	ModelAndView mv = new ModelAndView("web/dang-ki");
    	UserEntity user = new UserEntity();
    	mv.addObject("user", user);
    	mv.addObject("country", country.findAll());
    	return mv;
    }
   
    @RequestMapping(value = "/dang-nhap", method = RequestMethod.GET)
    public ModelAndView login() {
    	ModelAndView mv = new ModelAndView("web/dang-nhap");
    	mv.addObject("user", new UserEntity());
    	return mv;
    }
    
    @RequestMapping(value = "/dang-ki", method = RequestMethod.POST)
    public ModelAndView userSaving(@Valid @ModelAttribute("user") UserEntity user, BindingResult bindingResult, HttpServletRequest request){
    	ModelAndView mv = null;
    	int c = 0;
    	if(userservice.isEmailExist(user.getEmail()) == true) {
    		bindingResult.addError(new FieldError("user", "email", "email đã tồn tại !"));
    	}
    	if(!user.getPassword().equals(request.getParameter("confirm"))) {
    		bindingResult.addError(new FieldError("user","password", "password không khớp !"));
    	}
    	if(request.getParameter("country_id").equals("")) {
    		bindingResult.addError(new FieldError("user","country", "Vui lòng chọn quốc gia !"));
    	}
    	if(request.getParameter("zone_id").equals("")) {
    		bindingResult.addError(new FieldError("user","city", "Vui lòng chọn thành phố !"));
    	}
    		
    	if(bindingResult.hasErrors()) {
    		c++;
    		mv = new ModelAndView("web/dang-ki");
    		if(request.getParameter("agree") == null) {
    		   mv.addObject("alert", "Bạn phải đồng ý với chính sách bảo mật");
    		}
    		mv.addObject("country", country.findAll());
    		mv.addObject("user", user);
    	}
    	else {
    		if(request.getParameter("agree") == null) {
    			c++;
    			mv = new ModelAndView("web/dang-ki");
        		mv.addObject("country", country.findAll());
        		mv.addObject("user", user);
    			mv.addObject("alert", "Bạn phải đồng ý với chính sách bảo mật");
    		}else {
    		    mv = new ModelAndView("redirect:/freshfood/dang-nhap");
    		    mv.addObject("user",user);
    		}
    	}
    	if(c == 0) {
    		user.setPassword(passwordencoder.encode(user.getPassword()));
       	    user.setCity(request.getParameter("zone_id"));
    	    user.setCountry(country.findOneById(Integer.parseInt(request.getParameter("country_id"))));
    	    user.setStatus(1);
    	    user.getRoles().add(role.findOneByName("USER"));
    	    role.findOneByName("USER").getUsers().add(user);
    	    userservice.save(user);
    	}
    	return mv;
    }
    
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ModelAndView checkProduct(@RequestParam("id") Integer id){
         ModelAndView mv = new ModelAndView("web/check");
         ProductEntity p = product.findOneById(id);
         List<ProductEntity> splq = product.findByCategory(p.getCategory());
         mv.addObject("product", p);
         mv.addObject("splq",splq);
         mv.addObject("user", userservice.getLoggingInUsser());
         return mv;
    }
    
    @RequestMapping(value = "/cartitems", method = RequestMethod.GET)
    public ModelAndView allCartItem(HttpServletRequest request) {
    	UserEntity user = userservice.getLoggingInUsser();
    	List<CartItemEntity> cartlist = new ArrayList<CartItemEntity>();
    	long tongtien = 0;
    	if(user != null) {
    		cartlist = cartservice.findByUser(user);
    	}else {
    		HttpSession session = request.getSession();
    		Map<Integer, Integer> cart = (Map<Integer, Integer>)session.getAttribute("cart");
    		if (cart == null) {
				cart = new LinkedHashMap<Integer, Integer>();
			}
    		int id = 0;
    		for (Entry<Integer, Integer> item: cart.entrySet()) {
				CartItemEntity c = new CartItemEntity();
				c.setProduct(product.findOneById(item.getKey()));
				c.setQuantity(item.getValue());
				c.setUser(user);
				c.setId(id);
				cartlist.add(c);
				id++;
			}
    	}
    	for (CartItemEntity itemEntity : cartlist){
    		tongtien += itemEntity.getProduct().getPrice() * itemEntity.getQuantity();
		}
    	ModelAndView mv = new ModelAndView("web/cartitems");
    	mv.addObject("cartitems", cartlist);
    	mv.addObject("tongtien",tongtien);
    	return mv;
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkout() {
    	ModelAndView mv = new ModelAndView("web/checkout");
    	//mv.addObject("username", userservice.getLoggingInUsser().getFullname());
    	UserEntity userEntity = userservice.getLoggingInUsser();
    	if (userEntity != null) {
			mv.addObject("username", userEntity.getFullname());
		}
    	return mv;
    }
    
    @GetMapping(value = "/thanh-toan")
    public String thanhtoan(Model model){
    	model.addAttribute("country", country.findAll());
    	return "web/thanh-toan";
    }
    
    @GetMapping(value = "/profile")
    public String profile(Model model) {
    	UserEntity user = userservice.getLoggingInUsser();
    	model.addAttribute("user", user);
    	return "web/profile";
    }
    
    @GetMapping(value = "/lien-he")
    public String contact(Model model) {
    	return "web/lien-he";
    }
    
    @GetMapping(value = "/res")
    public ResponseEntity<String> resp0nse() {
    	return ResponseEntity.ok().body("linh");
    }

	//them reset password qua email
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm() {
		return "web/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPasswordForm(@RequestParam("email") String email) {
		String resetToken = UUID.randomUUID().toString();
		userservice.sendResetPasswordEmail(email, resetToken);
		return "redirect:/freshfood/forgot-password?success";
	}

	@GetMapping("/reset-password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		UserEntity user = userservice.findByToken(token);
		if (user == null) {
			return "redirect:/freshfood/forgot-password?error";
		}
		model.addAttribute("token", token);
		System.out.println(token);
		return "web/reset-password";
	}

	@PostMapping("/reset-password")
	public String processResetPasswordForm(HttpServletRequest request) {
		String token = request.getParameter("token");
		String newPassword = request.getParameter("password");
		userservice.resetPassword(token, newPassword);
		System.out.println(token);
		System.out.println(newPassword);
		return "redirect:/freshfood/dang-nhap?resetSuccess";
	}

	@RequestMapping(value = "/trang-thai", method = RequestMethod.GET)
	public ModelAndView status(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("web/trang-thai-don-hang");
		UserEntity user = userservice.getLoggingInUsser();

		List<BillDetailEntity> billDetails = billDetailService.findByUser(user);
		List<ProductEntity> products = new ArrayList<>();
		for (BillDetailEntity billDetail : billDetails) {
			products.add(productService.findOneById(billDetail.getPro_id()));
			products.get(products.size() - 1).setQuantity(billDetail.getQuantity());
		}
		if (request.getParameter("id") != null) {
			int id = Integer.valueOf(request.getParameter("id"));
			billDetails.get(id).setStatus("Đã giao");
			billDetailService.save(billDetails.get(id));
		}
		mv.addObject("productList", products);
		mv.addObject("bill", billDetails);
		return mv;
	}
	@GetMapping ("/submitOrder")
	public String submidOrder(HttpServletRequest request){
		UserEntity user = userservice.getLoggingInUsser();
		List<CartItemEntity> cartlist = new ArrayList<CartItemEntity>();

		long tongtien = 0;
		if(user != null) {
			cartlist = cartservice.findByUser(user);
			for(CartItemEntity p: cartlist){
				System.out.println(p.getProduct().getName());
			}
		}else {
			HttpSession session = request.getSession();
			Map<Integer, Integer> cart = (Map<Integer, Integer>)session.getAttribute("cart");
			if (cart == null) {
				cart = new LinkedHashMap<Integer, Integer>();
			}
			int id = 0;
			for (Entry<Integer, Integer> item: cart.entrySet()) {
				CartItemEntity c = new CartItemEntity();
				c.setProduct(product.findOneById(item.getKey()));
				c.setQuantity(item.getValue() + cart.get(item.getKey()));
				c.setUser(user);
				c.setId(id);
				cartlist.add(c);
				id++;
			}
		}
		for (CartItemEntity itemEntity : cartlist){
			tongtien += itemEntity.getProduct().getPrice() * itemEntity.getQuantity();
		}
		String orderInfo="DH"+(int)(Math.random() *1001 + 1);
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/freshfood";
		String vnpayUrl = vnPayService.createOrder(tongtien, orderInfo, baseUrl);
		return "redirect:" + vnpayUrl;
	}

	@GetMapping ("/vnpay-payment")
	public String payment(HttpServletRequest request, Model model){
		int paymentStatus =vnPayService.orderReturn(request);
		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");

		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("paymentTime", paymentTime);
		model.addAttribute("transactionId", transactionId);

		return paymentStatus == 1 ? "web/ordersuccess" : "web/orderfail";
	}

}

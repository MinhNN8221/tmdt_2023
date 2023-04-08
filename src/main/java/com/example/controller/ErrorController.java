package com.example.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

	private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
   
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		String errorPage = "error";
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				errorPage = "error/404";
				//Hiện lỗi ở Console
				log.error("404 Not Found");
			}
			else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorPage = "error/500";
				//Hiện lỗi ở Console
				log.error("500 Internal server error");
			}else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				errorPage = "error/403";
				//Hiện lỗi ở Console
				log.error("403 Forbidden");
			}
		}
		return errorPage;
	}
}

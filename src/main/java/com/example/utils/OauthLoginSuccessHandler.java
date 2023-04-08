package com.example.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.entity.AuthProvider;
import com.example.entity.UserEntity;
import com.example.service.InUserService;

@Component
public class OauthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	private InUserService userservice;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		OauthUserPrincipal oauthuser = (OauthUserPrincipal) authentication.getPrincipal();
		String email = oauthuser.getEmail();
		String name = oauthuser.getName();
		UserEntity user = userservice.findOneByEmail(email);
		if (user == null) {
			System.out.println("Chưa có");
		}else {
		    userservice.updateUser(email, name, AuthProvider.GOOGLE);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String targeturl = "/freshfood/trang-chu";
		if (response.isCommitted()) {
			return;
		}
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, targeturl);
	}
       
}

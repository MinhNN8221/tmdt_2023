package com.example.utils;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.service.InUserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
 
	@Autowired
	private InUserService userservice;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		String email = user.getEmail();
		String name = user.getUsername();
//		if (user.getauthprovider().equals(AuthProvider.GOOGLE)) {
//			userservice.updateUser(email, name, AuthProvider.LOCAL);
//		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String urltarget = getUrl(authentication);
		if (response.isCommitted()) {
			return;
		}
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, urltarget);
	}
	
	public String getUrl(Authentication authentication) {
		String url = "/freshfood/dang-nhap?error";
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority i: authorities) {
			if(i.getAuthority().equals("ROLE_USER"))
				url = "/freshfood/trang-chu";
			else if (i.getAuthority().equals("ROLE_ADMIN")) {
				url = "/freshfood/admin/trang-chu";
				break;
			}
		}
		return url;
	}

}

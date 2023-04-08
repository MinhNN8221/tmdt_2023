package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.utils.LoginSuccessHandler;
import com.example.utils.OauthLoginSuccessHandler;
import com.example.utils.OauthUserPrincipalDetailService;
import com.example.utils.UserPrincipalDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserPrincipalDetailService userprincipaldetailservice;
	
	@Autowired
	private OauthUserPrincipalDetailService oauthservice;
	
	@Autowired
	private LoginSuccessHandler loginsuccess;
	
	@Autowired
	private OauthLoginSuccessHandler oauthloginsuccess;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth){
	    auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/oauth/**").permitAll()
		    .antMatchers("/freshfood/trang-chu").permitAll()
		    .antMatchers("/freshfood/admin/**").hasRole("ADMIN")
		    .and()
		    .formLogin().loginProcessingUrl("/signin")
		                .loginPage("/freshfood/dang-nhap")
		                .permitAll()
		                .usernameParameter("email")
		                .passwordParameter("password") 
		                .failureUrl("/freshfood/dang-nhap?error")
		                .successHandler(loginsuccess)
		    .and()
		   	.oauth2Login().loginPage("/freshfood/dang-nhap")
		   	              .userInfoEndpoint().userService(oauthservice)
		   	              .and()
		   	              .successHandler(oauthloginsuccess)
		    .and()
		    .logout().logoutUrl("/logout")
		             .logoutSuccessUrl("/freshfood/trang-chu")
	                 .deleteCookies("JSESSIONID")
		             .invalidateHttpSession(true)
	                 .clearAuthentication(true);
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userprincipaldetailservice);
		return daoAuthenticationProvider;
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

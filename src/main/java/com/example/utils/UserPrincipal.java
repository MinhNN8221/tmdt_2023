package com.example.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.AuthProvider;
import com.example.entity.UserEntity;
//UserDetails đc dùng để xây dựng đối tượng Authentication 
//đc luu trong SecurityContextHolder
//và UserDetailService dùng để tạo ra UserDetails dựa theo username đc đưa vào
public class UserPrincipal implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private UserEntity user;
	
	public UserPrincipal(UserEntity user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	   List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
	   this.user.getRoles().forEach(
	         r -> {
	        	 String role = r.getName();
	        	 GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role);
	        	 roles.add(grantedAuthority);
	         }
	   );
	   return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	public String getEmail() {
		return this.user.getEmail();
	}
	
	public UserEntity getUser() {
		return this.user;
	}
	
	public AuthProvider getauthprovider() {
		return this.user.getAuthProvider();
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getFullname();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.getStatus() == 1;
	}
}

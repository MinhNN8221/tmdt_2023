package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.respository.InUserRes;

@Service
public class UserPrincipalDetailService implements UserDetailsService{

	private static final Logger log = LoggerFactory.getLogger(UserPrincipalDetailService.class);

	@Autowired
	private InUserRes user;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.trace("Entering method ...");
		log.debug("Authenticating ... ");
		if (!user.getEmails().contains(email)) {
			log.error("Wrong email !");
			throw new UsernameNotFoundException("Wrong username"); 
		}else {
			log.warn("Testing email ...");
			log.info("Authenticated successfully");
			return new UserPrincipal(user.findByEmail(email).get());
		}        
	}
}

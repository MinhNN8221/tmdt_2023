package com.example.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LanguageConfig implements WebMvcConfigurer{
	    //Thành phần trung gian giúp giải quyết giữa yêu cầu người dùng
		//và nhgx file messages_xx.properties
		@Bean
		public LocaleResolver localeResolver() {
			SessionLocaleResolver slr = new SessionLocaleResolver();
			slr.setDefaultLocale(new Locale("vi"));
			return slr;
		}
		
		//Tiếp nhận request của user tông qua parameter là "lang"
		// và đẩy qua LocalResolver
		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
		    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		    lci.setParamName("lang");
		    return lci;
		}

		//Đăng kí LocaleChangeInterceptor
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			// TODO Auto-generated method stub
			registry.addInterceptor(localeChangeInterceptor());
		}
		
		//GIúp tham chiếu đến file message tường ứng thông qua giá trị của biến lang
		@Bean
		public MessageSource messageSource() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasename("messages");
			messageSource.setDefaultEncoding("UTF-8");
			return messageSource;
		}
}

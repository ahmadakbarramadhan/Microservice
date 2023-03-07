package com.example.guest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.guest.interceptor.ProductInterceptor;


@Component
public class ProductRegistrationConfig implements WebMvcConfigurer {
	 
	@Autowired
	 ProductInterceptor productInterceptor;
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
		   
	      registry.addInterceptor(productInterceptor);
	   }
}

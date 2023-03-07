package com.example.guest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.guest.exception.InvalidAuthException;

@Component
public class ProductInterceptor implements HandlerInterceptor {
	 @Override
	   public boolean preHandle
	      (HttpServletRequest request, HttpServletResponse response, Object handler) 
	      throws Exception {
	      if (null == request.getHeader("guest-auth-key")) {
	    	  throw new InvalidAuthException("Invalid Authorization");
	      }
	      System.out.println("Inside the Pre Handle method");
	      return true;
	   }
}
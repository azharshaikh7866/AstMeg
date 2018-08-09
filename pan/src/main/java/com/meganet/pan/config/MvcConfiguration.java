package com.meganet.pan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
	private RequestInterceptor requestInterceptor;
	
	@Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(requestInterceptor);
	  }

}



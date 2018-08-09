package com.meganet.pan.util;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class RestUtil {
	
	public static <T> Object restPostCall(String url,String handler,Object inputObj, Class<T> responseType){
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		HttpEntity<?> httpEntity = new HttpEntity<Object>(gson.toJson(inputObj), requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<T> result = restTemplate.exchange(url+handler, HttpMethod.POST, httpEntity,responseType);
		return result.getBody();
	}
	
	public static <T> Object restGetCall(String url,String handler,Object inputObj, Class<T> responseType,String requestHeaderKey,String requestHeaderValue){
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set(requestHeaderKey, requestHeaderValue);
		Gson gson = new Gson();
		HttpEntity<?> httpEntity = new HttpEntity<Object>(gson.toJson(inputObj), requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<T> result = restTemplate.exchange(url+handler, HttpMethod.GET, httpEntity,responseType);
		return result.getBody();
	}
}
package com.meganet.eureka;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client-article")
public interface ArticleClient {
	
	@GetMapping("/")
	public String getWord();

}

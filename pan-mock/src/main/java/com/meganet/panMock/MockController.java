package com.meganet.panMock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MockController {

	@Value("${wordConfig.panids}") String panids;

	  @GetMapping("/prop")
	  public String showLuckyWord() {
	    return "The Panids are: " + panids;
	  }
}

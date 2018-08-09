package com.meganet.eureka;

//import java.net.URI;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomController {
	//Lab 6 Commented
	/*@Autowired
	RestTemplate client;
	
	
	  @GetMapping("/sentence")
	  public @ResponseBody String getSentence() {
	    return 
	      getWord("eureka-client-subject") + " "
	      + getWord("eureka-client-verb") + " "
	      + getWord("eureka-client-article") + " "
	      + getWord("eureka-client-adjective") + " "
	      + getWord("eureka-client-noun") + "."
	      ;
	  }
	  
	  public String getWord(String service) {
	    List<ServiceInstance> list = client.getInstances(service);
	    if (list != null && list.size() > 0 ) {
	      URI uri = list.get(0).getUri();
	  if (uri !=null ) {
	    return (new RestTemplate()).getForObject(uri,String.class);
	  }
	    }
	    return null;
		  return client.getForObject("http://" + service, String.class);
	  }*/
	
	
@Autowired SentenceService sentenceService;
	
	
	/**
	 * Display a small list of Sentences to the caller:
	 */
	@GetMapping("/sentence")
	public @ResponseBody String getSentence() {
	  return 
		"<h3>Some Sentences</h3><br/>" +	  
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>"
		;
	}

}

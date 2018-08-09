package com.meganet.pan.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meganet.pan.util.PanVerificationUtil;
import com.meganet.pan.util.PropsValue;


@RestController
public class PanController {
	
	
	
	@Value("pan.jksFilePath")
	String jksFilePath;
	
	@Value("pan.panUserId")
	String panUserId;
	
	@Value("pan.url")
	String url;
	
	@Value("pan.key")
	String key;
	
	@Autowired
	PanVerificationUtil panVerificationUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PanController.class);
	
	@GetMapping("/verify")
	public Map<String, String> panService(@RequestParam("pan")String[] pan) throws Exception{
		LOGGER.info("START OF panService");
		LOGGER.debug("Requested Pan input: "+pan );
		String res = panVerificationUtil.panWebService(jksFilePath, panUserId, url, key, pan);
		LOGGER.debug("Response from NSDL:" + res);
		
		Map<String, String> resMap = new LinkedHashMap<String, String>(); 
		try{
		res=res+"^END";
		String[] resArr = res.split("\\^");
		String code = resArr[0];
		resMap.put("code", code);
		int j=1;
		for(int i=1;i<resArr.length-1;i++){
			resMap.put("PAN"+j, resArr[i]);
			resMap.put("PAN"+j+"STATUS", resArr[++i]);
			resMap.put("PAN"+j+"LASTNAME", resArr[++i]);
			resMap.put("PAN"+j+"FIRSTNAME", resArr[++i]);
			resMap.put("PAN"+j+"MIDDLENAME", resArr[++i]);
			resMap.put("PAN"+j+"TITLE", resArr[++i]);
			resMap.put("PAN"+j+"LASTUPDATEDATE", resArr[++i]);
			resMap.put("PAN"+j+"FILLER1", resArr[++i]);
			resMap.put("PAN"+j+"FILLER2", resArr[++i]);
			resMap.put("PAN"+j+"FILLER3", resArr[++i]);
			j++;
		}
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("END OF panService");
		return resMap;
	}
	
}

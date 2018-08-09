package com.meganet.pan.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.meganet.pan.model.Context;
import com.meganet.pan.model.PanRequest;
import com.meganet.pan.model.ShRequest;
import com.meganet.pan.util.PanVerificationUtil;
import com.meganet.pan.util.PropsValue;
import com.meganet.pan.util.RestUtil;

@RestController
public class PanController {
	
	@Autowired
	PropsValue propsValue;
	
	@Autowired
	PanVerificationUtil panVerificationUtil;
	
	@GetMapping("/verify")
	public String panService(@RequestParam("pan")String[] pan) throws Exception{
		String res = panVerificationUtil.panWebService(propsValue.jksFilePath, propsValue.panUserId, propsValue.url, propsValue.key, pan);
		Gson gson = new Gson();
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
		}
		return gson.toJson(resMap);
	}
	
	//ESB Handler
	@PostMapping("/panVerification")
	public String panVerification(@RequestBody PanRequest panRequest) {
		//validation
		String panRes = (String) RestUtil.restPostCall("http://localhost:7777/nsdl/PanVerifivation", "", panRequest, String.class);
		return panRes.toString();
	}
	
	public void validatePanRequest(PanRequest panRequest) {
		if(panRequest!=null) {
			if(panRequest.getShRequest()!=null) {
				Context context = panRequest.getShRequest().getContext();
				if(context!=null) {
//					if(context.getcName()!=null&&)
				}else {
					
				}
		}
	}
	}
}

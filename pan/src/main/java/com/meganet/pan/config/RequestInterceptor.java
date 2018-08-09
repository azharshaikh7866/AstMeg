package com.meganet.pan.config;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.meganet.pan.enums.OperationType;
import com.meganet.pan.model.RequestDetail;
import com.meganet.pan.model.ResponseDetail;
import com.meganet.pan.services.RequestDetailService;
import com.meganet.pan.services.ResponseDetailService;

@Configuration
public class RequestInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private RequestDetailService requestDetailService;
	
	@Autowired 
	ResponseDetailService responseDetailService;
	
	private RequestDetail requestData;
	int i=1;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String,String> out = new HashMap<String, String>();
        Map<String,String[]> map = request.getParameterMap();
        for (Map.Entry<String,String[]> mapEntry : map.entrySet()) {
        	String key=mapEntry.getKey();
            String valueArray[] = mapEntry.getValue();
            Object object = valueArray.length == 1 ? valueArray[0] : valueArray;
            String value=(String)object;
            out.put(key, value);
        }
    	Gson gson = new Gson();
    	String json = gson.toJson(out);
		RequestDetail requestData=null;
		requestData=new RequestDetail(new Date(), "", "", "", json, OperationType.PAYLOAD);
		requestDetailService.save(requestData); 
		return true;
	}
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
			OutputStream responseData = response.getOutputStream();
			i++;
			long temp= (long) (Math.random()*i*100000);
			String responseType=response.getContentType();
			System.out.println(responseType);
			responseData.toString();
			byte[] b=new byte[32];
			responseData.write(b);
			
			ResponseDetail responseDetail= new ResponseDetail(new Date(), requestData, "temp"+temp, b, responseType, "0",OperationType.PAYLOAD );
			responseDetailService.save(responseDetail);
			System.out.println("response Successfully saved !!!");
			}
}

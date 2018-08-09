package com.meganet.pan.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("pan")
public class PropsValue {
	
	 public String url;
	 
	 public String jksFilePath;
	 
	 public String panUserId;
	 
	 public String key;
	 
	 public String signature;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJksFilePath() {
		return jksFilePath;
	}

	public void setJksFilePath(String jksFilePath) {
		this.jksFilePath = jksFilePath;
	}

	public String getPanUserId() {
		return panUserId;
	}

	public void setPanUserId(String panUserId) {
		this.panUserId = panUserId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	 
	

}

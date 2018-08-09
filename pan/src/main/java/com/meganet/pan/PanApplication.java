package com.meganet.pan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
/*import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;*/
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.meganet.pan.service.security.CustomUserInfoTokenServices;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableResourceServer
//@EnableOAuth2Client
@EnableJpaRepositories(basePackages = "com.meganet.pan.repository")
@EntityScan(basePackages = "com.meganet.pan.model")
@ComponentScan(basePackages = {"com.meganet.pan*"})
public class PanApplication {
	

	/*@Autowired
	private ResourceServerProperties sso;*/

	public static void main(String[] args) {
		SpringApplication.run(PanApplication.class, args);
	}
	
	/*@Bean
	public ResourceServerTokenServices tokenServices() {
		return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
	}
*/

}

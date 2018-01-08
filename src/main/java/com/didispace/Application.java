package com.didispace;

import com.didispace.base.jwt.filter.HTTPBearerAuthorizeAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public FilterRegistrationBean jwtFilterRegistrationBean(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
		registrationBean.setFilter(httpBearerFilter);
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/api/*");
		registrationBean.addUrlPatterns("/api/*");
		//registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
}

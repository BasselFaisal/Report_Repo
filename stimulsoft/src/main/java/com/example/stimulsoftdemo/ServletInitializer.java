package com.example.stimulsoftdemo;

import com.example.stimulsoftdemo.servlet.HelloServlet;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StimulSoftDemoApplication.class);
	}

//	@Bean
//	public ServletRegistrationBean<HelloServlet> servletRegistrationBean() {
//		ServletRegistrationBean<HelloServlet> bean =
//				new ServletRegistrationBean<HelloServlet>(new HelloServlet(), "/hello");
//		return bean;
//	}

}

package com.ktdsuniversity.edu.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.common.interceptors.AccessControlInterceptor;
import com.ktdsuniversity.edu.common.interceptors.CheckSessionInterceptor;

/**
 * Interceptor를 설정하기 위한 클래스.
 */
@Configuration // Spring Application에 필요한 여러가지 설정정보들을 활성화 시키는 애노테이션.
				// application.yml 과 동시에 사용될 경우, application.yml의 우선순위가 밀려남.
@EnableWebMvc // spring-boot-starter-web 라이브러리 활성화 시키는 애노테이션.
public class WebConfig implements WebMvcConfigurer {

	// 웹 애플리케이션의 필수 기능.
	// 사용자가 방문한 엔드포인트를 Database에 저장.
	//  누가 언제 어디로 어떤 방법을 통해서 접근을 했는가?
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new CheckSessionInterceptor())
				.addPathPatterns(List.of("/**")) // CheckSessionInterceptor가 개입 할 주소
				.excludePathPatterns(
						List.of(
								"/js/**", 
								"/css/**", 
								"/member/regist/**", 
								"/member/login",
								"/member/delete-success",
								"/list"
								)) // CheckSessionInterceptor가 개입하지 말아야 할 주소
				;
		
		registry.addInterceptor(new AccessControlInterceptor())
				.addPathPatterns(List.of("/member/login", "/member/regist/**"));
		
	}
	
	
	/**
	 * Static Resource를 받아오는 설정.
	 *  /static 폴더에 있는 파일에 접근하기 위한 Endpoint 설정.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");
		
		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/");
		
	}
	
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	
	
}

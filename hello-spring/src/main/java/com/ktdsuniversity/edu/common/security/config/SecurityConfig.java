package com.ktdsuniversity.edu.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true) // debug = true로 설정하면, 어떤 필터가 어떤 순서로 동작하는 지 볼 수 있음 
// 위 2개 달아줘야 함
public class SecurityConfig {
	// 어떤 조건으로 동작할지 설정 (end point)
	/**
	 * Spring Security에 내장된 필터들이 어떤 조건에서 어떤 순서로 어떤 우선순위로 동작할 것인지 규칙을 정의
	 * 규칙을 정의
	 * @param http
	 * @return
	 */
	@Bean // 내가 반환시킨 데이터를 Bean에 들어가게 하는 방법 => @Bean를 붙인다 (메소드를 등록하는 게 아니라 반환한 데이터를 등록하는 것)
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // !!그냥 외워야 함!! public 이런 거 쓰지말기
        // 인증과 관련된 필터에 대한 조건 명시.
		// 설정상의 예외는 spring이 알 수 있도록 그냥 던짐 try catch 안함
		http.formLogin((formLogin /*FormLoginConfigurer*/) -> {
			
			//formLogin.successForwardUrl("/list")
			formLogin.defaultSuccessUrl("/list");
		
		}); // (Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer) 

		return http.build(); // 이제 임시 비번 안줌 (encode 사용), db를 바라보게 해놨음
		// /login은 spring이 만들어준 url
	}
}

package com.ktdsuniversity.edu.common.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ktdsuniversity.edu.common.security.handlers.LoginFailureHandler;
import com.ktdsuniversity.edu.common.security.handlers.LoginSuccessHandler;
import com.ktdsuniversity.edu.common.security.jwt.filter.JwtAuthenticationFilter;
import com.ktdsuniversity.edu.member.dao.MemberDao;

import ch.qos.logback.core.joran.spi.NoAutoStart;

@Configuration // 들어가면 @Component 붙어있음 > 그래서 MemberDao에 Autowired를 붙인다 
@EnableWebSecurity(debug = true) // debug = true로 설정하면, 어떤 필터가 어떤 순서로 동작하는 지 볼 수 있음 
// 위 2개 달아줘야 함
@EnableMethodSecurity // boolean prePostEnabled() default true; => default가 true라서 이것만 달아주면됨
public class SecurityConfig {
	// 어떤 조건으로 동작할지 설정 (end point)
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Autowired
	private MemberDao memberDao;
    
    
    
	/**
	 * Spring Security에 내장된 필터들이 어떤 조건에서 어떤 순서로 어떤 우선순위로 동작할 것인지 규칙을 정의
	 * 규칙을 정의
	 * @param http
	 * @return
	 */
	@Bean // 내가 반환시킨 데이터를 Bean에 들어가게 하는 방법 => @Bean를 붙인다 (메소드를 등록하는 게 아니라 반환한 데이터를 등록하는 것)
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // !!그냥 외워야 함!! public 이런 거 쓰지말기
		
		// CustomFilter FilterChain에 등록
		http.addFilterAfter(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 우리가 만든 필터, 기준(이 다음에 들어가라)
		
		// http.csrf(csrf -> csrf.disable()); // 이거 안하면 405 에러 남
		// http://localhost:8081/auth
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/auth")); // 추가한 url에는 확인하지마라 
		
		// CORSFilter 활성화. 파라미터 있는 생성자 사용. 시스템 간의 연동은 아래 설정이 필요!
 		http.cors(cors/*CorsConfigurer*/ -> {
			CorsConfigurationSource corsSource = (request) -> {
				CorsConfiguration corsConfig = new CorsConfiguration();
				corsConfig.setAllowedOrigins(List.of("http://localhost:8080")); // 누가 우리에게 요청할 것이냐.
				corsConfig.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE", "FETCH", "OPTION")); 
				// 요청할 때 어떤 메소드로 요청할 것이냐.
				// 리액트에서 사용 (준비 작업) : FETCH (데이터 조회), OPTION (검사) 
				// 리액트 서버 : :5172 or :3000
				corsConfig.setAllowedHeaders(List.of("*")); // 요청할 때 어떤 Request Header를 보낼 것이냐. *면 모든 헤더를 받음
				return corsConfig;
			};
			
			cors.configurationSource(corsSource);
			
		});
		
        // 인증과 관련된 필터에 대한 조건 명시.
		// 설정상의 예외는 spring이 알 수 있도록 그냥 던짐 try catch 안함
		http.formLogin((formLogin /*FormLoginConfigurer*/) -> {
			formLogin.loginPage("/member/login");
			// Security AuthenticationProvider가 동작될 End Point => Security 인증 절차를 태워라!
			formLogin.loginProcessingUrl("/member/authenticate"); // form에 적어준 url
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password"); // 파라미터 이름 맞게 바꿔서 전달 
			//formLogin.defaultSuccessUrl("/list");
			formLogin.successHandler(new LoginSuccessHandler(this.memberDao)); // 로그인이 성공했을 때 처리될 객체. => db에 넣고 처리
			// successHandler(AuthenticationSuccessHandler successHandler)
			formLogin.failureHandler(new LoginFailureHandler(this.memberDao)); // 로그인이 실패했을 때 처리될 객체. => db에 넣고 처리
		    // failureHandler(AuthenticationFailureHandler authenticationFailureHandler)
		}); // (Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer) 

		return http.build(); // 이제 임시 비번 안줌 (encode 사용), db를 바라보게 해놨음
		// /login은 spring이 만들어준 url
	}
}

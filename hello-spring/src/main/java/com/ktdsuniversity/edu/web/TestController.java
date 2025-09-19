package com.ktdsuniversity.edu.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller 역할 부여.
@Controller
public class TestController {

	public TestController() {
		System.out.println("인스턴스를 만듭니다!!!!!");
	}
	
	// End-point 생성.
	@GetMapping("/hello")
	// "/hello"에 대응하는 메소드.
	public String viewHelloPage(Model model) {
		model.addAttribute("myname", "장민창");
		
		// Template Engine 반환.
		// spring.mvc.view.prefix + 파일의 이름 + spring.mvc.view.suffix
		// /WEB-INF/views/ + testview + .jsp
		return "testview"; // "/WEB-INF/views/testview.jsp"
	}
	
}

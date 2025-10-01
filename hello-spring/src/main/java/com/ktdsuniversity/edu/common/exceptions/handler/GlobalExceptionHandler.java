package com.ktdsuniversity.edu.common.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(HelloSpringException.class)
	public String viewErrorPage(
			HelloSpringException hse,
			Model model) {
		
		logger.error(hse.getMessage(), hse);
		
		model.addAttribute("errorMessage", hse.getMessage());
		
		if (hse.getModelKey() != null) {
			model.addAttribute(hse.getModelKey(), hse.getModelValue());
		}
		
		return hse.getViewName();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String viewRuntimeExceptionPage(
			RuntimeException re, Model model) {
		
		logger.error(re.getMessage(), re);
		
		model.addAttribute("errorMessage", "예기치 않은 에러가 발생했습니다.");
		
		return "error/500";
	}
	
	
}

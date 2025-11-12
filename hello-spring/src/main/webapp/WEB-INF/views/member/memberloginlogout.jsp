<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="horizontal-flex flex-end flex-gap-10 item-padding-0">
	<sec:authorize access="!isAuthenticated()">	
		<a href="/member/regist">회원가입</a>
		<a href="/member/login">로그인</a> 
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">	
		<div>
		<!-- [2025-10-20] Chatting Room 보관 -->
		     <span id="login-user-name">
		     	<sec:authentication property="principal.name"/> <!-- memberVO 에 있는 이름 -->
		     </span>
			(<span id="login-user-email"><sec:authentication property="principal.email" /></span>)
			<!-- 컨트롤러 파라미터에 있는 authentication임. property="name" -> 이메일을 가져옴 -->
		</div>
		<a href="/member/logout">로그아웃</a>
		<a href="/member/delete-me">탈퇴</a>
	</sec:authorize>	
	
</div>
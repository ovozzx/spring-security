<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="horizontal-flex flex-end flex-gap-10 item-padding-0">
	<c:choose>
		<c:when test="${empty sessionScope.__LOGIN_USER__}">
			<a href="/member/regist">회원가입</a>
			<a href="/member/login">로그인</a>
		</c:when>
		<c:otherwise>
			<div>${sessionScope.__LOGIN_USER__.name}
				(${sessionScope.__LOGIN_USER__.email})</div>
			<a href="/member/logout">로그아웃</a>
			<a href="/member/delete-me">탈퇴</a>
		</c:otherwise>
	</c:choose>
</div>
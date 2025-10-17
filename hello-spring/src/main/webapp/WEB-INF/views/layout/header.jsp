<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${param.title}</title>
		<link type="text/css" 
		      rel="stylesheet" 
		      href="/css/hello-spring.css" />
		<script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
		<script type="text/javascript" src="/js/common/common.js"></script>
		<script type="text/javascript" src="/js/common/paginator.js"></script>
		<script type="text/javascript" src="/js/common/validate.js"></script>
		${param.scripts}
		<c:if test="${not empty sessionScope.__LOGIN_USER__}">
		    <script type="text/javascript" src="/js/sockjs/sockjs.min.js"></script>
		    <script type="text/javascript" src="/js/sockjs/chat.js"></script>
		</c:if>
	</head>
	<body>
	    <div class="wrapper">
	        <jsp:include page="../member/memberloginlogout.jsp" />
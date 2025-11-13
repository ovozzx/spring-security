<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<sec:csrfMetaTags /> <!-- ajax 용 -->
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
		<sec:authorize access="isAuthenticated()">
		    <script type="text/javascript" src="/js/sockjs/sockjs.min.js"></script>
		    <script type="text/javascript" src="/js/sockjs/chat.js"></script>		
		</sec:authorize>
		<!--<c:if test="${not empty sessionScope.__LOGIN_USER__}">
		</c:if>-->
	</head>
	<body>
	    <div class="wrapper">
	        <jsp:include page="../member/memberloginlogout.jsp" />
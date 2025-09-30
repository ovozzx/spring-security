<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
		<script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
		<script type="text/javascript" src="/js/common/validate.js"></script>
		<script type="text/javascript" src="/js/member/login.js"></script>
	</head>
	<body>
	
	   <div class="wrapper">
	       <jsp:include page="./memberloginlogout.jsp" />
	       <h1 class="page-title">로그인</h1>
	       
           <form:form modelAttribute="requestMemberLoginVO"
                      method="post"
                      action="/member/login">
		       <div class="grid member-login">
		           <label for="email" class="require">이메일</label>
		           <div>
		              <input type="text" id="email" name="email" value="${inputData.email}" />
		              <form:errors path="email" cssClass="validate-error validate-require" />
		           </div>
		           
                   <label for="password" class="require">비밀번호</label>
		           <div>
                      <input type="password" id="password" name="password" />
                      <form:errors path="password" cssClass="validate-error validate-require" />
                   </div>
		           
		           <div class="btn-group">
		              <div class="right-align">
		                  <button type="button" 
		                          disabled="disabled"
		                          data-range=".member-login"
		                          data-dependencies="#email,#password"
		                          class="save-btn auto-active login-button">Login</button>
		              </div>
		           </div>
		           
		       </div>
           </form:form>
	   </div>
	
    </body>
</html>









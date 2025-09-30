<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link type="text/css" rel="stylesheet" href="/css/hello-spring.css" />
        <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
        <script type="text/javascript" src="/js/board/write.js"></script>
        <script type="text/javascript" src="/js/common/validate.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../member/memberloginlogout.jsp" />
	        <h1 class="page-title">게시글 작성</h1>
	        <form:form modelAttribute="requestCreateBoardVO" 
	                   method="post" 
	                   action="/write" 
	                   enctype="multipart/form-data">
	            <div class="grid board-write">
		            <label for="subject" class="require">제목</label>
		            <div>
		              <input id="subject" type="text" name="subject" value="${writeData.subject}" />
		              <form:errors path="subject" cssClass="validate-error validate-require" />
		            </div>
		            
		            <label for="file">첨부파일</label>
                    <div class="vertical-flex">
                        <button type="button" class="save-btn add-file">파일 추가</button>
                        <input id="file" type="file" name="file" />
                    </div>
		            
		            <label for="content" class="require">내용</label>
		            <div>
		              <textarea id="content" name="content">${writeData.content}</textarea>
		              <form:errors path="content" cssClass="validate-error validate-require" />
		            </div>
		            
		            <div class="btn-group">
		                <div class="right-align">
		                    <button class="cancel-btn" type="button">취소</button>
		                    <button class="save-btn">저장</button>
		                </div>
		            </div>
	            </div>
	        </form:form>
        </div>
    </body>
</html>
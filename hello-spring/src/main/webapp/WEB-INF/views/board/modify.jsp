<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link type="text/css" rel="stylesheet" href="/css/hello-spring.css" />
    </head>
    <body>
        <div class="wrapper">
	        <h1 class="page-title">게시글 수정</h1>
	        <form method="post" action="/modify/${board.id}">
	            <div class="grid board-write">
		            <label for="subject" class="require">제목</label>
		            <input id="subject" type="text" name="subject" value="${board.subject}"/>
		            
		            <label for="email" class="require">이메일</label>
		            <input id="email" type="email" name="email" value="${board.email}" />
		            
		            <label for="content" class="require">내용</label>
		            <textarea id="content" name="content">${board.content}</textarea>
		            
		            <div class="btn-group">
		                <div class="right-align">
		                    <button class="cancel-btn" type="button">취소</button>
		                    <button class="save-btn">저장</button>
		                </div>
		            </div>
	            </div>
	        </form>
        </div>
    </body>
</html>
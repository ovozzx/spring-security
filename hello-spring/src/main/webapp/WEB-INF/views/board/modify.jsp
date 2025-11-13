<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <jsp:include page="/WEB-INF/views/layout/header.jsp">
        <jsp:param value="게시글 수정" name="title" />
    </jsp:include>
	        <h1 class="page-title">게시글 수정</h1>
	        <form:form modelAttribute="requestModifyBoardVO" method="post" action="/modify/${board.id}">
	            <sec:csrfInput />
	            <div class="grid board-write">
		            <label for="subject" class="require">제목</label>
		            <div>
		              <input id="subject" type="text" name="subject" value="${board.subject}"/>
		              <form:errors path="subject" cssClass="validate-error" />
		            </div>
		            
		            <label for="content" class="require">내용</label>
		            <div>
		              <textarea id="content" name="content">${board.content}</textarea>
		              <form:errors path="content" cssClass="validate-error" />
		            </div>
		            
		            <div class="btn-group">
		                <div class="right-align">
		                    <button class="cancel-btn" type="button">취소</button>
		                    <button class="save-btn">저장</button>
		                </div>
		            </div>
	            </div>
	        </form:form>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
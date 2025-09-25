<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link type="text/css" rel="stylesheet" href="/css/hello-spring.css" />
    </head>
    <body>
        <div class="wrapper">
            <h1 class="page-title">게시글 조회</h1>
               <div class="grid board-view">
                   <label for="subject">제목</label>
                   <div><c:out value="${board.subject}" /></div>
                   
                   <label for="email">이메일</label>
                   <div>${board.email}</div>
                   
                   <label for="files">첨부파일</label>
                   <div>
                        <div>${board.fileGroupVO.fileCount}개의 첨부파일</div>
                        <c:forEach items="${board.fileGroupVO.file}" var="file">
                            <div>
                                <a href="/file/${board.id}/${file.fileGroupId}/${file.fileId}">
                                    ${file.fileDisplayName}
                                </a>
                                ${file.fileSize} bytes
                            </div>
                        </c:forEach>
                   </div>
                   
                   <label for="viewCnt">조회수</label>
                   <div>${board.viewCnt}</div>
                   
                   <label for="crtDt">등록일</label>
                   <div>${board.crtDt}</div>
                   
                   <label for="mdfyDt">수정일</label>
                   <div>${board.mdfyDt}</div>
                   
                   <label for="content">내용</label>
                   <div>${board.content}</div>
                   
                   <div class="btn-group">
                       <div class="right-align">
                           <a href="/modify/${board.id}" class="modify-link">수정</a>
                           <a href="/delete/${board.id}" class="delete-link">삭제</a>
                           <a href="/list" class="list-link">목록으로 가기</a>
                       </div>
                   </div>
               </div>
        </div>
    </body>
</html>
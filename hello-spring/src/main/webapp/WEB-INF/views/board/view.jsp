<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link type="text/css" rel="stylesheet" href="/css/hello-spring.css" />
        <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
        <script type="text/javascript" src="/js/common/common.js"></script>
        <script type="text/javascript" src="/js/reply/reply.js"></script>
        <script type="text/javascript" src="/js/common/validate.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../member/memberloginlogout.jsp" />
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
                <div>
                    <!-- pre <- Presentation : 태그 컨텐츠를 그대로 표현시키는 태그. -->
                    <pre class="content">${board.content}</pre>
                </div>
                
                <div class="btn-group">
                    <div class="right-align">
                        <sec:authentication property="principal.email" var="authEmail"/> <!-- property 값을 var 변수에 넣어라 -->
                        <c:if test="${authEmail eq board.email}">
                         <a href="/modify/${board.id}" class="modify-link">수정</a>
                         <a href="/delete/${board.id}" class="delete-link">삭제</a>
                        </c:if>
                        <a href="/list" class="list-link">목록으로 가기</a>
                    </div>
                </div>
                
                <div class="reply-area" data-board-id="${board.id}">
                    <template class="reply-template">
                        <li data-reply-id="#replyId#">
                            <div class="reply-author horizontal-flex flex-gap-10">
                                <div class="flex-grow-1">#replyAuthor#</div>
                                <div>작성: #replyCreateDateTime#</div>
                                <div>수정: #replyModifyDateTime#</div>
                                <div class="recommend-count">추천: #replyRecommendCount#</div>
                            </div>
                            <pre class="content">#replyContent#</pre>
                            <div class="attached-file" 
                                 data-file-id="#fileId#">
                                <a href="/file/#replyId#/#fileGroupId#/#fileId#">#replyAttachedFilename#</a>
                                다운로드: #replyAttachedFileDownloadCount#
                            </div>
                            <div class="reply-control horizontal-flex flex-end flex-gap-10">
	                             <div class="reply-reply">댓글달기</div>	
                                 <div class="reply-delete">삭제하기</div>
                                 <div class="reply-modify">수정하기</div>
                                 <div class="reply-recommend">추천하기</div>                                                       	
                            </div>
                        </li>
                    </template>
                    <ul class="replies"></ul>
                    <div class="reply-input">
                        <input type="hidden" class="parent-reply-id" />
                        <input type="hidden" class="reply-id" />
                        <input type="file" />
                        
                        <div class="modify-attached-file">
                            <input type="checkbox" class="delete-file" />
                            <span class="attached-file-name"></span>
                        </div>
                        
                        <label for="reply-content" class="require">댓글내용</label>
                        <div>
                            <textarea id="reply-content"></textarea>
                        </div>
                        
                        <div class="btn-group">
                            <div class="right-align">
                                 <!-- 저장했을 때 보내는 엔드포인트: /reply/게시글아이디 -->
                                <button type="button"
                                        data-range=".reply-input"
                                        data-dependencies="#reply-content" 
                                        disabled="disabled"
                                        class="auto-active save-btn">댓글 등록</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
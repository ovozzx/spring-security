<%-- 
    <%@ <== Directive
    <%@ page ..... %> <== Page Directive ( Java class Import )
    <%@ taglib .... %> <== Tag library Directive
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <jsp:include page="/WEB-INF/views/layout/header.jsp">
        <jsp:param value="게시글 목록" name="title" />
        <jsp:param value="
            <script type='text/javascript' src='/js/board/list.js'></script>
        " name="scripts"/>
    </jsp:include>
    
	    <h1 class="page-title">게시글 목록</h1>
	    <div>검색된 게시글의 수: ${list.count}개</div>
	    
	    <table class="grid">
	        <colgroup>
	            <col width="80" />
	            <col width="*" />
	            <col width="200" />
	            <col width="80" />
	            <col width="120" />
	            <col width="120" />
	        </colgroup>
	        <thead>
		        <tr>
		            <th>번호</th>
		            <th>제목</th>
		            <th>이름</th>
		            <th>조회수</th>
		            <th>등록일</th>
		            <th>수정일</th>
		        </tr>
	        </thead>
	        <tbody>
	        
	            <c:choose>
	               <c:when test="${not empty list.list}">
	                   <c:forEach items="${list.list}" var="article">
		                    <tr>
		                        <td>${article.number}</td>
		                        <td>
		                          <a href="/view/${article.id}"><c:out value="${article.subject}" /></a>
		                        </td>
		                        <td class="request-chat" data-email="${article.memberVO.email}">${article.memberVO.name}</td>
		                        <td>${article.viewCnt}</td>
		                        <td>${article.crtDt}</td>
		                        <td>${article.mdfyDt}</td>
		                    </tr>
		                </c:forEach>
	               </c:when>
	               <c:otherwise>
			            <tr>
			               <td colspan="6" class="no-data">게시글이 없습니다. 첫 번째 게시글의 주인공이 되어보세요!</td>
			            </tr>
	               </c:otherwise>
	            </c:choose>
	        </tbody>
	    </table>
	    
	    <form class="board-search-form">
	       <input type="date" name="from" class="from" value="${search.from}" /> ~ 
	       <input type="date" name="to" class="to" value="${search.to}" />
	       <select name="searchType">
	           <option value="name" ${search.searchType eq "name" ? "selected" : ""}>작성자명</option>
	           <option value="subject_content" ${search.searchType eq "subject_content" ? "selected" : ""}>제목+내용</option>
	           <option value="subject" ${search.searchType eq "subject" ? "selected" : ""}>제목</option>
	           <option value="content" ${search.searchType eq "content" ? "selected" : ""}>내용</option>
	       </select>
	       <input type="text" name="searchKeyword" value="${search.searchKeyword}" />
	       <button type="button" class="search-button">검색</button>
	    </form>
	    
	    <jsp:include page="/WEB-INF/views/layout/paginator.jsp">
	       <jsp:param value="${search.listSize}" name="listSize"/>
	       <jsp:param value="${search.havePrevPageGroup}" name="havePrevPageGroup"/>
	       <jsp:param value="${search.prevGroupStartPageNo}" name="prevGroupStartPageNo"/>
	       <jsp:param value="${search.groupStartPageNo}" name="groupStartPageNo"/>
	       <jsp:param value="${search.groupEndPageNo}" name="groupEndPageNo"/>
	       <jsp:param value="${search.haveNextPageGroup}" name="haveNextPageGroup"/>
	       <jsp:param value="${search.nextGroupStartPageNo}" name="nextGroupStartPageNo"/>
	       <jsp:param value="${search.pageCount}" name="pageCount"/>
	    </jsp:include>
	    
	    <div class="btn-group">
	       <div class="right-align">
	           <c:if test="${not empty sessionScope.__LOGIN_USER__}">
	               <button type="button" class="save-btn download-article">목록 다운로드</button>
	               <button type="button" class="save-btn download-article-2">목록 다운로드2</button>
	               <button type="button" class="save-btn write-article">새 글 작성</button>
	           </c:if>
	       </div>
	    </div>
	    
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
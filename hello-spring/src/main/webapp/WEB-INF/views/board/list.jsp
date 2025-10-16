<%-- 
    <%@ <== Directive
    <%@ page ..... %> <== Page Directive ( Java class Import )
    <%@ taglib .... %> <== Tag library Directive
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" 
      rel="stylesheet" 
      href="/css/hello-spring.css" />
<script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>
<script type="text/javascript" src="/js/board/list.js"></script>
<script type="text/javascript" src="/js/common/paginator.js"></script>
</head>
<body>

    <div class="wrapper">
        <jsp:include page="../member/memberloginlogout.jsp" />
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
		                        <td>${article.memberVO.name}</td>
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
	    
	    <ul class="paginator"
	        data-search-form-class=".board-search-form">
	       <li>
	           <select class="page-list-size">
	               <option value="10" ${search.listSize eq "10" ? "selected" : ""}>10</option>
	               <option value="20" ${search.listSize eq "20" ? "selected" : ""}>20</option>
	               <option value="30" ${search.listSize eq "30" ? "selected" : ""}>30</option>
	               <option value="40" ${search.listSize eq "40" ? "selected" : ""}>40</option>
	               <option value="50" ${search.listSize eq "50" ? "selected" : ""}>50</option>
	               <option value="100" ${search.listSize eq "100" ? "selected" : ""}>100</option>
	           </select>
	       </li>
	       
	       <c:if test="${search.havePrevPageGroup}">
	           <li>
                   <a data-page-no="0">
                       처음
                   </a>
               </li>
	           <li>
                   <a data-page-no="${search.prevGroupStartPageNo}">
                       이전
                   </a>
               </li>
	       </c:if>
	       
	       <c:forEach begin="${search.groupStartPageNo}"
	                  end="${search.groupEndPageNo}"
	                  step="1"
	                  var="page">
	           <li class="${search.pageNo eq page ? "active" : ""}">
	               <a data-page-no="${page}">
	                   ${page + 1}
	               </a>
	           </li>
	       </c:forEach>
	       
	       <c:if test="${search.haveNextPageGroup}">
               <li>
                   <a data-page-no="${search.nextGroupStartPageNo}">
                       다음
                   </a>
               </li>
               <li>
                   <a data-page-no="${search.pageCount-1}">
                       마지막
                   </a>
               </li>
           </c:if>
	       
	    </ul>
	    
	    <div class="btn-group">
	       <div class="right-align">
	           <c:if test="${not empty sessionScope.__LOGIN_USER__}">
	               <button type="button" class="save-btn download-article">목록 다운로드</button>
	               <button type="button" class="save-btn download-article-2">목록 다운로드2</button>
	               <button type="button" class="save-btn write-article">새 글 작성</button>
	           </c:if>
	       </div>
	    </div>
	    
    </div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>
<style>
  table {
    width: 100%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #444444;
    padding: 10px;
    text-align: center;
  }
  #search-form {
  	text-align: right;
  	}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container">
	<h2 class="text-primary" style="margin: 20px 0px 30px 0px;">게시판 목록</h2>
	<div id="search-form">
	<form id="search_form" action="boardList.do" method="get">
		<ul class="search">
		<li>
				<select name="keyfield" class="form-control" style="width:100px;height:42px;margin:3px">
					<option value="1">제목</option>
					<option value="2">작성자</option>
					<option value="3">내용</option>
				</select>
			
				<input type="text" class="form-control" value="${param.keyword}" name="keyword" id="keyword">
		<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px;">찾기</button>
			</li>
		</ul>
	</form>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회</th>
		</tr>
		<c:forEach var="board" items="${list}">
		<tr>
			<td>${board.board_num}</td>
			<td><a href="boardDetail.do?board_num=${board.board_num}">${board.title}</a></td>
			<td>${board.id}</td>
			<td>${board.reg_date}</td>
			<td>${board.hit}</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center" style="margin-top: 20px;">
		${pagingHtml}
	</div>
	</c:if>
	<div class="list-space" align="right">
		<input class="btn btn-primary" type="button" value="글쓰기" style="margin: 10px 0px 10px 0px;" onclick="location.href='boardWriteForm.do'"
		<c:if test="${empty user_num}"> onclick="location.href='${pageContext.request.contextPath}/main/loginform.do'"</c:if>>
		<input class="btn btn-secondary" type="button" value="목록" onclick="location.href='boardList.do';">
	</div>
</div>

</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





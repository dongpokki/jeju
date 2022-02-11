<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록(관리자 전용)</title>
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
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container mt-5">
	<h2>회원목록(관리자 전용)</h2>
	<form id="search_form" action="userList.do" method="get" align="right">
				<input type="text" class="form-control" placeholder="검색어를 입력해주세요" value="${param.keyword}" name="keyword" id="keyword">
				<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px;">찾기</button>
	</form>
	
	<c:if test="${count == 0}">
	<div class="result-display">
		표시할 내용이 없습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table id="userList" style="width:100%">
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>이메일</th>
			<th>전화번호</th>
			<th>가입일</th>
			<th>등급</th>
		</tr>
		<c:forEach var="user" items="${list}">
		<tr>
			<td>
				<c:if test="${user.auth > 0}">
				<a href="detailUserForm.do?user_num=${user.user_num}">${user.id}</a>
				</c:if>
				<c:if test="${user.auth == 0}">${user.id}</c:if>
			</td>
			<td>${user.name}</td>
			<td>${user.email}</td>
			<td>${user.phone}</td>
			<td>${user.reg_date}</td>
			<td>
			<c:if test="${user.auth == 0}">탈퇴</c:if>
			<c:if test="${user.auth == 1}">정지</c:if>
			<c:if test="${user.auth == 2}">일반</c:if>
			<c:if test="${user.auth == 3}">관리</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
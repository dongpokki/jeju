<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
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
    border-top: 1px solid #665d57;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #665d57;
    padding: 10px;
    text-align: center;
  }
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container mt-5">
	<h2 class="text-primary">회원목록</h2>
	<form id="search_form" action="userList.do" method="get" align="right">
	<div class="SearchSelect">
		<ul id="search">
		<li>
			<select name="keyfield" class="form-control" style="width:120px;height:42px;margin:3px">
							<option value="1"<c:if test="${param.keyfield==1}">selected</c:if>>ID</option>
							<option value="2"<c:if test="${param.keyfield==2}">selected</c:if>>이름</option>
							<option value="3"<c:if test="${param.keyfield==3}">selected</c:if>>E-mail</option>
							</select>
						
		<li>
		<input type="text" class="form-control" value="${param.keyword}" name="keyword" id="keyword">
		<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px;">찾기</button>
	</li>
	</ul>
	</div>
	JEJU와 함께하고 있는 회원 수 <span class="text-primary">${count }</span>명
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
				<a href="detailUserForm.do?admin_num=${user.user_num}">${user.id}</a>
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
		<div class="list-space align-right" align="right">
		<input class="btn btn-secondary" type="button" value="목록" onclick="location.href='userList.do';" style="margin-top: 10px;">
	</div>
	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
</div>
</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





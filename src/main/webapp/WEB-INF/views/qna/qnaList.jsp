<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
<div>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="container mt-5">
			<div class="page-banner" style="height:50px;background-color:#fff">
				<h1>QnA</h1>
			</div>
			<div>
				<table id="Qna">
					<tr>
						<c:if test="${session_user_auth==3}"><th>체크</th></c:if>
						<th>글번호</th>
						<th>공개여부</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
					<c:if test="${count==0 }">
					<tr>
						<td class="table-none" colspan="<c:if test="${session_user_auth==3 }">7</c:if><c:if test="${session.user_auth<3 }">6</c:if> align-center">표시할 내용이 없습니다.</td>
					</tr>
					</c:if>
					<c:if test="${count>0 }">
					<c:forEach var="qna" items="${list }">
					<tr>
						<c:if test="${session_user_auth==3}"><td>
							<input type="checkbox" name="delet_check" >
						</td></c:if>
						<td>${qna.qna_num }</td>
						<td><c:if test="${qna.viewable_check==1 }"><img src="${pageContext.request.contextPath }/images/lock.png"></c:if>
						<td><a href="qnaDetail.do">${qna.title }</a></td>
						<td>${qna.id }</td>
						<td>${qna.reg_date }</td>
						<td>${qna.hit }</td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				<div>
					
				</div>
				<div class="d-grid gap-2 col-6">
					<c:if test="${session_user_auth==3}"><input class="btn btn-primary btn-login fw-bold" type="button" value="삭제" onclick="location.href='delete.do'"></c:if>
					<input <c:if test="${empty session_user_num}">disabled="disabled"</c:if> class="btn btn-primary btn-login fw-bold" type="button" value="작성" onclick="location.href='qnaWriteForm.do'"> 
					<input class="btn btn-secondary btn-login fw-bold" type="button" value="목록" onclick="location.href='qnaList.do';">
				</div>
			</div>
		</div>
		
	</div>
</div>
<p>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
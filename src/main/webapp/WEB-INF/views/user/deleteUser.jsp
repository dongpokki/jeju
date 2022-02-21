<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${check}">
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>회원탈퇴</title>
	<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
	</head>
	<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<!-- 본문 영역 -->
			<div class="container">
				<div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
						<div class="card border-0 shadow rounded-3 my-5">
							<div class="card-body p-4 p-sm-5">
								<h5 class="card-title text-center mb-5 fw-light fs-5">회원탈퇴 완료</h5>
								<div class="d-grid gap-2 col-6 mx-auto" style="text-align: center;"> 
									<input class="btn btn-secondary btn-login text-uppercase fw-bold" type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do';">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 본문 영역 -->

	<!-- 푸터 시작 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!-- 푸터 끝 --> 
	</body>
	</html>
</c:if>
<c:if test="${!check}">
	<script type="text/javascript">
		alert('아이디 또는 비밀번호 불일치');
		history.go(-1);
	</script>
</c:if>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
	<!-- 헤더 시작 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 헤더 끝 -->
				
	<!-- 중앙 컨텐츠 시 작 -->	
	<div class="container">

		<!-- 메인배너 시작-->
		<div class="main-banner">
		</div>
		<!-- 메인배너 끝 -->	
		
		<!-- 베스트 스팟 시작 -->	
		<h1 class="text-primary">BEST SPOT</h1>
		<div class="row">
			<c:if test="${empty spot_list}">
				<div class="alert alert-warning" style="width:100%;">등록된 추천 장소가 없습니다.</div>
			</c:if>
			<c:if test="${!empty spot_list}">
				<c:forEach var="spot" items="${spot_list}">
					<div class="col-sm-6 col-lg-4">
						<h3 class="best-title">${spot.title}</h3>
						<a href="${pageContext.request.contextPath}/spot/spotDetail.do?spot_num=${spot.spot_num}"><img src="${pageContext.request.contextPath}/upload/${spot.filename}" class="img-thumbnail" style="width:300px; height: 300px; object-fit: scale-down;"></a>
						<div class="main-content">${spot.content}</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	
	<p><p>
	
	<!--  푸터 시작  -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!--  푸터 끝  -->
	
</body>
</html>
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
			<c:if test="${empty list}">
				<p class="empty_content">등록된 글이 없습니다.</p>
			</c:if>
			<c:if test="${!empty list}">
				<c:forEach var="spot" items="${list}">
					<div class="col-sm-6 col-lg-4">
						<h3 class="best-title">${spot.title}</h3>
						<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
						<p class="best-content">${spot.content}</p>
		  				<p><a href="${pageContext.request.contextPath}/spot/spotDetail.do?spot_num=${spot.spot_num}" class="btn btn-warning">상세보기 &raquo;</a></p>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<!-- 베스트 스팟 끝 -->	
		<hr class="featurette-divider">
		
		<!-- 베스트 코스 시작 -->	
		<h1 class="text-primary">BEST COURSE</h1>
		<div class="row">
			<c:if test="${empty list}">
				<p class="empty_content">등록된 글이 없습니다.</p>
			</c:if>
			<c:if test="${!empty list}">
				<c:forEach var="spot" items="${list}">
					<div class="col-sm-6 col-lg-4">
						<h3 class="best-title">${spot.title}</h3>
						<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
						<p class="best-content">${spot.content}</p>
		  				<p><a href="${pageContext.request.contextPath}/spot/spotDetail.do?spot_num=${spot.spot_num}" class="btn btn-warning">상세보기 &raquo;</a></p>
					</div>
				</c:forEach>
			</c:if>
		</div>
	<!-- 중앙 컨텐츠 끝 -->
	
	<p><p>
	
	<!--  푸터 시작  -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!--  푸터 끝  -->
	
</body>
</html>
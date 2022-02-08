<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		
	<!-- 메인배너 시작-->
	<div class="jumbotron">
		<div class="container">
  			<h1>Hello, world!</h1>
  			<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  			<p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
		</div>
	</div>
	<!-- 메인배너 끝 -->	
		
	<!-- 중앙 컨텐츠 시 작 -->	
	<div class="container">
		
		<h1 class="text-primary">BEST COURSE</h1>
		<div class="row">
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
		</div>
		
		<hr class="featurette-divider">
		
		<h1 class="text-primary">BEST SPOT</h1>
		<div class="row">
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
			<div class="col-sm-6 col-lg-4">
				<h3>bootstap</h3>
				<img src="${pageContext.request.contextPath}/images/Tulips.jpg" class="img-thumbnail">
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information. </p>
  				<p><a href="#" class="btn btn-warning">상세보기 &raquo;</a></p>
			</div>
		</div>
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
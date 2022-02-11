<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 장소 상세 페이지</title>

<!-- css  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 사진 섹션 시작 -->
	<div class="container mt-5" style="width: 1300px !important;">
		<div class="page-banner">
			<div class="row justify-content-center align-items-center h-100" style="border-radius: 30px;background-image: URL(${pageContext.request.contextPath}/images/${spot.category }.jpg)">
				<div class="col-md-6" style="position: absolute;">
					<c:choose>
						<c:when test="${spot.category ==0}">
							<h1 class="text-center">JEJU</h1>
						</c:when>
						<c:when test="${spot.category ==1}">
							<h1 class="text-center">EAST</h1>
						</c:when>
						<c:when test="${spot.category ==2}">
							<h1 class="text-center">WEST</h1>
						</c:when>
						<c:when test="${spot.category ==3}">
							<h1 class="text-center">SOUTH</h1>
						</c:when>
						<c:when test="${spot.category ==4}">
							<h1 class="text-center">NORTH</h1>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<!-- 사진 섹션 끝 -->
	<!-- 컨테이너 시작 -->
	<main>
		<!-- 카테고리 표시 -->
		<div class="page-section pt-5">
			<div class="container" style="width: 1300px !important">
				<nav aria-label="Breadcrumb">
					<ul class="breadcrumb p-0 mb-0 bg-transparent">
						<li class="breadcrumb-item"><a href="spotList.do?category=0" <c:if test="${spot.category<1}">style="color:#FE9A2E;"</c:if>>전체</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=1" <c:if test="${spot.category==1 }">style="color:#FE9A2E;"</c:if>>동부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=2" <c:if test="${spot.category==2 }">style="color:#FE9A2E;"</c:if>>서부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=3" <c:if test="${spot.category==3 }">style="color:#FE9A2E;"</c:if>>남부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=4" <c:if test="${spot.category==4 }">style="color:#FE9A2E;"</c:if>>북부</a></li>
					</ul>
				</nav>
				<div class="row">
					<!-- 게시글 시작 -->
					<div class="col-lg-9">
						<div class="blog-single-wrap">
							<div class="header">
								<div class="post-thumb">
									<img src="${pageContext.request.contextPath }/upload/${spot.filename}">
								</div>
							</div>
							<h1 class="post-title">${spot.title }</h1>
							<div class="post-meta">
								<div class="post-date">${spot.reg_date }</div>
								<div class="post-comment-count ml-2">
									<a href="#">덧글 수(누르면 덧글 창으로 이동)</a>
								</div>
							</div>
							<div class="post-content">${spot.content }</div>
						</div>
						<div>
							<p align="right">(좋아요 버튼)</p>
						</div>
						<!-- 덧글 시작 -->
						<div class="comment-form-wrap pt-5">
							<h5>comment</h5>
							<div class="comment-list">
								<div class="single-comment justify-content-btween d-flex">
									<div class="user justify-content-btween d-flex">
										<div class="desc">
											<div class="d-flex justify-content-between">
												<div class="d-flex align-items-center">
													<h6>닉네임</h6>
													<p class="date">2022-02-06</p>
												</div>
											</div>
											<p class="comment">여기 어쩌구 저쩌구 해서 그렇게 좋지는 않았어요</p>
										</div>
									</div>
								</div>
								<div align="right">
									<a href="#" class="btn-reply">수정</a> <a href="#" class="btn-reply">삭제</a>
								</div>
							</div>
							<div class="comment-form">
								<form class="form-contact comment_form" action="#" id="commentForm">
									<div class="row">
										<div class="col-12">
											<div class="form-group">
												<textspot class="form-control w-100" name="comment" id="comment" cols="10" rows="2" placeholder="내용을 입력해주세요."></textspot>
											</div>
										</div>
									</div>
									<div class="form-group">
										<input type="submit" value="덧글 작성" class="btn btn-primary">
									</div>
								</form>
							</div>
							<div align="right">
								<c:if test="${session_user_auth == 3}">
									<input class="btn btn-primary" type="button" value="수정" onclick="location.href='spotUpdateForm.do?spot_num=${spot.spot_num}'" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">

									<input class="btn btn-secondary" type="button" value="삭제" id="delete_btn" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
									<script type="text/javascript">
										let delete_btn = document
												.getElementById('delete_btn');
										delete_btn.onclick = function() {
											let choice = confirm('삭제하시겠습니까?');
											if (choice) {
												location
														.replace('spotDelete.do?spot_num=${spot.spot_num}');
											}
										};
									</script>
								</c:if>
							</div>
						</div>
						<!-- 덧글 끝 -->
					</div>
					<!-- 게시글 끝 -->
					<!-- 사이드 바 시작 -->
					<div class="col-lg-3">
						<div class="widget">
							<!-- Widget Categories -->
							<div class="widget-box">
								<h4 class="widget-title">Category</h4>
								<div class="divider"></div>

								<ul class="categories">
									<li><a href="#">전체</a></li>
									<li><a href="#">동부</a></li>
									<li><a href="#">서부</a></li>
									<li><a href="#">남부</a></li>
									<li><a href="#">북부</a></li>
								</ul>
							</div>
							<!-- Widget search -->
							<div class="widget-box">
								<form action="#" class="search-widget">
									<input type="text" class="form-control" placeholder="Enter keyword..">
									<button type="submit" class="btn btn-primary btn-block">Search</button>
								</form>
							</div>
						</div>
					</div>
					<!-- 사이드 바 끝 -->
				</div>
			</div>
		</div>
	</main>
	<!-- 컨테이너 끝 -->
	<!-- 	footer -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>





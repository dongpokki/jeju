<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 장소 상세 페이지</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
// 감삼다 
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 사진 들어올 부분 -->
	<div class="container mt-5">
		<div class="page-banner">
			<div class="row justify-content-center align-items-center h-100">
				<div class="col-md-6">
					<nav aria-label="Breadcrumb">
						<ul class="breadcrumb justify-content-center py-0 bg-transparent">
							<li class="breadcrumb-item active">Recommend Places</li>
						</ul>
					</nav>
					<h1 class="text-center">Jeju</h1>
				</div>
			</div>
		</div>
	</div>
	<!-- 컨테이너 시작 -->
	<main>
		<!-- 카테고리 표시 -->
		<div class="page-section pt-5">
			<div class="container">
				<nav aria-label="Breadcrumb">
					<ul class="breadcrumb p-0 mb-0 bg-transparent">
						<li class="breadcrumb-item"><a href="areaList.do">전체</a></li>
						<li class="breadcrumb-item"><a href="#">동부/서부/남부/북부</a></li>
						<li class="breadcrumb-item active">카테고리 1</li>
					</ul>
				</nav>

				<div class="row">
					<!-- 게시글 시작 -->
					<div class="col-lg-8">
						<div class="blog-single-wrap">
							<div class="header">
								<div class="post-thumb">
									<img src="${pageContext.request.contextPath }/upload/${area.filename}">
								</div>
							</div>
							<h1 class="post-title">${area.title }</h1>
							<div class="post-meta">
								<div class="post-date">${area.reg_date }</div>
								<div class="post-comment-count ml-2">
									<a href="#">덧글 수(누르면 덧글 창으로 이동)</a>
								</div>
							</div>
							<div class="post-content">
								<p>${area.content }</p>
							</div>
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
												<textarea class="form-control w-100" name="comment" id="comment" cols="10" rows="2" placeholder="내용을 입력해주세요."></textarea>
											</div>
										</div>
									</div>
									<div class="form-group">
										<input type="submit" value="덧글 작성" class="btn btn-primary">
									</div>
								</form>
							</div>
							<div align="right">
								<a href="areaModifyForm.do?spot_num=${area.spot_num}">수정</a> <a href="areaDeleteForm.do?spot_num=${area.spot_num}">삭제</a>
							</div>
						</div>
						<!-- 덧글 끝 -->
					</div>
					<!-- 게시글 끝 -->
					<!-- 사이드 바 시작 -->
					<div class="col-lg-4">
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

	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>





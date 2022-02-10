<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 장소 페이지</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 사진 섹션 시작 -->
	<div class="container mt-5">
		<div class="page-banner">
			<div class="row justify-content-center align-items-center h-100">
				<img src="${pageContext.request.contextPath}/images/${category }.jpg" style="border-radius: 30px; width:100%; max-height: 320px;">
				<div class="col-md-6" style="position: absolute;">
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
	<!-- 사진 섹션 끝 -->
	<main>
		<div class="page-section">
			<div class="container">
				<form class="search-widget" align="right">
					<c:if test="${!empty param.category}">
						<input type="hidden" name="category" value="${param.category}">
					</c:if>
					<c:if test="${empty param.category}">
						<input type="hidden" name="category" value="0">
					</c:if>
					<input type="text" class="form-control" placeholder="검색어를 입력해주세요" value="${param.keyword}" name="keyword" id="keyword">
					<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px;">검색</button>
				</form>
				<nav aria-label="Breadcrumb">
					<ul class="breadcrumb p-0 mb-0 bg-transparent">
						<li class="breadcrumb-item"><a href="spotList.do?category=0" <c:if test="${category<1}">style="color:#FE9A2E;"</c:if>>전체</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=1" <c:if test="${category==1 }">style="color:#FE9A2E;"</c:if>>동부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=2" <c:if test="${category==2 }">style="color:#FE9A2E;"</c:if>>서부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=3" <c:if test="${category==3 }">style="color:#FE9A2E;"</c:if>>남부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=4" <c:if test="${category==4 }">style="color:#FE9A2E;"</c:if>>북부</a></li>
					</ul>
				</nav>
				<div class="row">

					<!-- 등록된 내용이 없을 경우 -->
					<c:if test="${count == 0}">
						<p align="center">등록된 글이 없습니다.</p>
					</c:if>
					<!-- 게시글 목록 시작 -->
					<c:if test="${count > 0}">
						<c:forEach var="spot" items="${list}">
							<div class="col-md-6 col-lg-4 py-3">

								<div class="card-blog">
									<div class="body">
										<div class="post-title">
											<a href="spotDetail.do?spot_num=${spot.spot_num}">${spot.title }</a>
										</div>
										<div class="post-excerpt">${spot.content }</div>
									</div>
									<div class="footer">
										<a href="spotDetail.do?spot_num=${spot.spot_num}">Read More</a>
									</div>
								</div>

							</div>
						</c:forEach>
					</c:if>
					<!-- 게시글 목록 끝 -->
					<!-- 페이지 시작 -->
					<div class="col-12 mt-5">
						<nav aria-label="Page Navigation">
							<ul class="pagination justify-content-center">
								<div align="center">${pagingHtml}</div>
							</ul>
						</nav>
					</div>
					<!-- 페이지 끝 -->

				</div>
				<!-- 관리자만 등록/삭제 버튼 보이게 -->
				<c:if test="${session_user_auth == 3}">
					<div align="right">
						<input class="btn btn-primary" type="button" value="등록" onclick="location.href='spotWriteForm.do'"> <input class="btn btn-secondary" type="button" value="삭제" onclick="location.href='spotDeleteForm.do';">
					</div>
				</c:if>
			</div>

		</div>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>




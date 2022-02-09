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
	<!-- 사진 섹션 끝 -->
	<main>
		<div class="page-section">
			<div class="container">
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




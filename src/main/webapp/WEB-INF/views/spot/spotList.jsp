<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 장소 페이지</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 사진 섹션 시작 -->
	<div class="container mt-5" style="width: 1300px !important;">
		<div class="page-banner">
			<div class="row justify-content-center align-items-center h-100" style="border-radius: 30px;background-image: URL(${pageContext.request.contextPath}/images/${category }.jpg)">
				<div class="col-md-6" style="position: absolute;">
					<c:choose>
						<c:when test="${category ==0}">
							<h1 class="text-center">JEJU</h1>
						</c:when>
						<c:when test="${category ==1}">
							<h1 class="text-center">EAST</h1>
						</c:when>
						<c:when test="${category ==2}">
							<h1 class="text-center">WEST</h1>
						</c:when>
						<c:when test="${category ==3}">
							<h1 class="text-center">SOUTH</h1>
						</c:when>
						<c:when test="${category ==4}">
							<h1 class="text-center">NORTH</h1>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<!-- 사진 섹션 끝 -->
	<main>
		<div class="page-section">
			<div class="container" style="width: 1300px !important;">
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
				<nav aria-label="Breadcrumb" style="display: flex; justify-content: space-between; margin-top: 10px;">
					<ul class="breadcrumb p-0 mb-0 bg-transparent">
						<li class="breadcrumb-item"><a href="spotList.do?category=0" <c:if test="${category<1}">style="color:#FE9A2E;"</c:if>>전체</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=1" <c:if test="${category==1 }">style="color:#FE9A2E;"</c:if>>동부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=2" <c:if test="${category==2 }">style="color:#FE9A2E;"</c:if>>서부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=3" <c:if test="${category==3 }">style="color:#FE9A2E;"</c:if>>남부</a></li>
						<li class="breadcrumb-item"><a href="spotList.do?category=4" <c:if test="${category==4 }">style="color:#FE9A2E;"</c:if>>북부</a></li>
					</ul>
					<form action="spotList.do" method="post">
						<c:if test="${!empty param.category}">
							<input type="hidden" name="category" value="${param.category}">
						</c:if>
						<c:if test="${empty param.category}">
							<input type="hidden" name="category" value="0">
						</c:if>
						<c:if test="${!empty param.keyword}">
							<input type="hidden" name="keyword" value="${param.keyword}">
						</c:if>
						<select name="sort" class="nice-select" onChange="this.form.submit()" style="border-color: transparent; margin: 0; height: none; padding: 0;">
							<option <c:if test="${empty param.sort }">selected</c:if> value="spot_num">최신순</option>
							<option <c:if test="${param.sort eq'hit' }">selected</c:if> value="hit">조회수순</option>
							<option <c:if test="${param.sort eq'good' }">selected</c:if> value="good">좋아요순</option>
						</select>
					</form>
				</nav>
				<div class="row">

					<!-- 등록된 내용이 없을 경우 -->
					<c:if test="${count == 0}">
						<p style="text-align: center">등록된 글이 없습니다.</p>
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
										<div class="post-excerpt" style="max-height: 200px !important;">
											<a href="spotDetail.do?spot_num=${spot.spot_num}"><c:if test="${!empty spot.filename }">
													<img src="${pageContext.request.contextPath }/upload/${spot.filename}" width="100%">
												</c:if></a>
										</div>
										<div class="post-excerpt" style="padding: 15px 0 0 0;">${spot.content }</div>
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
						<nav aria-label="Page Navigation" style="margin-top: 2rem;">
							<ul class="pagination justify-content-center">
								<div align="center">${pagingHtml}</div>
							</ul>
						</nav>
					</div>
					<!-- 페이지 끝 -->
				</div>
				<!-- 관리자만 등록/삭제 버튼 보이게 -->
				<c:if test="${session_user_auth == 3}">
					<div class="col-12 mt-5" align="right">
						<input class="btn btn-primary" type="button" value="등록" onclick="location.href='spotWriteForm.do'" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;"> <input class="btn btn-secondary" type="button" value="삭제" onclick="location.href='spotDeleteForm.do';" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
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




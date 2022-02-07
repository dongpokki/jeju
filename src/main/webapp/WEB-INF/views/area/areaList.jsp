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
	<c:if test="${count == 0}">
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
	</c:if>

	<main>
		<div class="page-section">
			<div class="container">
				<div class="row">
					<c:if test="${count > 0}">
						<c:forEach var="area" items="${list}">
							<div class="col-md-6 col-lg-4 py-3">

								<div class="card-blog">
									<div class="body">
										<div class="post-title">
											<a href="areaDetail.do?board_spot_num=${area.board_spot_num}">${area.title }</a>
										</div>
										<div class="post-excerpt">${area.content }</div>
									</div>
									<div class="footer">
										<a href="areaDetail.do?board_spot_num=${area.board_spot_num}">Read More</a>
									</div>
								</div>

							</div>
						</c:forEach>
					</c:if>
					<div class="col-12 mt-5">
						<nav aria-label="Page Navigation">
							<ul class="pagination justify-content-center">
								<div align="center">${pagingHtml}</div>
							</ul>
						</nav>
					</div>

				</div>
				<div align="right">
					<a href="areaDetail.do?board_spot_num=${area.board_spot_num}">등록</a> <a href="areaDetail.do?board_spot_num=${area.board_spot_num}">삭제</a>
				</div>
			</div>

		</div>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>





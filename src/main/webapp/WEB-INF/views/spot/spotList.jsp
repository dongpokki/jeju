<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 장소 페이지</title>

<!-- css  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function deleteValue() {
		var valueArr = new Array();
		var list = $("input[name='RowCheck']");
		for (var i = 0; i < list.length; i++) {
			if (list[i].checked) { //선택되어 있으면 배열에 값을 저장함
				valueArr.push(list[i].value);
			}
		}
		if (valueArr.length == 0) {
			alert("선택된 글이 없습니다.");
		} else {
			var chk = confirm("정말 삭제하시겠습니까?");
			if (chk == true) {// 확인
				$.ajax({
					url : 'spotDelete.do',
					type : 'POST',
					traditional : true,
					data : {
						valueArr : valueArr
					},
					success : function(jdata) {
						if (jdata = 1) {
							alert("게시글을 성공적으로 삭제했습니다.");
							location.replace("spotList.do") // 목록 페이지로 리다이렉트
						} else {
							alert("네트워크 오류 발생");
						}
					}
				});
			} else {// 취소
				return false;
			}
		}
	}﻿
</script>
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
						<div class="no_list">
							<p>등록된 글이 없습니다.</p>
						</div>
					</c:if>
					<!-- 게시글 목록 시작 -->
					<c:if test="${count > 0}">
						<c:forEach var="spot" items="${list}">
							<div class="col-md-6 col-lg-4 py-3">
								<input ﻿ name="RowCheck" type="checkbox" value="${spot.spot_num}" />
								<div class="card-blog">
									<div class="body">
										<div class="post-title">
											<a href="spotDetail.do?spot_num=${spot.spot_num}">${spot.title }</a>
											<p class="post-category">
												<c:choose>
													<c:when test="${spot.category ==1}">&lt;동부&gt; 
													</c:when>
													<c:when test="${spot.category ==2}">&lt;서부&gt;
													</c:when>
													<c:when test="${spot.category ==3}">&lt;남부&gt;
													</c:when>
													<c:when test="${spot.category ==4}">&lt;북부&gt;
													</c:when>
												</c:choose>
											</p>
										</div>
										<div class="post-excerpt" style="max-height: 200px !important;">
											<a href="spotDetail.do?spot_num=${spot.spot_num}"> <c:if test="${!empty spot.filename }">
													<div class="image-box">
														<img class="image-thumbnail" src="${pageContext.request.contextPath }/upload/${spot.filename}">
													</div>
												</c:if>
											</a>
										</div>
										<div class="post-excerpt">
											<a href="spotDetail.do?spot_num=${spot.spot_num}">${spot.content }</a>
										</div>
										<div class="footer">
											<div style="color: #898798;">
												<img src="${pageContext.request.contextPath }/images/eyes.png" style="padding: 0 5px 1px 0;">${spot.hit }
											</div>
										</div>
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
						<input class="btn btn-primary" type="button" value="등록" onclick="location.href='spotWriteForm.do'" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;"> <input class="btn btn-secondary" type="button" value="삭제" onclick="deleteValue();" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
					</div>
				</c:if>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>




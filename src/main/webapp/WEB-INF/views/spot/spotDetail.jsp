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
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/spot_cmt.js"></script>

</head>
<script>
$(function(){
	let user_num = ${user_num };
	let checked = ${checked};
	$('#good').click(function() {
		 if (user_num == 0) { // 로그인 안 한 상태에서 좋아요 눌렀을 경우
			alert('좋아요는 로그인 한 사용자만 가능합니다.');
			return;
		} 
		if (user_num != 0) {
			$.ajax({
				url : 'spotGood.do',
				type: 'post',
				dataType: 'json',
				data : {spot_num: ${spot.spot_num}, checked :checked},
				success:function(param){
					if (param.result == 'success') { // 좋아요
						$('#good').css('color','#FE9A2E');
						$('#good_result').text(param.good_result);
					}else if (param.result == 'cancel') { // 좋아요 취소
						$('#good').css('color','#000000');
						$('#good_result').text(param.good_result);
					}
				}
			});
		}
	});	
});
</script>
<script type="text/javascript" charset="utf-8">
/* 댓글에 띄울 프로필 사진의 상대경로 값을 js로 넘기기 위한 작업 */
sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
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
							<h2 class="post-title">${spot.title }</h2>
							<div class="post-meta">
								<div class="post-date">${spot.reg_date }</div>
							</div>
							<div class="post-content">
								<c:if test="${!empty spot.filename }">
									<img src="${pageContext.request.contextPath }/upload/${spot.filename}" style="display: block; margin: auto; max-width: 690px">
								</c:if>
								<div style="padding: 20px 10px 0 10px;">${spot.content }</div>
								<c:if test="${!empty spot.course }">
									<img src="${spot.course }" style="display: block; margin: auto;">
								</c:if>
							</div>
						</div>
						<div align="right" style="padding: 0 20px 0 0;">
							<input type="button" value="♡" id="good" <c:if test="${checked==1}">style="color:#FE9A2E;"</c:if>>
							<div id="good_result" style="display: inline;">${good }</div>
						</div>
						<!-- 댓글 시작 -->
						<div class="comments-area">
							<h4>댓글</h4>
							<!-- 댓글 목록 출력 시작 -->
							<div class="comment-list" id="output"></div>
							<!-- 댓글 목록 출력 끝 -->
							<div <c:if test="${cmt_count > 0 }">class="comment-form"</c:if>>
								<form class="form-contact comment_form" id="cmt_form2" style="">
									<input type="hidden" name="spot_num" value="${spot.spot_num}" id="spot_num">
									<div class="row">
										<div class="col-12">
											<textarea class="form-control w-100" name="cmt_content" id="cmt_content" cols="10" rows="2" placeholder="내용을 입력해주세요." <c:if test="${empty session_user_num}">disabled="disabled"</c:if>><c:if test="${empty session_user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
										</div>
									</div>
									<c:if test="${!empty session_user_num}">
										<div id="cmt_first" style="display: flex; justify-content: space-between;">
											<div class="form-group">
												<input type="submit" value="덧글 등록" class="btn btn-primary" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
											</div>
											<span class="letter-count">100/100</span>
										</div>
									</c:if>
								</form>
							</div>
						</div>
						<!-- 댓글 끝 -->
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
							<input class="btn btn-secondary" type="button" value="목록" onclick="location.href='spotList.do'" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
						</div>
					</div>
					<!-- 게시글 끝 -->
					<!-- 사이드 바 시작 -->
					<div class="col-lg-3">
						<div class="widget">
							<!-- 위젯 카테고리 -->
							<div class="widget-box">
								<h4 class="widget-title">Category</h4>
								<div class="divider"></div>
								<ul class="categories">
									<li><a href="spotList.do?category=0" <c:if test="${spot.category<1}">style="color:#FE9A2E;"</c:if>>전체</a></li>
									<li><a href="spotList.do?category=1" <c:if test="${spot.category==1 }">style="color:#FE9A2E;"</c:if>>동부</a></li>
									<li><a href="spotList.do?category=2" <c:if test="${spot.category==2 }">style="color:#FE9A2E;"</c:if>>서부</a></li>
									<li><a href="spotList.do?category=3" <c:if test="${spot.category==3 }">style="color:#FE9A2E;"</c:if>>남부</a></li>
									<li c><a href="spotList.do?category=4" <c:if test="${spot.category==4 }">style="color:#FE9A2E;"</c:if>>북부</a></li>
								</ul>
							</div>
							<!-- 위젯 검색 -->
							<div class="widget-box">
								<form action="spotList.do" class="search-widget">
										<input type="hidden" name="category" value="0">
									<input type="text" class="form-control" placeholder="검색어를 입력해주세요" value="${param.keyword}" name="keyword" id="keyword" style="width: 100% !important;">
									<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px; width: 100% !important;">검색</button>
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
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>





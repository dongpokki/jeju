<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage_script.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<!-- 중앙 컨텐츠 시작 -->	
	<div class="container">
		<h1 class="text-primary" style="margin-top:20px;">마이 페이지</h1>
		<div class="row">
			<div class="col-sm-4 col-lg-4 p-3 mb-2 bg-light text-dark">
				<c:if test="${empty user.photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" width="250" height="250" class="my-photo">
				</c:if>
				<c:if test="${!empty user.photo}">
					<img src="${pageContext.request.contextPath}/upload/${user.photo}" width="250" height="250" class="my-photo">
				</c:if>
				<div class="align-center" style="width:250px;" id="update_delete">
					<input type="button" value="수정" id="photo_btn" class="btn btn-warning">
					<c:if test="${!empty user.photo}">
					<input type="button" value="삭제" id="delete_btn" class="btn btn-warning">
					</c:if>
				</div>
				<div id="photo_choice" style="display:none">
					<input type="file" id="photo" accept="image/gif,image/png,image/jpeg">
					<div class="align-center">
						<input type="button" value="전송" id="photo_submit" class="btn btn-warning">
						<input type="button" value="취소" id="photo_reset" class="btn btn-warning" style="margin-right: 55px;"> 
					</div>
				</div>
			</div>
			<div class="col-sm-8 col-lg-8 p-3 mb-2 bg-light text-dark">
				<h4>
					<b>${user.id}</b>님 안녕하세요!
				</h4>
				<div class="mypage-info-warrap">
					<div>
						<span class="mypage-info">이름</span>${user.name}
					</div>
					<div>
						<span class="mypage-info">전화번호</span>${user.phone}
					</div>
					<div>
						<span class="mypage-info">이메일</span>${user.email}
					</div>
					<div>
						<span class="mypage-info">주소</span>(${user.zipcode})${user.address1} ${user.address2}
					</div>
				</div>
				<div style="text-align: center;">
					<input type="button" value="회원정보 수정" onclick="location.href='modifyUserForm.do';" class="btn btn-secondary">
					<input type="button" value="비밀번호 수정" onclick="location.href='modifyPasswordForm.do';" class="btn btn-secondary">
					<input type="button" value="회원탈퇴" onclick="location.href='deleteUserForm.do';" class="btn btn-secondary">
				</div>
			</div>
		</div>

		<hr class="featurette-divider">

		<!-- 내가 추천한 장소 시작 -->
		<h1 class="text-primary" >내가 추천한 장소</h1>
		<div class="row" id="spot_output"></div>
		<div class="spot_paging-button" style="display: none">
			<input type="button" value="더보기" class="btn btn-outline-dark" style="width:100%;">
		</div>
		<div id="spot_loading" style="display: none">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 내가 추천한 장소 끝 -->
		
		<hr class="featurette-divider">

		<!-- 내가 추천한 코스 시작 -->
		<h1 class="text-primary" >내가 추천한 코스</h1>
		<div class="row" id="mygoodcourse_output"></div>
		<div class="mygoodcourse_paging-button" style="display: none">
			<input type="button" value="더보기" class="btn btn-outline-dark" style="width:100%;">
		</div>
		<div id="mygoodcourse_loading" style="display: none">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 내가 추천한 코스 끝 -->

		<hr class="featurette-divider">
		
		<!-- 내가 작성한 게시판 시작 -->
		<h1 class="text-primary" >내가 작성한 게시글</h1>
		<div class="row" id="mywriteboard_output"></div>
		<div class="mywriteboard_paging-button" style="display: none">
			<input type="button" value="더보기" class="btn btn-outline-dark" style="width:100%;">
		</div>
		<div id="mywriteboard_loading" style="display: none">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 내가 작성한 게시판 끝 -->

	<hr class="featurette-divider">

		<!-- 내가 작성한 문의내역 시작 -->
		<h1 class="text-primary" >내가 작성한 문의내역</h1>
		<div class="row" id="myqna_output"></div>
		<div class="myqna_paging-button" style="display: none">
			<input type="button" value="더보기" class="btn btn-outline-dark" style="width:100%;">
		</div>
		<div id="myqna_loading" style="display: none">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 내가 작성한 문의내역 끝 -->

	</div>
	<!-- 중앙 컨텐츠 끝 -->

	<p><p>

	<!-- 푸터 시작 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!-- 푸터 끝 -->
</body>
</html>
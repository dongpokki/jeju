<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.nav-item {margin:0px 30px 0px 30px;}
</style>
<!-- header 시작 -->
<header>
	<nav class="navbar navbar-expand-lg navbar-light shadow-sm">
		<div class="container">
			<a href="${pageContext.request.contextPath}/main/main.do" class="navbar-brand">JE<span class="text-primary">JU</span></a>

			<button class="navbar-toggler" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse collapse" id="navbarContent">
				<ul class="navbar-nav mx-4">
					<li class="nav-item"><a href="${pageContext.request.contextPath}/main/main.do" class="nav-link">메인</a></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">추천 장소</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown" style="margin:0px 30px 0px 30px;">
							<a class="dropdown-item" href="${pageContext.request.contextPath}/spot/spotList.do?category=0">전체</a> 
							<a class="dropdown-item" href="${pageContext.request.contextPath}/spot/spotList.do?category=1">동부</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath}/spot/spotList.do?category=2">서부</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath}/spot/spotList.do?category=3">남부</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath}/spot/spotList.do?category=4">북부</a>
						</div></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/board/boardList.do" class="nav-link">자유게시판</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath }/qna/qnaList.do" class="nav-link">문의사항</a></li>
				</ul>

				<div class="ml-auto">
					<%-- 
					[2022.02.07 정동윤 - 사진 첨부 시 너무 안보여서 우선 주석 처리 했습니다.]
					<c:if test="${!empty session_user_num && !empty session_user_photo}"> <!-- user_num,user_photo가 있는경우 (로그인이 되어있고 사진이 있는경우) -->
						<img src="${pageContext.request.contextPath}/upload/${session_user_photo}" width="25" height="25" class="my-photo">
					</c:if>
					<c:if test="${!empty session_user_num && empty session_user_photo}"> <!-- 사진은 없지만, 로그인 된 경우 -->
						<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					 --%>
					<c:if test="${!empty session_user_num}">
						<!--  로그인 된 경우 -->
						[<b><span>${session_user_id}</span></b>]
						<a href="${pageContext.request.contextPath}/user/logout.do" class="btn btn-outline ">로그아웃</a>
					</c:if>

					<c:if test="${empty session_user_num}">
						<!--  로그인 안되어 있는 경우 -->
						<a href="${pageContext.request.contextPath}/user/loginForm.do" class="btn btn-outline ">로그인</a>
						<a href="${pageContext.request.contextPath}/user/registerUserForm.do" class="btn btn-outline ">회원가입</a>
					</c:if>

					<c:if test="${!empty session_user_num && session_user_auth == 2}">
						<!--  일반 회원으로 로그인 한 경우 -->
						<a href="${pageContext.request.contextPath}/user/myPage.do" class="btn btn-outline ">내 정보</a>
					</c:if>

					<c:if test="${!empty session_user_num && session_user_auth == 3}">
						<!--  관리자로 로그인 한 경우 -->
						<a href="${pageContext.request.contextPath}/user/userList.do" class="btn btn-outline ">회원 관리</a>
					</c:if>
				</div>

			</div>
		</div>
	</nav>
</header>
<!-- header 끝 -->








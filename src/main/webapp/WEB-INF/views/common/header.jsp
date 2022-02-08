<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- header 시작 -->
<header>
	<nav class="navbar navbar-expand-lg navbar-light shadow-sm">
		<div class="container">
			<a href="${pageContext.request.contextPath}/main/main.do" class="navbar-brand">JE<span class="text-primary">JU</span></a>

			<button class="navbar-toggler" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse collapse" id="navbarContent">
				<ul class="navbar-nav ml-lg-4 pt-3 pt-lg-0">
					<li class="nav-item"><a href="${pageContext.request.contextPath}/main/main.do" class="nav-link">Main</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/area/areaList.do" class="nav-link">Recommend Places</a></li>
					<li class="nav-item"><a href="#" class="nav-link">Recommend Courses</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath }/qna/qnaList.do" class="nav-link">Contact</a></li>
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
					<c:if test="${!empty session_user_num}"> <!--  로그인 된 경우 -->
						[<b><span>${session_user_id}</span></b>]
						<a href="${pageContext.request.contextPath}/user/logout.do" class="btn btn-outline ">Logout</a>
					</c:if>
					
					<c:if test="${empty session_user_num}"> <!--  로그인 안되어 있는 경우 -->
						<a href="${pageContext.request.contextPath}/user/loginForm.do" class="btn btn-outline ">Login</a>
						<a href="${pageContext.request.contextPath}/user/registerUserForm.do" class="btn btn-outline ">Join</a>
					</c:if>
					
					<c:if test="${!empty session_user_num && session_user_auth == 2}"> <!--  일반 회원으로 로그인 한 경우 -->
						<a href="${pageContext.request.contextPath}/user/myPage.do" class="btn btn-outline ">MY page</a>
					</c:if>
					
					<c:if test="${!empty session_user_num && session_user_auth == 3}"> <!--  관리자로 로그인 한 경우 -->
						<a href="${pageContext.request.contextPath}/user/userList.do" class="btn btn-outline ">User management</a>
					</c:if>
				</div>
				
			</div>
		</div>
	</nav>
</header>
<!-- header 끝 -->








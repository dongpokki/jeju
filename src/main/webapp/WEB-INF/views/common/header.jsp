<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- header 시작 -->
<header>
	<nav class="navbar navbar-expand-lg navbar-light shadow-sm">
		<div class="container">
			<a href="${pageContext.request.contextPath}/main/main.do" class="navbar-brand">어쩌구<span class="text-primary">저쩌구</span></a>

			<button class="navbar-toggler" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse collapse" id="navbarContent">
				<ul class="navbar-nav ml-lg-4 pt-3 pt-lg-0">
					<li class="nav-item"><a href="${pageContext.request.contextPath}/main/main.do" class="nav-link">Main</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/area/areaList.do" class="nav-link">Recommend Places</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/course/courseList.do" class="nav-link">Recommend Courses</a></li>
					<li class="nav-item"><a href="#" class="nav-link">Contact</a></li>
				</ul>

				<div class="ml-auto">
					<c:if test="${!empty user_num}">
		[<span>${user_id}</span>]
		<a href="${pageContext.request.contextPath}/member/logout.do" class="btn btn-outline ">로그아웃</a>
					</c:if>
					<c:if test="${empty user_num}">
						<a href="${pageContext.request.contextPath}/member/login.do" class="btn btn-outline ">로그인</a>
					</c:if>
					<c:if test="${!empty user_num && user_auth == 2}">
						<a href="${pageContext.request.contextPath}/member/myPage.do" class="btn btn-outline ">MY페이지</a>
					</c:if>
					<c:if test="${!empty user_num && user_auth == 3}">
						<a href="${pageContext.request.contextPath}/member/memberList.do" class="btn btn-outline ">회원관리</a>
					</c:if>

				</div>
			</div>
		</div>
	</nav>
</header>
<!-- header 끝 -->








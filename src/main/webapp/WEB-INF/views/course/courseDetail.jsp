<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 코스 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board-reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container">
	<h2>추천 코스 상세</h2>
	<ul>
		<li>글번호 : ${jboard_course.course_num}</li>
		<li>글제목 : ${jborad_course.title}</li>
		<li>조회수 : ${jboard_course.hit}</li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${!empty jboard_course.filename}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${jboard_course.filename}" class="detail-img">
	</div>
	</c:if>
	<p>
		${jboard_course.content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-right">
		<c:if test="${!empty jboard_course.modify_date}">
		최근 수정일 : ${jboard_course.modify_date}
		</c:if>
		작성일 : ${jboard_course.reg_date}
		<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
		<c:if test="${user_num == jboard_course.user_num}">
		<input type="button" value="수정" 
		    onclick="location.href='courseUpdateForm.do?course_num=${jboard_course.course_num}'">
		<input type="button" value="삭제" id="delete_btn">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('courseDelete.do?course_num=${jboard_course.course_num}');
				}
			};
		</script>
		</c:if>
		<input type="button" value="목록" onclick="location.href='courseList.do'">
	</div>
	<!-- 댓글 시작 -->
	<div id="reply_div">
		<span class="re-title">댓글 달기</span>
		<form id="re_form">
			<input type="hidden" name="board_num" value="${jboard_course.course_num}"
			                                                id="board_num">
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content" 
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
			><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
		<c:if test="${!empty user_num}">
		<div id="re_first">
			<span class="letter-count">300/300</span>
		</div>
		<div id="re_second" class="align-right">
			<input type="submit" value="전송">
		</div>
		</c:if>
		</form>
	</div>
	<!-- 댓글 목록 출력 시작 -->
	<div id="output"></div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="다음글 보기">
	</div>
	<div id="loading" style="display:none;">
		<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
	</div>
	</div>
	<!-- 댓글 목록 출력 끝 -->
	<!-- 댓글 끝 -->
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





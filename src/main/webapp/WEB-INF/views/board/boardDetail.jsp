<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board-reply.js"></script>
</head>
<style>
</style>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container">
	<h2 class="text-primary" style="margin: 20px 0px 20px 0px; font-weight: bolder;">${board.board_num} | ${board.title}</h2>
	<p><img src="${pageContext.request.contextPath }/images/people.png" style= "width: 25px; height: 25px;"> ${board.id} | <img src="${pageContext.request.contextPath }/images/eyes.png" style= "width: 25px; height: 25px;"> ${board.hit}</p>
	<p>
	<c:if test="${!empty board.modify_date}">
	<img src="${pageContext.request.contextPath }/images/refresh.png" style= "width: 20px; height: 20px;"> ${board.modify_date}
	</c:if>
	<img src="${pageContext.request.contextPath }/images/writing.png" style= "width: 25px; height: 25px;"> ${board.reg_date}</p>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${!empty board.filename}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img">
	</div>
	</c:if>
	<p style="width:300px; height:500px;">
		${board.content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-right">
		
		<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
		<c:if test="${session_user_num == board.user_num}">
		<input class="btn btn-primary" type="button" value="수정" style="margin-bottom :20px"
		    onclick="location.href='boardUpdateForm.do?board_num=${board.board_num}'">
		<input class="btn btn-secondary" type="button" value="삭제" style="margin-bottom :20px" id="delete_btn">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('boardDelete.do?board_num=${board.board_num}');
				}
			};
		</script>
		</c:if>
		<input class="btn btn-secondary" type="button" value="목록" style="margin-bottom :20px"onclick="location.href='boardList.do'">
	</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





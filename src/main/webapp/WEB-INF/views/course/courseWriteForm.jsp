<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 코스 등록 페이지</title>

<!-- 레이아웃을 위한  -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/theme.css">
<!-- 글작성 api  -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="container">
		<div class="WritingWrap">
			<div class="WritingHeader">
				<h2 class="title">추천 코스 작성하기</h2>
			</div>
			<div class="WritingContent">
				<div class="WritingEditor">
					<div class="ArticleWritingTitle">
						<div class="column_title">
							</div>
						</div>
						</div>
						<div>
							
<button class="btn-primary">장소 추가</button>

<form id="write_form" action="courseWrite.do" method="post"
	                                        enctype="multipart/form-data">
		<ul>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" maxlength="50">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="30" name="content" id="content"></textarea>
			</li>
			<li>
				<label for="filename">파일</label>
				<input type="file" name="filename" id="filename"
				                       accept="image/gif,image/png,image/jpeg">
			</li>
		</ul>
	
						<div class="form-group" align="center">
							<input type="submit" value="등록" class="btn btn-primary">
							<input type="submit" value="취소" class="btn btn-primary"
								onclick="location.href='${pageContext.request.contextPath}/course/courseList.do'">
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>




	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>





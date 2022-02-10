<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
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
	<div class="WritingEditor">
		<div class="WritingHeader">
			<h2 class="title">QnA 작성하기</h2>
		</div>
	</div>
	<form id="write_form" action="qnaWrite.do"  enctype="multipart/form-data" method="post"  >
		<div class="WritingContent" style="text-align:center;padding-top:75px">
			<div class="FlexableTextArea">
			<p>
				<input type="radio" name="viewable_check" value="0" id="viewable_check0" checked>공개
				<input type="radio" name="viewable_check" value="1" id="viewable_check1">비공개
			</p>
				<label for="title" class="col-lg-2">제목</label>
				<div class="FlexableTextArea">
					<input type="text" name="title" id="title" class="textarea_title col-lg-offset-2 col-lg-10">
				</div>
				<label for="content" class="col-lg-2">내용</label>
				<div class="FlexableTextArea">
					<textarea class="textarea_input col-lg-offset-2 col-lg-10" name="content" id="content"></textarea>
				</div>
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
			</div>
			<div class="d-grid gap-2">
					<input class="btn btn-primary btn-login fw-bold" type="submit" value="등록"> 
					<input class="btn btn-secondary btn-login fw-bold" type="button" value="취소" onclick="location.href='qnaList.do';">
			</div>
		</div>
	</form>
	</div>
</body>
</html>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="container">
		<div class="WritingWrap">
			<div class="WritingHeader">
				<h2 class="title">추천 장소 작성하기</h2>
			</div>
			<div class="WritingContent">
				<div class="column_title">
					<form id="write_form" action="spotWrite.do" method="post" enctype="multipart/form-data">
						<select class="nice-select" name="category" id="category">
							<option data-display="Select" class="nice-select" value="">지역을 선택해주세요</option>
							<option value="1">동부</option>
							<option value="2">서부</option>
							<option value="3">남부</option>
							<option value="4">북부</option>
						</select>
				</div>
				<div class="FlexableTextArea">
					<textarea placeholder="제목을 입력해 주세요." class="textarea_input" style="height: 40px;" name="title" id="title"></textarea>
				</div>
				<div class="form-group">
					<textarea id="summernote" name="content"></textarea>
				</div>
				<label for="filename">파일</label> <input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				<div class="form-group" align="center">
					<input type="submit" value="등록" class="btn btn-primary"> <input type="button" value="취소" class="btn btn-primary" onclick="location.href='spotList.do'">
				</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>





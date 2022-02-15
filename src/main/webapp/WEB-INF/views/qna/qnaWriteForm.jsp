<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-section">
		<div class="container">
			<div class="WritingEditor">
				<div class="WritingHeader">
					<h2 class="title">QnA 수정하기</h2>
				</div>
			</div>
			<form id="write_form" action="qnaWrite.do"
				enctype="multipart/form-data" method="post">
				<div class="WritingContent" style="padding-top: 20px">
					<div class="FlexableTextArea">
						<p>
							<input type="radio" name="viewable_check" value="0"
								id="viewable_check0" checked>공개 <input type="radio"
								name="viewable_check" value="1" id="viewable_check1">비공개
						</p>
						<div>
							<div class="FlexableTextArea">
								<input type="text" name="title" id="title" maxlength="50"
									class="textarea_input" placeholder="제목을 입력해 주세요.">
							</div>
							<label for="content" class="col-lg-2">내용</label>
							<div class="form-group">
								<textarea id="summernote" name="content"></textarea>
							</div>
							<input type="file" name="filename"
								accept="image/gif,image/png,image/jpeg">
						</div>

					</div>
					<p>
					<div style="text-align: center">
						<input class="btn btn-primary" type="submit" value="등록"> <input
							class="btn btn-secondary" type="button" value="취소"
							onclick="location.href='qnaList.do';">
					</div>
				</div>
			</form>
		</div>
	</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
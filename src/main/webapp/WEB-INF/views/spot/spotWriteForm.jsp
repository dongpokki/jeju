<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 장소 상세 페이지</title>

<!-- 레이아웃을 위한  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">

<!-- 글작성 api  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">


</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="container">
		<div class="WritingWrap">
			<div class="WritingHeader">
				<h2 class="title">추천 장소 작성하기</h2>
			</div>
			<form id="write_form" action="spotWrite.do" method="post" enctype="multipart/form-data">
				<div class="WritingContent">
					<div class="WritingEditor">
						<div class="ArticleWritingTitle">
							<div class="column_title">
								<div class="FormSelectBox menu_candidates_selectbox">
									<div class="FormSelectButton">
										<button type="button" aria-haspopup="true" aria-expanded="selectboxLayer" aria-pressed="selectboxLayer" class="button">게시판을 선택해 주세요.</button>
									</div>
								</div>
							</div>
							<div>
								<div class="FlexableTextArea">
									<textarea placeholder="제목을 입력해 주세요." class="textarea_input" style="height: 40px;" name="title"></textarea>
								</div>
							</div>
							<div>
								<form id="write_form" action="spotWrite.do" method="post" enctype="multipart/form-data">
									<div class="form-group">
										<textarea id="summernote" name="content"></textarea>
									</div>
								</form>

							</div>
							<div class="form-group" align="center">
								<input type="submit" value="등록" class="btn btn-primary"> <input type="submit" value="취소" class="btn btn-primary" onclick="location.href='spotList.do'">
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>



	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<jsp:include page="/WEB-INF/views/common/writingForm.jsp" />
</body>
</html>





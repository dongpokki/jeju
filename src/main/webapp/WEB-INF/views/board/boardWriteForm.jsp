<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>자유게시판 작성 페이지</title>

<%-- 코스 그리기 --%><!-- 소진님 -->
<script src="${pageContext.request.contextPath}/js/draw_course.js"></script>

<!-- css  -->
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>
<script><!-- 소진님 -->
<!-- 코스그리기 추가되는 부분 -->
	$(function() {
		$('#reviewI-select--course')
				.change(
						function() {
							var selected = $(
									'#reviewI-select--course option:selected')
									.val();

							if (selected === 'yes') {
								$('#reviewI-selectBox')
										.after(
												"<div class='common-tbl__item' id='reviewI-course--title'>"
														+ "<div> <input id='placeTitle' type='text'  placeholder='코스 제목을 입력해 주세요.' class='course_title' style='margin-bottom:20px;'/> "
														+ "<button type='button' class='btn btn-course' onclick='addTitle();' >적용</button> "
														+ "</div> </div> "
														+ "<div class='common-tbl__item' id='reviewI-course--drawcourse'> "
														+ "<div id='writeform__item--course' style='display:flex; align-items: center;'>"
														+ "<input id='placeName' placeholder='코스 내용을 입력해 주세요.' type='text' class='course_title'/> "
														+ "<div style='margin: 0 20px;'><input type='radio' name='placeType' value='1' checked='checked'/>식당/카페"
														+ "<input type='radio' name='placeType' value='2'/>관광"
														+ "<input type='radio' name='placeType' value='3'/>액티비티</div>"
														+ "<div style='margin: 0 5px; color: #808080;'><button type='button' class='btn btn-course' onclick='drawNodeAndLine();' id='insertName'>추가</button>"
														+ "<button type='button' class='btn btn-course' onclick='clearNode();'>초기화</button></div></div> "
														+ "<canvas id='canvas' width='970' height='300' style='width: 100%; margin: 0 0 10px 50px;'></canvas>"
														+ "</div></div></div><button type='button' class='btn btn-course' id='makeImgBtn' onclick='imgtest();'>저장</button>");

								[ x, y ] = drawDefaultNode(x, y);
							} else {
								$('#reviewI-course--title').remove();
								$('#reviewI-course--drawcourse').remove();
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
				<h2 class="title">자유게시판 작성하기</h2>
			</div>
			<div class="WritingContent">
				<div class="column_title">
					<form id="write_form" action="boardWrite.do" method="post" enctype="multipart/form-data">
				</div>
				<div class="FlexableTextArea">
					<textarea placeholder="제목을 입력해 주세요." class="textarea_input" style="height: 40px;" name="title" id="title"></textarea>
				</div>
				<div class="common-tbl__item" id="reviewI-selectBox"><!-- 소진님 -->
					<select id="reviewI-select--course" class="course-select">
						<option value="no">여행 코스 추가 안함</option>
						<option value="yes">여행 코스 추가 하기</option>
					</select>
				</div>
				<div class="form-group">
					<textarea id="summernote" name="content"></textarea>
				</div>
				<label for="filename">파일</label> <input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				<div class="form-group" align="center">
					<input type="submit" value="등록" class="btn btn-primary"> <input type="button" value="취소" class="btn btn-secondary" onclick="location.href='boardList.do'">
					<input type="hidden" value="" id="course" name="course">
				</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>





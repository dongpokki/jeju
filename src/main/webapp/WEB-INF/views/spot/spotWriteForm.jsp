<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>추천 장소 게시글 작성</title>

<%-- 코스 그리기 --%>
<script src="${pageContext.request.contextPath}/js/draw_course.js"></script>

<!-- css  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>

<script>
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
														+ "<button type='button' class='btn btn-course' onclick='addTitle();'>적용</button> "
														+ "</div> </div> "
														+ "<div class='common-tbl__item' id='reviewI-course--drawcourse'> "
														+ "<div id='writeform__item--course' style='display:flex; align-items: center;'>"
														+ "<input id='placeName' placeholder='코스 내용을 입력해 주세요.' type='text' class='course_title'/> "
														+ "<div style='margin: 0 20px;'><input type='radio' name='placeType' value='1' checked='checked'/>식당/카페"
														+ "<input type='radio' name='placeType' value='2'/>관광"
														+ "<input type='radio' name='placeType' value='3'/>액티비티</div>"
														+ "<div style='margin: 0 5px; color: #808080;'><button type='button' class='btn btn-course' onclick='drawNodeAndLine();'>추가</button>"
														+ "<button type='button' class='btn btn-course' onclick='clearNode();'>초기화</button></div></div> "
														+ "<canvas id='canvas' width='970' height='500' style='width: 100%; margin: 0 0 10px 50px;'></canvas>"
														+ "</div></div></div>");

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
				<h2 class="title">추천 장소 작성하기</h2>
			</div>
			<div class="WritingContent">
				<form id="write_form" action="spotWrite.do" method="post" enctype="multipart/form-data">
					<div class="column_title">

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
					<div class="common-tbl__item" id="reviewI-selectBox">
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
						<input type="submit" value="등록" class="btn btn-primary"> <input type="button" value="취소" class="btn btn-primary" onclick="location.href='spotList.do'">
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
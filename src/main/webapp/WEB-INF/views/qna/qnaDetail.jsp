<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${qna.viewable_check==1 && session_user_num != qna.user_num}">
	<script type="text/javascript">
		alert('비공개 글입니다.');
		location.href='qnaList.do';
	</script>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상셍</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="WritingEditor">
			<div class="WritingHeader">
				<h2 class="title">QnA 상세 페이지</h2>
			</div>
			<div>
				<ul>
					<li>글번호: ${qna.qna_num }</li>
					<li><c:if test="${qna.viewable_check==1}">[ 비공개 ]</c:if></li>
					<li>제목 : ${qna.title }</li>
					<li>작성자 : ${qna.name } (<c:choose>
							<c:when test="${fn:length(qna.id) gt 3}">
								<c:out value="${fn:substring(qna.id, 0, 2)}" />
								<c:forEach begin="2" end="${fn:length(qna.id) }">*</c:forEach>
							</c:when>
							<c:otherwise>
								<c:out value="${qna.id}" />
							</c:otherwise>
						</c:choose>)
					</li>
					<li>조회수 : ${qna.hit }</li>
				</ul>
				<p>
					<c:if test="${!empty qna.filename }">
						<div>
							<img alt="파일"
								src="${pageContext.request.contextPath }/upload/${qna.filename}">
						</div>
					</c:if>
					<p>
						${qna.content }
					</p>
				<div>
					<c:if test="${!empty qna.modify_date }">
					최근 수정일 : ${qna.modify_date }
				</c:if>
					작성일 : ${qna.reg_date }
					<c:if test="${session_user_num==qna.user_num }">
						<input type="button" class="btn btn-primary btn-login fw-bold"
							value="수정"
							onclick="location.href='qnaUpdateForm.do?qna_num=${qna.qna_num}'">
						<input type="button" class="btn btn-tertiary btn-login fw-bold"
							value="삭제" id="delete_btn">
<script type="text/javascript">
	let delete_btn = document
			.getElementById('delete_btn');
	//이벤트 연결
	delete_btn.onclick = function() {
		let choice = confirm('삭제하시겠습니까?');
		if (choice) {
			location
					.replace('qnaDelete.do?qna_num=${qna.qna_num}');
		}
	};
</script>
					</c:if>
				</div>
			</div>
		</div>
</div>
</body>
</html>
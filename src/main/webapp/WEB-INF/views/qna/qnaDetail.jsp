<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when
		test="${session_user_auth!=3 && qna.viewable_check==1 && session_user_num!=qna.user_num }">
		<script type="text/javascript">
			alert('비공개 글입니다.');
			location.href = 'qnaList.do';
		</script>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html>
<head>
<meta charset="UTF-8">
<title>Qna 상세</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/qna-cmt.js"></script>
</head>
<body>
	<div class="page-section">
		<div class="container">
			<jsp:include page="/WEB-INF/views/common/header.jsp" />
			<div class="WritingEditor">
				<div class="WritingHeader">
					<h2 class="title">${qna.qna_num }번.QnA 상세 페이지</h2>
				</div>
				<div class="blog-single-wrap" style="padding-top: 28px">
					<h4 class="post-title">
						<c:if test="${qna.viewable_check==1}">[ 비공개 ]</c:if>
						${qna.title }
					</h4>
					<hr size="1" width="100%"
						style="border-top: 2px dashed rgba(0, 0, 0, 0.1)">
					<div class="post-meta">
						<div class="post-name">
							작성자 ${qna.name }
							<c:if test="${qna.id ne 'admin'}">
						(<c:choose>
									<c:when test="${fn:length(qna.id) gt 3}">
										<c:out value="${fn:substring(qna.id, 0, 2)}" />
										<c:forEach begin="3" end="${fn:length(qna.id)}">*</c:forEach>

									</c:when>
									<c:otherwise>
										<c:out value="${qna.id}" />
									</c:otherwise>
								</c:choose>)
						</c:if>
						</div>
						<div class="post-lookup">| 조회 ${qna.hit } |</div>
						<div class="post-date">
							<c:if test="${!empty qna.modify_date }">
						최근 수정일 : ${qna.modify_date }
					</c:if>
							<c:if test="${empty qna.modify_date }">
						작성일 : ${qna.reg_date }
					</c:if>
						</div>
					</div>
					<c:if test="${!empty qna.filename }">
						<div>
							<img alt="파일"
								src="${pageContext.request.contextPath }/upload/${qna.filename}"
								width="100px">
						</div>
					</c:if>
					<div class="post-content">${qna.content }</div>
				</div>
				<hr size="1" width="100%">
				<div style="text-align: right">
					<c:if test="${session_user_num==qna.user_num}">
						<input type="button" class="btn btn-primary" value="수정"
							onclick="location.href='qnaUpdateForm.do?qna_num=${qna.qna_num}'">
					</c:if>
					<c:if test="${session_user_num==qna.user_num || session_user_auth==3 }">
						<input type="button" class="btn btn-tertiary" value="삭제"
							id="delete_btn">
						<script type="text/javascript">
							let delete_btn = document
									.getElementById('delete_btn');
							//이벤트 연결
							delete_btn.onclick = function() {
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location.replace('qnaDelete.do?qna_num=${qna.qna_num}');
								}
							};
						</script>
					</c:if>
					<input type="button" class="btn btn-secondary" value="목록"
						id="delete_btn" onclick="location.href='qnaList.do'">
				</div>
				<!-- 댓글 시작 -->
				<c:if test="${session_user_auth!=3}">
					<form id="cmt_form" class="comment-form" style="">
						<input type="hidden" name="qna_num" value="${qna.qna_num }" id="qna_num">
					</form>
				</c:if>
				<c:if test="${session_user_auth==3}">
					<div id="cmt_div" class="comment-form-wrap">
						<h5>
							<span class="cmt-title">답변 작성</span>
						</h5>
						<form id="cmt_form" class="comment-form">
							<input type="hidden" name="qna_num" value="${qna.qna_num }" id="qna_num">
							<textarea rows="3" cols="50" name="cmt_content" id="cmt_content" class="cmtp-content"></textarea>
							<div id="cmt_first">
								<span class="letter-count">100/100</span>
							</div>
							<div id="cmt_second">
								<input type="submit" value="답변" class="btn btn-secondary"
									onclick="location.href='qnaDqnaDetail.do?qna_num='${qna.qna_num}">
							</div>
						</form>
					</div>
				</c:if>
				<!-- 댓글 목록 출력 -->
				<div id="output"></div>
				<div class="paging-button" style="display: none;">
					<input type="button" value="다음글 보기">
				</div>
				<div id="loading" style="display: none">
					<img
						src="${pageContext.request.contextPath }/images/ajax-loader.gif">
				</div>
				<!-- 댓글 목록 출력 끝 -->
				<!-- 댓글 끝 -->
			</div>
		</div>
		<div></div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
		</html>
	</c:otherwise>
</c:choose>
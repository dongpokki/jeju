<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&amp;A 게시판</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="container mt-5">
		<div class="page-banner">
			<div class="row justify-content-center align-items-center h-100" style="border-radius: 30px;cursor:pointer;background-size: cover;background-image: URL(${pageContext.request.contextPath}/images/qnaimage.jpg);background-position:center;" onclick="location.href='qnaList.do'">
				<div style="position: absolute;">
					<h1 class="text-center">Q&amp;A</h1>
				</div>
			</div>
		</div>
	</div>
<div>
	<div class="page-section">
		<div class="container mt-5">
			
			<div class="search-form" >
			<div style="float: left;font-size:20px">
				전체 <span class="qna-text-primary">${count }</span>건 | 페이지 <span class="qna-text-primary">${pageNum }</span>/${pageCount }
			</div>
			<form action="qnaList.do" method="get" style="float: right;">
				<div class="FormSelectButton">
					<div>
					<ul id="search">
						<li>
							<select name="keyfield" class="form-control" style="width:115px;height:38px">
							<option value="0"<c:if test="${param.keyfield==0}">selected</c:if>>전체</option>
							<option value="1"<c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2"<c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
							<option value="3"<c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
							</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" class="form-control" value="${param.keyword }" placeholder="검색어를 입력해주세요" style="height:38px;width:230px">
							<button style="height:38px;" type="submit" class="btn btn-primary" style="backgroun-color:#FEA82F">
								<img src='${pageContext.request.contextPath }/images/search.png'width="25px">
							</button>
						</li>
					</ul>
					</div>
				</div>
			</form>
			</div>
			<c:if test="${count==0 }">
				<div class="search-none">
					<hr size="1" noshade="noshade" class="qna_hr">
					<h5>게시물이 없습니다.</h5>
					새로운 게시물을 등록해주세요.
				</div>
			</c:if>
			<div>
				<table class="qnaTable">
					<%-- <tr>
						<c:if test="${session_user_auth==3}"><th>체크</th></c:if>
						<th>글번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
						<th>답변여부</th>
					</tr> --%>
					<colgroup>
						<col style="width:8%">
						<col style="width:40%">
						<col style="width:11%">
						<col style="width:15%">
						<col style="width:8%">
						<col style="width:10%">
					</colgroup>
					<c:if test="${count>0 }">
					<c:forEach var="qna" items="${list }">
					<tr>
						<%-- <c:if test="${session_user_auth==3}"><td style="text-align:center">
							<input type="checkbox" name="delete_check" >
						</td></c:if> --%>
						<td style="text-align:center">${qna.qna_num }</td>
						<td onclick="location.href='qnaDetail.do?qna_num=${qna.qna_num}'" style="cursor:pointer">
							<c:choose>
								<c:when test="${fn:length(qna.title) gt 27}">
								<div class="qna_title">
								<c:out value="${fn:substring(qna.title, 0, 27)}"/>...
								<c:if test="${qna.viewable_check==1 }">
								<img src="${pageContext.request.contextPath }/images/lock1.png" width="20px">
								</c:if>
								</div>
								</c:when>
								<c:otherwise>
								<div class="qna_title">
									<c:out value="${qna.title}"/>
									<c:if test="${qna.viewable_check==1 }">
										<img src="${pageContext.request.contextPath }/images/lock1.png" width="20px">
									</c:if>
								</div>
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>${qna.id }
						</td>
						<%-- <td>${qna.name }
						<c:if test="${qna.id ne 'admin'}">
							(<c:choose>
								<c:when test="${fn:length(qna.id) gt 2}">
									<c:out value="${fn:substring(qna.id, 0, 2)}"/><c:forEach begin="3" end="${fn:length(qna.id) }">*</c:forEach>
								</c:when>
							<c:otherwise>
								<c:out value="${qna.id}"/>
							</c:otherwise>
							</c:choose>)
						</c:if>
						</td> --%>
						<td><img src="${pageContext.request.contextPath }/images/time.png"> ${qna.reg_date }</td>
						<td><img src="${pageContext.request.contextPath }/images/eyes.png"> ${qna.hit }</td>
						<td>
						<c:if test="${qna.cmt_count==0 }">
							<div class="btn qna_preanswer">
								답변예정
							</div>
						</c:if>
						<c:if test="${qna.cmt_count!=0 }">
							<div class="btn qna_answer">
								답변완료
							</div>
						</c:if>
						</td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				<div class="paging" style="text-align:center">
					${pagingHtml }
				</div>	
				<div style="text-align: right">
<%-- 					<c:if test="${session_user_auth==3}"><input class="btn btn-tertiary qna" type="button" value="삭제" onclick="location.href='qnaDelete.do'"></c:if> --%>
					<input <c:if test="${empty session_user_num}">disabled="disabled"</c:if> class="btn btn-primary qna" type="button" value="작성" onclick="location.href='qnaWriteForm.do'" style="backgroun-color:#FEA82F"> 
					<input class="btn btn-secondary qna" type="button" value="목록" onclick="location.href='qnaList.do'">
				</div>
			</div>
		</div>
	</div>
</div>
<p>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
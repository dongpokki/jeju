<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 코스 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
<!-- 지도를 표시할 div -->
<div id="map" style="width:100%;height:350px;"></div>
<!-- 지도 api 키 값 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0f0f45ae09fd5fe9bd4014a783fa7b89"></script>
<script>
<!-- 지도 생성 시작 -->
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = { 
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
<!-- 지도 생성 끝 -->
<!-- 지도 컨트롤러 시작 -->
//일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

//지도에 컨트롤을 추가해야 지도위에 표시됩니다
//kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
<!-- 지도 컨트롤러 끝 -->
<!-- 지도 마커 표시 시작 -->
//지도를 클릭한 위치에 표출할 마커입니다
var marker = new kakao.maps.Marker({ 
// 지도 중심좌표에 마커를 생성합니다 
position: map.getCenter() 
}); 
//지도에 마커를 표시합니다
marker.setMap(map);

//지도에 클릭 이벤트를 등록합니다
//지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        

// 클릭한 위도, 경도 정보를 가져옵니다 
var latlng = mouseEvent.latLng; 

// 마커 위치를 클릭한 위치로 옮깁니다
marker.setPosition(latlng);
});
<!-- 지도 마커 표시 끝 -->
</script>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>Recommended course</h2>
	<h1>추천 코스 목록</h1>
	<form id="search_form" action="list.do" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1">최신순</option>
					<option value="2">추천순</option>
					<option value="3">조회순</option>
				</select>
			</li>
			<li>
				<input type="search" size="16" name="keyword" id="keyword"
				                                   value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form>
	<div class="list-space align-right">
		<c:if test="${empty user_num}">
		<input type="button" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/area/areaWriteForm.do'"></c:if>
		<!-- 로그인 작업 후 다시
		<c:if test="${empty user_num}">
		<input type="button" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/user/loginform.do'"></c:if> -->
		<input type="button" value="홈으로" 
		 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회</th>
		</tr>
		<c:forEach var="board" items="${list}">
		<tr>
			<td>${board.board_num}</td>
			<td><a href="detail.do?board_num=${board.board_num}">${board.title}</a></td>
			<td>${board.id}</td>
			<td>${board.reg_date}</td>
			<td>${board.hit}</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
</div>
</body>
</html>





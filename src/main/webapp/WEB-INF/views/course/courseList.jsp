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
		<input type="button" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/course/courseWriteForm.do'"
		<c:if test="${empty user_num}"> onclick="location.href='${pageContext.request.contextPath}/user/loginForm.do'"</c:if>>
		<input type="button" value="홈으로" 
		 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
	<c:if test="${count == 0}">
    
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0f0f45ae09fd5fe9bd4014a783fa7b89"></script>
<div id="map" style="width: 40%; height: 500px;"></div>
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = {
		center : new kakao.maps.LatLng(33.4992269,126.4890004), // 지도의 중심좌표
		level : 9
	// 지도의 확대 레벨
	};
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	//첫번째 띄울 좌표
	var first_positions = [
		{
			content : '<div>제주공항</div>',
			latlng : new kakao.maps.LatLng(33.510418, 126.4891647)
		},
		{
			content : '<div>하귀옛날국수집</div>',
			latlng : new kakao.maps.LatLng(33.4755061,126.3679761)
		},
		{
			content : '<div>제주공룡랜드</div>',
			latlng : new kakao.maps.LatLng(33.3958579,126.3360471)
		},
		{
			content : '<div>큰노꼬메오름</div>',
			latlng : new kakao.maps.LatLng(33.3929915,126.3466901)
		},
		{
			content : '<div>아르떼뮤지엄제주</div>',
			latlng : new kakao.maps.LatLng(33.3929915,126.3466901)
		}

	];
	//두번째 띄울 좌표
	var second_positions = [
	{
		content : '<div>제주민속오일시장</div>',
		latlng : new kakao.maps.LatLng(33.4996456,126.4785268)
	},
	{
		content : '<div>탤러해시 제주점</div>',
		latlng : new kakao.maps.LatLng(33.4921534,126.4756042)
	},
	{
		content : '<div>느영나영매장</div>',
		latlng : new kakao.maps.LatLng(33.4835738,126.4717199)
	}
	];
	// 첫번째 마커 생성
	for (var i = 0; i < first_positions.length; i++) {
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map : map, // 마커를 표시할 지도
			position : first_positions[i].latlng
		// 마커의 위치// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		});
		// 마커에 표시할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			content : first_positions[i].content, // 인포윈도우에 표시할 내용
			removable : true
		});
		kakao.maps.event.addListener(marker, 'click', marker_click(map, marker,
				infowindow));
	}
	// 두번째 마커 생성
	for (var i = 0; i < second_positions.length; i++) {
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map : map, // 마커를 표시할 지도
			position : second_positions[i].latlng
		// 마커의 위치// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		});
		// 마커에 표시할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			content : second_positions[i].content, // 인포윈도우에 표시할 내용
			removable : true
		});
		kakao.maps.event.addListener(marker, 'click', marker_click(map, marker,
				infowindow));
	}
	function marker_click(map, marker, infowindow) {
		return function() {
			infowindow.open(map, marker);
		};
	}
	// 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
	var first_polyline = [
		new kakao.maps.LatLng(33.510418, 126.4891647),
		new kakao.maps.LatLng(33.4755061, 126.3679761),
		new kakao.maps.LatLng(33.3958579,126.3360471),
		new kakao.maps.LatLng(33.3929915,126.3466901),
		new kakao.maps.LatLng(33.3929915,126.3466901)
	];
	// 지도에 표시할 선을 생성합니다
	var first_linePath = new kakao.maps.Polyline({
		path : first_polyline, // 선을 구성하는 좌표배열 입니다
		strokeWeight : 3, // 선의 두께 입니다
		strokeColor : 'black', // 선의 색깔입니다
		strokeOpacity : 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		strokeStyle : 'solid' // 선의 스타일입니다
	});
	var second_linePath = [
	new kakao.maps.LatLng(33.4996456,126.4785268),
	new kakao.maps.LatLng(33.4921534,126.4756042),
	new kakao.maps.LatLng(33.4835738,126.4717199)
	];
	// 지도에 표시할 선을 생성합니다
	var second_polyline = new kakao.maps.Polyline({
		path : second_linePath, // 선을 구성하는 좌표배열 입니다
		strokeWeight : 3, // 선의 두께 입니다
		strokeColor : 'red', // 선의 색깔입니다
		strokeOpacity : 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		strokeStyle : 'solid' // 선의 스타일입니다
	});
	// 지도에 선을 표시합니다
	first_linePath.setMap(map);
	second_polyline.setMap(map);
	
</script>
<!-- 제주도 코스수정
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = {
		center : new kakao.maps.LatLng(33.510418, 126.4891647), // 지도의 중심좌표
		level : 6
	// 지도의 확대 레벨
	};
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	//첫번째 띄울 좌표
	var first_positions = [
	{
		content : '<div>제주공항</div>',
		latlng : new kakao.maps.LatLng(33.510418, 126.4891647)
	},
	{
		content : '<div>하귀옛날국수집</div>',
		latlng : new kakao.maps.LatLng(33.4755061,126.3679761)
	},
	{
		content : '<div>제주공룡랜드</div>',
		latlng : new kakao.maps.LatLng(33.3958579,126.3360471)
	}
	{
		content : '<div>큰노꼬메오름</div>',
		latlng : new kakao.maps.LatLng(33.3929915,126.3466901)
	}
	{
		content : '<div>아르떼뮤지엄제주</div>',
		latlng : new kakao.maps.LatLng(33.3929915,126.3466901)
	}
	];
	//두번째 띄울 좌표
	var second_positions = [
	{
		content : '<div>한림공원</div>',
		latlng : new kakao.maps.LatLng(33.3646089,126.3096112)
	},
	{
		content : '<div>신창풍차해안</div>',
		latlng : new kakao.maps.LatLng(333.3519915,126.3017148)
	},
	{
		content : '<div>제주도립 김창열미술관</div>',
		latlng : new kakao.maps.LatLng(33.3382249,126.2896985)
	}
	];
	// 첫번째 마커 생성
	for (var i = 0; i < first_positions.length; i++) {
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map : map, // 마커를 표시할 지도
			position : first_positions[i].latlng
		// 마커의 위치// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		});
		// 마커에 표시할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			content : first_positions[i].content, // 인포윈도우에 표시할 내용
			removable : true
		});
		kakao.maps.event.addListener(marker, 'click', marker_click(map, marker,
				infowindow));
	}
	// 두번째 마커 생성
	for (var i = 0; i < second_positions.length; i++) {
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map : map, // 마커를 표시할 지도
			position : second_positions[i].latlng
		// 마커의 위치// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		});
		// 마커에 표시할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			content : second_positions[i].content, // 인포윈도우에 표시할 내용
			removable : true
		});
		kakao.maps.event.addListener(marker, 'click', marker_click(map, marker,
				infowindow));
	}
	function marker_click(map, marker, infowindow) {
		return function() {
			infowindow.open(map, marker);
		};
	}
	// 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
	var first_polyline = [
	new kakao.maps.LatLng(33.510418, 126.4891647),
	new kakao.maps.LatLng(33.4755061, 126.3679761),
	new kakao.maps.LatLng(33.3958579,126.3360471),
	new kakao.maps.LatLng(33.3929915,126.3466901),
	new kakao.maps.LatLng(33.3929915,126.3466901)
	];
	// 지도에 표시할 선을 생성합니다
	var first_linePath = new kakao.maps.Polyline({
		path : first_polyline, // 선을 구성하는 좌표배열 입니다
		strokeWeight : 3, // 선의 두께 입니다
		strokeColor : 'black', // 선의 색깔입니다
		strokeOpacity : 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		strokeStyle : 'solid' // 선의 스타일입니다
	});
	var second_linePath = [
	new kakao.maps.LatLng(33.3646089,126.3096112),
	new kakao.maps.LatLng(333.3519915,126.3017148),
	new kakao.maps.LatLng(33.3382249,126.2896985)
	];
	// 지도에 표시할 선을 생성합니다
	var second_polyline = new kakao.maps.Polyline({
		path : second_linePath, // 선을 구성하는 좌표배열 입니다
		strokeWeight : 3, // 선의 두께 입니다
		strokeColor : 'red', // 선의 색깔입니다
		strokeOpacity : 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		strokeStyle : 'solid' // 선의 스타일입니다
	});
	// 지도에 선을 표시합니다
	first_linePath.setMap(map);
	second_polyline.setMap(map);
	
</script>
 -->
<!-- 지도 끝 -->
<!-- 지도 구현 후 수정필요
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>	
	 -->
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





$(function() {
	$('#write_form').submit(function() {
		if ($('#category').val() == '') {
			alert('카테고리를 선택해주세요.');
			$('#category').focus();
			return false;
		}
		if ($('#title').val().trim() == '') {
			alert('제목을 입력해주세요.');
			$('#title').val('').focus();
			return false;
		}
		if ($('#summernote').val().trim() == '') {
			alert('내용을 입력해주세요.');
			$('#summernote').val('').focus();
			return false;
		}
	});
	
	$('#summernote').summernote(
					{
						toolbar : [
								// [groupName, [list of button]]
								[ 'fontname', [ 'fontname' ] ],
								[ 'fontsize', [ 'fontsize' ] ],
								[
										'style',
										[ 'bold', 'italic', 'underline',
												'strikethrough', 'clear' ] ],
								[ 'color', [ 'forecolor', 'color' ] ],
								[ 'table', [ 'table' ] ],
								[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
								[ 'height', [ 'height' ] ], ],
						fontNames : [ 'Arial', 'Arial Black', 'Comic Sans MS',
								'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림',
								'돋움체', '바탕체' ],
						fontSizes : [ '8', '9', '10', '11', '12', '14', '16',
								'18', '20', '22', '24', '28', '30', '36', '50',
								'72' ],
						height : 500, // 에디터 높이
						width : "100%",
						focus : true, // 에디터 로딩후 포커스를 맞출지 여부
						lang : "ko-KR", // 한글 설정
						placeholder : '내용을 입력해주세요.' //placeholder 설정
					});
});




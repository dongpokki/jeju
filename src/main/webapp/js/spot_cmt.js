$(function() {
	let currentPage;
	let count;
	let rowCount;

	function getContextPath() {
		return sessionStorage.getItem("contextpath");
	}
	let ctx = getContextPath();
	//댓글 목록
	function selectData(pageNum) {
		currentPage = pageNum;

		$.ajax({
			url: 'spotListCmt.do',
			type: 'post',
			data: { pageNum: pageNum, spot_num: $('#spot_num').val() },
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(param) {

				count = param.count;
				rowCount = param.rowCount;

				if (pageNum == 1) {
					$('#output').empty();
				}

				$(param.list).each(function(index, item) {
					let output = '<div id="item">'
					output += '<div id="sub-item">'
					output += '<div class="user justify-content-btween d-flex" style="margin : 20px 0;">';
					output += '<div class="thumb">';

					if (item.user_num && item.user_photo) {
						output += '<img src="' + ctx + '/upload/' + item.user_photo + '" style="max-width: 100%; height: 100%; object-fit: cover;">';
					} else {
						output += '<img src="' + ctx + '/images/face.png" style="max-width: 100%; height: 100%; object-fit: cover;" >';
					}
					output += '</div>';
					output += '<div class="desc" >';
					output += '<h6  id="comment" >' + item.cmt_content + '</h6>';
					output += '<div class="d-flex justify-content-between" >';
					output += '<div class="d-flex align-items-center">';
					output += '<p style="margin: 4px 0 0;">' + item.id + '</p>';
					if (item.modify_date) {
						output += '<p class="date" id="modify_date">' + item.modify_date + '</p>';
					} else {
						output += '<p class="date"  id="modify_date">' + item.reg_date + '</p>';
					}
					output += '</div>';
					output += '<div>';
					if (param.user_num == item.user_num) {
						output += '<input type="button" data-spotcmtnum="' + item.spotcmt_num + '"value="수정" id="modify_btn" class="btn-cmt"  >';
						output += '<input type="button" data-spotcmtnum="' + item.spotcmt_num + '"value="삭제" id="delete_btn" class="btn-cmt"  >';
					}
					output += '</div>';
					output += '</div>';
					output += '</div></div>';
					output += '</div></div></div>';

					$('#output').append(output);
				});
				if (currentPage >= Math.ceil(count / rowCount)) {
					$('.paging-button').hide();
				} else {
					$('.paging-button').show();
				}
			},
			error: function() {
				alert('네트워크 오류');
			}
		});
	}
	//페이지 처리 이벤트 연결 (다음 댓글 보기 버트 클릭시 데이터 추가 )
	$('.paging-button input').click(function() {
		selectData(currentPage + 1);
	})
	//댓글등록
	$('#cmt_form2').submit(function(event) {
		if ($('#cmt_content').val().trim() == '') {
			alert('내용을 입력하세요.');
			$('#cmt_content').val('').focus();
			return false;
		}

		let form_data = $(this).serialize();

		$.ajax({
			url: 'spotWriteCmt.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인해야 작성할 수 있습니다.');
				} else if (param.result == 'success') {
					//폼 초기화
					initForm();
					//댓글 작성이 성송하면 새로 입력한 글을 포함해서 첫번째 페이지의 게시글을
					//다시 호출함
					selectData(1);
				} else {
					alert('등록시 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
		event.preventDefault();
	});
	//댓글 작성 폼 초기화 
	function initForm() {
		$('textarea').val('');
		$('#cmt_first .letter-count').text('100/100');
	}

	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup', 'textarea', function() {
		//입력한 글자수 구함 
		let inputLength = $(this).val().length;

		if (inputLength > 100) {//입력한 글자가 100자 넘어선 경우
			$(this).val($(this).val().substring(0, 100));
			if ($(this).attr('id') == 'cmt_content') {
				//등록폼글자수
				$('#cmt_first .letter-count').text('0/100');
			} else {
				//수정폼 글자수 
				$('#mcmt_second .letter-count').text('0/100');
			}
		} else {//100글자를 안 넘어선 경우
			let remain = 100 - inputLength;
			remain += '/100';
			if ($(this).attr('id') == 'cmt_content') {
				//등록폼글자수
				$('#cmt_first .letter-count').text(remain);
			} else {
				//수정폼 글자수 
				$('#mcmt_second .letter-count').text(remain);
			}
		}
	});
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click', '#modify_btn', function() {
		let spotcmt_num = $(this).attr('data-spotcmtnum');
		let content = $(this).parents('#sub-item').find('#comment').html().replace(/<br>/gi, '\n');

		let modifyUI = '<form id="mcmt_form" class="form-contact comment_form">';
		modifyUI += '	<input type="hidden" name="spotcmt_num" id="spotcmt_num" value="' + spotcmt_num + '">';
		modifyUI += '		<div class="row">';
		modifyUI += '			<div class="col-12">';
		modifyUI += '				<div class="form-group" style="margin : 0;">';
		modifyUI += '	<textarea cols="10" rows="2" name="cmt_content" id="mcmt_content" class="form-control w-100">' + content + '</textarea>';
		modifyUI += '				</div>';
		modifyUI += '			</div>';
		modifyUI += '		</div>';
		modifyUI += '		<div style="display: flex; justify-content: space-between;">';
		modifyUI += '	<div id="mcmt_first"><span class="letter-count">100/100</span></div>';
		modifyUI += '	<div id="mcmt_second" style="text-align: right">';
		modifyUI += '		<input type="submit" value="수정" class="btn-cmt">';
		modifyUI += '		<input type="button" value="취소" class="btn-cmt" id="cmt-reset">';
		modifyUI += '	</div></div>';
		modifyUI += '</form>';

		initModifyForm();

		$(this).parents('#sub-item').hide();
		$(this).parents('#item').append(modifyUI);

		let inputLength = $('#mcmt_content').val().length;
		let remain = 100 - inputLength;
		remain += '/100';

		$('#mcmt_first .letter-count').text(remain);

	});
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화 
	$(document).on('click', '#cmt-reset', function() {
		initModifyForm();
	})
	//댓글 수정 폼 초기화
	function initModifyForm() {
		$('div').show();
		$('#mcmt_form').remove();
	}
	//댓글 수정 
	$(document).on('submit', '#mcmt_form', function(event) {
		if ($('#mcmt_content').val().trim() == '') {
			alert('내용을 입력하세요.');
			$('#mcmt_content').val('').focus();
			return false;
		}
		let form_data = $(this).serialize();

		$.ajax({
			url: 'spotUpdateCmt.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인해야 작성 가능합니다.');
				} else if (param.result == 'success') {
					$('#mcmt_form').parent().find('#comment').html($('#mcmt_content').val().replace(/</g, '&lt;').replace(/>/g, '&gt').replace(/\n/g, '<br>'));
					$('#mcmt_form').parent().find('#modify_date').text('최근 수정일 : 5초 미만');
					initModifyForm();
				} else if (param.result == 'wrongAccess') {
					alert('관리자만 가능합니다.');
				} else {
					alert('수정시 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류');
			}
		});
		event.preventDefault();
	});
	//댓글 삭제
	$(document).on('click', '#delete_btn', function() {
		let check = confirm('삭제하시겠습니까?');
		let spotcmt_num = $(this).attr('data-spotcmtnum');
		if (check) {
			$.ajax({
				url: 'spotDeleteCmt.do',
				type: 'post',
				data: { spotcmt_num: spotcmt_num },
				dataType: 'json',
				cache: false,
				timeout: 30000,
				success: function(param) {
					if (param.result == 'logout') {
						alert('로그인해야 가능합니다.');
					} else if (param.result == 'success') {
						alert('삭제 완료');
						selectData(1);
					} else if (param.result == 'wrongAccess') {
						alert('관리자만 가능합니다.');
					} else {
						alert('삭제 시 오류 발생');
					}
				},
				error: function() {
					alert('네트워크 오류 발생');
				}
			});
		}
	});
	//초기 데이터(목록) 호출
	selectData(1);
})
$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//댓글 목록
	function selectData(pageNum){
		
	}
	//댓글등록
	$('#cmt_form').submit(function(event){
		if($('#cmt_content').val().trim()==''){
			alert('내용을 입력하세요.');
			$('#cmt_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		
		$.ajax({
			url:'qnaWriteCmt.do',
			type:'post',
			data:form_data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result=='success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성송하면 새로 입력한 글을 포함해서 첫번째 페이지의 게시글을
					//다시 호출함
					selectData(1);
				}else{
					alert('등록시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		event.preventDefault();
	});
	//댓글 작성 폼 초기화 
	function initForm(){
		$('textarea').val('');
		$('#cmt_first .letter-count').text('100/100');
	}
	
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함 
		let inputLength=$(this).val().length;
		
		if(inputLength>100){//입력한 글자가 300자 넘어선 경우
			$(this).val($(this).val().substring(0,100));
			if($(this).attr('id')=='cmt_content'){
				//등록폼글자수
				$('#cmt_first .letter-count').text('0/300');
			}else{
				//수정폼 글자수 
				$('#mcmt_second .letter-count').text('0/300');
			}
		}else{//300글자를 안 넘어선 경우
			let remain = 300- inputLength;
			remain+='/300';
			if($(this).attr('id')=='cmt_content'){
				//등록폼글자수
				$('#cmt_first .letter-count').text(remain);
			}else{
				//수정폼 글자수 
				$('#mcmt_second .letter-count').text(remain);
			}
		}
	});
	//댓글 수정 버튼 클릭시 수정폼 노출
	
	//댓글 수정 폼 초기화

	//댓글 수정
	
	//댓글 삭제
	
	//초기 데이터(목록) 호출
	selectData(1);
})
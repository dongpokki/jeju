$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//댓글 목록
	function selectData(pageNum){
		currentPage = pageNum;
		$('#loading').show();
		
		$.ajax({
			url:'qnaListCmt.do',
			type:'post',
			data:{pageNum:pageNum,qna_num:$('#qna_num').val()},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				$('#loading').hide();
				
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum==1){
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output ='<div class="item">';
						output +='	<div class="sub-item  post-meta">';
						output +='		<div class="post-name"><img src="/jeju/images/qnaperson.png" width="17px"> '+item.name+'</div>';
						output +='		<div style="float:right" class="post-lookup">'
					if(item.modify_date){
						output+='			<span class="modify_date"><img src="/jeju/images/time.png"> '+item.modify_date+'</span>';
					}else{
						output +='			<span class="modify_date"><img src="/jeju/images/time.png"> '+item.reg_date+'</span>';
					}
						output +='		</div>'
						output +='		<p class="cmtList_content">'+item.cmt_content+'</p>';
						output +='		<div style="text-align: right">'
					if(param.user_num==item.user_num){
						output +='			<input type="button" data-qnacmtnum="'+item.qnacmt_num+'"value="수정" id="modify_btn" class="btn btn-primary qna">';
						output +='			<input type="button" data-qnacmtnum="'+item.qnacmt_num+'"value="삭제" id="delete_btn" class="btn btn-secondary qna">';
					}
						output +='			<hr size="1" width="100%">';
						output +='		</div>';
						output +='	</div>';
						output +='<div>';
					
					$('#output').append(output);
				});
				if(currentPage >=Math.ceil(count/rowCount)){
					$('.paging-button').hide();
				}else{
					$('.paging-button').show();
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	}
	//페이지 처리 이벤트 연결 (다음 댓글 보기 버트 클릭시 데이터 추가 )
	$('.paging-button input').click(function(){
		selectData(currentPage+1);
	})
	//댓글등록
	$('#cmtForm_qna').submit(function(event){
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
		$('#cmt_first .letter-count-qna').text('300 / 300');
	}
	
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함 
		let inputLength=$(this).val().length;
		
		if(inputLength>100){//입력한 글자가 100자 넘어선 경우
			$(this).val($(this).val().substring(0,300));
			if($(this).attr('id')=='cmt_content'){
				//등록폼글자수
				$('#cmt_first .letter-count-qna').text('0 / 300');
			}else{
				//수정폼 글자수 
				$('#mcmt_second .letter-count-qna').text('0 / 300');
			}
		}else{//100글자를 안 넘어선 경우
			let remain = 300- inputLength;
			remain+=' / 300';
			if($(this).attr('id')=='cmt_content'){
				//등록폼글자수
				$('#cmt_first .letter-count-qna').text(remain);
			}else{
				//수정폼 글자수 
				$('#mcmt_second .letter-count-qna').text(remain);
			}
		}
	});
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','#modify_btn',function(){
		let qnacmt_num=$(this).attr('data-qnacmtnum');
		let content = $(this).parents('.sub-item').find('p').html().replace(/<br>/gi,'\n');
		
		let modifyUI = '<form id="mcmtForm_qna">';
			modifyUI +='	<div class="post-meta" style="padding: 12px 0;margin-bottom: 25px;">';
			modifyUI +='		<span class="post-cmt" style="float:left"><img alt="답변이미지" src="/jeju/images/cmt-speech.png"><b> 관리자 답변 수정 </b></span>';
			modifyUI +='		<div id="mcmt_first" style="float:right"><span class="letter-count-qna">300 / 300</span></div>';
			modifyUI +='	</div>';
			modifyUI +='	<input type="hidden" name="qnacmt_num" id="qnacmt_num" value="'+qnacmt_num+'">';
			modifyUI +='	<textarea rows="3" cols="50" name="cmt_content" id="mcmt_content" class="cmtp-content">'+ content +'</textarea>';
			modifyUI +='	<p>'
			modifyUI +='	<div id="mcmt_second" style="text-align: right">';
			modifyUI +='		<input type="submit" value="수정" class="btn btn-primary qna">';
			modifyUI +='		<input type="button" value="취소" class="cmt-reset btn btn-secondary qna" >';
			modifyUI +='	</div>';
			modifyUI +='	<hr size="1" width="100%" style="border-top: 1px solid rgba(0, 0, 0, 0.1)">'
			modifyUI +='</form>';
			
			initModifyForm();
			
			$(this).parents('.sub-item').hide();
			$(this).parents('.item').append(modifyUI);
			
			let inputLength=$('#mcmt_content').val().length;
			let remain = 300-inputLength;
			remain +=' / 300';
			
			$('#mcmt_first .letter-count-qna').text(remain);
			
	});
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화 
	$(document).on('click','.cmt-reset',function(){
		initModifyForm();
	})
	//댓글 수정 폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mcmtForm_qna').remove();
	}
	//댓글 수정 
	$(document).on('submit','#mcmtForm_qna',function(event){
		if($('#mcmt_content').val().trim()==''){
			alert('내용을 입력하세요.');
			$('#mcmt_content').val('').focus();
			return false;
		}
		let form_data =$(this).serialize();
		
		$.ajax({
			url:'qnaUpdateCmt.do',
			type:'post',
			data:form_data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 작성 가능합니다.');
				}else if(param.result =='success'){
					$('#mcmtForm_qna').parent().find('p').html($('#mcmt_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt').replace(/\n/g,'<br>'));
					initModifyForm();
				}else if(param.result=='wrongAccess'){
					alert('관리자만 가능합니다.');
				}else{
					alert('수정시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
		event.preventDefault();
	});
	//댓글 삭제
	$(document).on('click','#delete_btn',function(){
		let check = confirm('삭제하시겠습니까?');
		let qnacmt_num = $(this).attr('data-qnacmtnum');
		if(check){
			$.ajax({
				url:'qnaDeleteCmt.do',
				type:'post',
				data:{qnacmt_num:qnacmt_num},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result=='logout'){
						alert('로그인해야 가능합니다.');
					}else if(param.result=='success'){
						alert('삭제 완료');
						selectData(1);
					}else if(param.result=='wrongAccess'){
						alert('관리자만 가능합니다.');
					}else{
						alert('삭제 시 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		}
	});
	//초기 데이터(목록) 호출
	selectData(1);
})
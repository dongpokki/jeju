$(function(){
		let photo_path = $('.my-photo').attr('src'); // 파일의 경로(String)을 담을 변수 (처음 화면에 보여지는 이미지 읽기)
		let my_photo; // 파일 객체(DOM)를 담을 공간의 변수
		
		$('#photo_btn').click(function(){
			$('#photo_choice').show(); // 파일선택,전송,취소 버튼 노출
			$(this).hide(); // 수정버튼 감추기
		});
		
		//이미지 미리보기 취소
		$('#photo_reset').click(function(){
			$('.my-photo').attr('src',photo_path); //이미지 미리보기 전 이미지로 되돌리기
			$('#photo').val(''); // 파일선택값 초기화
			$('#photo_choice').hide(); // 파일선택,전송,취소 버튼 숨김
			$('#photo_btn').show(); // 수정 버튼 노출
		
		});
		
		//이미지 선택 및 이미지 미리보기
		$('#photo').change(function(){ // 파일선택이 첨부 or 미첨부가 될경우
			my_photo = this.files[0]; // 첨부한 file은 한개여도 항상 files[] 배열(객체)형으로 관리
			if(!my_photo){ // my_photo의 값이 비어있다면 = 첨부한 파일이 없다면,
				$('.my-photo').attr('src',photo_path); // 기본 이미지가 노출
				return; // 이벤트(함수) 종료
			}
			
			if(my_photo.size > 1024*1024){ // 선택한 파일이 이미지제한 1MB을 넘으면
				alert('1MB까지만 업로드 가능!');
				photo.value = ''; // 파일 선택값 지정을 취소
				return; // 이벤트(함수) 종료
			}
			
			let reader = new FileReader(); // 파일을 읽기위해선 FileReader 객체 생성
			reader.readAsDataURL(my_photo); // 파일을 읽기위해 readAsDataURL 메서드 실행
			
			reader.onload = function(){ // FileReader.readAsDataURL이 호출되어 파일을 다 읽으면
				$('.my-photo').attr('src',reader.result);
			};
		}); // end of change
		
		
		//이미지 전송
		$('#photo_submit').click(function(){
			// 파일 입력 유효성 체크
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			// 파일 전송 (ajax(json 방식) 으로 페이지 이동없이 비동기 처리)
			let form_data = new FormData(); // ajax 방식 처리 시 FormData 객체 생성
			// FormData 객체 생성후 append 메서드 호출하여 인자값에 키와 value에 파일객체를 넣어준다.
			form_data.append('photo',my_photo); // 파일 선택하여 첨부된 파일(DOM) Element
			$.ajax({
				url : 'updateMyPhoto.do',
				type : 'post', // 파일 업로드 시 반드시 post 방식 사용
				data : form_data, // 선택한 파일이 append 메서드 처리된 FormData 객체를 데이터로 넘겨준다.
				dataType : 'json', //jackson 라이브러리를 사용하기위해 json 방식 
				contentType : false, // 데이터 객체를 문자열로 바꿀지 지정 (true일 경우 일반문자) - 파일을 넘겨주기 때문에 반드시 false
				processData : false, // 해당 타입을 true로 하면 일반 text로 구분  - 파일을 넘겨주기 때문에 반드시 false
				enctype : 'multipart/form-data', //비동기처리든 동기처리든 파일 라이브러리 사용시 enctype의 Multipart/form-data 설정은 필수
				success : function(param){ // ajax 비동기처리가 성공하면 plain 형식의 파일에 데이터를 가져온다.
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('프로필 사진이 수정되었습니다.');
						photo_path = $('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					}else{
						alert('파일 전송 오류 발생');	
					}
				},
				error : function(){
					alert('네트워크 오류발생');
				}
			});
		});
		
		
		// ================================================================================================================================
		// ↓ 내가 좋아하는 장소 & 내가 좋아하는 코스 & 내가 작성한 문의사항 스크립트문
		
		
		//내가 좋아하는 장소 목록
		let spot_currentPage;
		let spot_count;
		let spot_rowCount;

		function spot_selectData(spot_pageNum){
			spot_currentPage = spot_pageNum;
			
			//로딩 이미지 노출
			$('#spot_loading').show();
			
			$.ajax({
				type:'post',
				data:{spot_pageNum:spot_pageNum},
				url:'mygoodspot.do',
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					//로딩 이미지 감추기
					$('#spot_loading').hide();
										
					spot_count = param.spot_count;
					spot_rowCount = param.spot_rowCount;
					
					if(spot_pageNum == 1){
						// 처음 호출시는 해당 영역의 div의 내부 내용물을 제거
						$('#spot_output').empty();
					}	
					
					if($(param.spot_list).length == 0){ // 내가 추천하는 장소가 없다면
						let output = '<div class="alert alert-warning" style="width:100%;">등록된 추천 코스가 없습니다.</div>';
						
						//문서 객체에 추가
						$('#spot_output').append(output);
					}
										
					$(param.spot_list).each(function(index,spot){

							let output = '<div class="col-sm-12 col-lg-12">';
							output += '<h5 class="my-best-title alert alert-warning"><a href="/jeju/spot/spotDetail.do?spot_num=' + spot.spot_num + '">' + spot.title + '</a></h5>';
							output += '</div>';
						
						//문서 객체에 추가
						$('#spot_output').append(output);	
					});
				
					
					//page button 처리
					if(spot_currentPage>=Math.ceil(spot_count/spot_rowCount)){
						//다음 페이지가 없음
						$('.spot_paging-button').hide();		
					}else{
						//다음 페이지가 존재
						$('.spot_paging-button').show();
					}
				
				},
				error:function(){
					alert('네트워크 오류');
				}
			});
		}
		
		// 페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭 시 데이터 추가)
		$('.spot_paging-button input').click(function(){
			spot_selectData(spot_currentPage + 1);		
		});
		
		
		
		//내가 좋아하는 장소 목록
		let myqna_currentPage;
		let myqna_count;
		let myqna_rowCount;

		function myqna_selectData(myqna_pageNum){
			myqna_currentPage = myqna_pageNum;

			//로딩 이미지 노출
			$('#myqna_loading').show();
			
				$.ajax({
				type:'post',
				data:{myqna_pageNum:myqna_pageNum},
				url:'myqna.do',
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					//로딩 이미지 감추기
					$('#myqna_loading').hide();

					myqna_count = param.myqna_count;
					myqna_rowCount = param.myqna_rowCount;
					
					if(myqna_pageNum == 1){
						// 처음 호출시는 해당 영역의 div의 내부 내용물을 제거
						$('#myqna_output').empty();
					}

					
					if($(param.myqna_list).length == 0){ // 내가 작성한 문의사항이 없다면
						let output = '<div class="alert alert-warning" style="width:100%;">등록된 문의사항이 없습니다.</div>';
						
						//문서 객체에 추가
						$('#myqna_output').append(output);
					}					
					
					
					$(param.myqna_list).each(function(index,myqna){
							
							let output = '<div class="col-sm-12 col-lg-12">';
							output += '<h5 class="my-best-title alert alert-warning"><a href="/jeju/qna/qnaDetail.do?qna_num=' + myqna.qna_num + '">' + myqna.title + '</a></h5>';
							output += '</div>';
						
						//문서 객체에 추가
						$('#myqna_output').append(output);	
					});
					
					//alert('myqna_count/myqna_rowCount : ' + myqna_count + '/' +myqna_rowCount);
					
					//page button 처리
					if(myqna_currentPage>=Math.ceil(myqna_count/myqna_rowCount)){
						//다음 페이지가 없음
						$('.myqna_paging-button').hide();		
					}else{
						//다음 페이지가 존재
						$('.myqna_paging-button').show();
					}
				},
				
				error:function(){
					alert('네트워크 오류');
				}
			});	
		}
		
		$('.myqna_paging-button input').click(function(){
			myqna_selectData(myqna_currentPage + 1);		
		});
		
		// 마이페이지 최초 진입 시 내가 좋아하는 장소 / 내가 좋아하는 코스 / 내가 작성한 코스 / 내가 작성한 문의사항 목록 호출
		spot_selectData(1);
		myqna_selectData(1);
		
	});
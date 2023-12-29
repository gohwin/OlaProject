/**
 * 
 */
/* 트레이드 게시글 상세보기 자바스크립트*/

// 삭제 버튼 눌렀을때 확인절차하는 alert 

/* 트레이드 게시글 작성폼 자바스크립트*/
function goBack() {
	window.history.back();
}
/* textarea의 입력 이벤트에 반응하여 글자 수를 업데이트하는 함수 */
function updateCharCount(textarea) {
	var charCountElement = document.getElementById('charCount');
	charCountElement.textContent = textarea.value.length + ' / ' + textarea.maxLength;
}

document.getElementById('tradeInsertForm').addEventListener('submit', function(event) {
	event.preventDefault(); // 폼 기본 제출 동작 방지

	Swal.fire({
		title: '게시글 등록',
		text: "게시글을 등록하시겠습니까?",
		icon: 'question',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '네, 등록합니다',
		cancelButtonText: '아니요'
	}).then((result) => {
		if (result.isConfirmed) {
			// 사용자가 확인을 눌렀을 때 폼 제출
			this.submit(); // 실제 폼 제출

			// 여기에 성공 알림 표시 로직을 추가할 수 있습니다. (서버 응답에 따라)
			Swal.fire(
				'등록 완료!',
				'게시글이 성공적으로 등록되었습니다.',
				'success'
			);
		}
	});
});

/* 트레이드 수정 화면 자바스크립트*/
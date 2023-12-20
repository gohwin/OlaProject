/**
 * 
 */
// 아이디 찾는 폼 오픈
function openIdRecoveryForm() {
	var popup = window.open("", "_blank", "width=400,height=300,scrollbars=yes,resizable=yes");
	popup.document.write(`
		<html>
		<head>
			<title>아이디 찾기</title>
			<style>
				body {
					font-family: Arial, sans-serif;
					background-color: #f2f2f2;
					margin: 0;
				}

				h1 {
					text-align: center;
					color: #333;
				}

				form {
					max-width: 400px;
					margin: 0 auto;
				}

				label {
					display: block;
					margin-bottom: 8px;
				}

				input {
					width: calc(100% - 20px);
					padding: 10px;
					margin-top: 5px;
					margin-bottom: 10px;
					box-sizing: border-box;
					border: 1px solid #ddd;
					border-radius: 4px;
				}

				input[type="submit"] {
					background-color: #4caf50;
					color: white;
					padding: 10px 15px;
					border: none;
					border-radius: 4px;
					cursor: pointer;
					font-size: 16px;
					display: block;
					margin: 0 auto;
				}

				input[type="submit"]:hover {
					background-color: #45a049;
				}

				#result {
					margin-top: 20px;
					text-align: center;
				}
			</style>
		</head>
		<body>
			<h1>아이디 찾기</h1>
			<form id="idRecoveryForm" method="post">
				<label for="name">이름:</label>
				<input type="text" name="name" id="name" required><br>
				<label for="email">이메일:</label>
				<input type="email" name="email" id="email" required><br>
				<input type="submit" value="확인">
			</form>
			<div id="result"></div>
		</body>
		</html>
	`);

	popup.document.getElementById('idRecoveryForm').onsubmit = function(event) {
		event.preventDefault();
		var name = popup.document.getElementById('name').value;
		var email = popup.document.getElementById('email').value;

		fetch('/find-id', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			body: 'name=' + encodeURIComponent(name) + '&email=' + encodeURIComponent(email)
		})
			.then(response => response.text())
			.then(data => {
				popup.document.getElementById('result').innerHTML = data;
			})
			.catch(error => {
				console.error('Error:', error);
				popup.document.getElementById('result').innerHTML = '오류가 발생했습니다.';
			});
	};
}


/* 오류메시지를 Alert가 아닌 innerText로 사용해서 사용자 개선 향상 */
function displayMessage(popup, message, color) {
	var messageArea = popup.document.getElementById('messageArea');
	messageArea.innerText = message; // 오류 메시지 설정
	messageArea.style.color = color; // 색상 설정
}

//패스워드 찾기 폼 오픈
function openPasswordRecoveryForm() {
	var popup = window.open("", "_blank", "width=400,height=400,scrollbars=yes,resizable=yes");
	popup.document.write(`
        <html>
            <head>
                <title>비밀번호 찾기</title>
            </head>
            <body>
                <h1>비밀번호 찾기</h1>
                <form id="passwordRecoveryForm">
                    <label for="name">이름:</label>
                    <input type="text" id="name" required><br>
                    <label for="memberId">아이디:</label>
                    <input type="text" id="memberId" required><br>
                    <label for="email">이메일:</label>
                    <input type="email" id="email" required><br>
                    <input type="button" value="인증번호 전송" onclick="sendVerificationCode()">
                <div id="verificationCodeSection" style="display:none;">
		            <label for="verificationCode">인증번호:</label>
		            <input type="text" id="verificationCode" required>
		            <input type="button" value="인증 확인" onclick="verifyCode()">
		            <div id="verificationResult"></div>
        		</div>
        		<div id="passwordResetForm" style="display:none;">
			        <label for="newPassword">새 비밀번호:</label>
			        <input type="password" id="newPassword" required>
			        <br>
			        <label for="confirmPassword">비밀번호 확인:</label>
			        <input type="password" id="confirmPassword" required>
			        <br>
			        <input type="button" value="비밀번호 변경" onclick="resetPassword()">
			    </div>
                </form>
			    <div id="messageArea"></div>
			    <div id="closeButtonContainer" style="display:none;">
    					<button id="closeButton">닫기</button>
				</div>
            </body>
        </html>
    `);



	popup.sendVerificationCode = function() {
		var name = popup.document.getElementById('name').value;
		var memberId = popup.document.getElementById('memberId').value;
		var email = popup.document.getElementById('email').value;

		if (!name || !memberId || !email) {
			displayMessage(popup, "모든 정보를 입력해주세요!(´。＿ 。｀)", "red");

			return;
		}

		fetch('/recover-password', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			body: 'name=' + encodeURIComponent(name) +
				'&memberId=' + encodeURIComponent(memberId) +
				'&email=' + encodeURIComponent(email)
		})
			.then(response => {
				if (response.ok) {
					return response.text(); // 성공 응답 시 텍스트로 변환
				} else {
					throw new Error('잘못된 사용자 정보입니다.'); // 실패 시 오류 발생
				}
			})
			.then(data => {
				displayMessage(popup, data, "green");
				popup.document.getElementById('verificationCodeSection').style.display = 'block'; // 인증번호 입력란 표시
			})
			.catch(error => {
				console.error('Error:', error);
				displayMessage(popup, error.message, "red");
				// 여기에 인증번호 입력란을 숨기는 코드를 추가할 수 있습니다.
				popup.document.getElementById('verificationCodeSection').style.display = 'none'; // 인증번호 입력란 숨기기
			});
	};


	popup.verifyCode = function() {
		// 인증번호 입력 필드에서 사용자가 입력한 값 가져오기
		var verificationCode = popup.document.getElementById('verificationCode').value;

		// AJAX 요청을 사용하여 인증번호 검증
		fetch('/verify-codePwd', {
			method: 'POST', // POST 메소드 사용
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded', // 콘텐츠 타입 설정
			},
			body: 'verificationCode=' + encodeURIComponent(verificationCode) // 요청 본문에 인증번호 포함
		})
			.then(response => response.text()) // 서버 응답을 텍스트로 변환
			.then(data => {
				var verificationResult = popup.document.getElementById('verificationResult');

				if (data === "인증번호가 정확합니다.") {
					verificationResult.innerText = data;
					verificationResult.style.color = 'green'; // 텍스트 색상을 초록색으로 설정
					popup.document.getElementById('passwordResetForm').style.display = 'block'; // 비밀번호 재설정 폼 표시
				} else {
					verificationResult.innerText = "인증번호 불일치";
					verificationResult.style.color = 'red'; // 텍스트 색상을 빨간색으로 설정
				}
			})
			.catch(error => {
				console.error('Error:', error);
				popup.document.getElementById('verificationResult').innerText = '오류가 발생했습니다.';
				popup.document.getElementById('verificationResult').style.color = 'red';
			});

		popup.resetPassword = function() {
			var memberId = popup.document.getElementById('memberId').value;
			var newPassword = popup.document.getElementById('newPassword').value;
			var confirmPassword = popup.document.getElementById('confirmPassword').value;

			// 비밀번호 일치 확인
			if (newPassword !== confirmPassword) {
				displayMessage(popup, "비밀번호가 일치하지 않습니다.", "red");
				return;
			}

			// AJAX 요청을 사용하여 비밀번호 변경
			fetch('/reset-password', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					memberId: memberId, // 사용자 식별 정보
					newPassword: newPassword
				})
			})
				.then(response => response.json()) // JSON 형식으로 응답을 파싱
				.then(data => {
					if (data.success) {
						displayMessage(popup, data.message, "green");
						
						// 닫기 버튼 표시 및 이벤트 핸들러 설정
						var closeButtonContainer = popup.document.getElementById('closeButtonContainer');
						closeButtonContainer.style.display = 'block';

						var closeButton = popup.document.getElementById('closeButton');
						closeButton.onclick = function() {
							popup.close();
						};
					} else {
						displayMessage(popup, data.message, "red");
					}
				})
				.catch(error => {
					console.error('Error:', error);
					displayMessage(popup, '오류가 발생했습니다.', "red");
				});
		}
	}
}
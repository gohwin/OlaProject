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
            </body>
        </html>
    `);

    popup.sendVerificationCode = function() {
        var name = popup.document.getElementById('name').value;
        var memberId = popup.document.getElementById('memberId').value;
        var email = popup.document.getElementById('email').value;

        fetch('/recover-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'name=' + encodeURIComponent(name) +
                  '&memberId=' + encodeURIComponent(memberId) +
                  '&email=' + encodeURIComponent(email)
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            popup.document.getElementById('verificationCodeSection').style.display = 'block'; // 인증번호 입력 필드 표시
            // 추가적인 처리 (예: 인증번호 입력 필드 표시)
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
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
    var newPassword = popup.document.getElementById('newPassword').value;

    fetch('/reset-password', {
        // 비밀번호 재설정 요청 세부 사항...
    })
    .then(response => response.text())
    .then(data => {
        alert(data); // 비밀번호 변경 결과 알림
    })
    .catch(error => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    });
 };
popup.resetPassword = function() {
    var newPassword = popup.document.getElementById('newPassword').value;
    var confirmPassword = popup.document.getElementById('confirmPassword').value;

    // 비밀번호 일치 확인
    if (newPassword !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    // AJAX 요청을 사용하여 비밀번호 변경
    fetch('/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'newPassword=' + encodeURIComponent(newPassword)
    })
    .then(response => response.json()) // 서버 응답을 JSON 형태로 파싱
    .then(data => {
        if (data.success) {
            alert("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            alert("비밀번호 변경에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    });
};

};
}


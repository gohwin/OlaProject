document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('passwordRecoveryForm').onsubmit = function(event) {
        event.preventDefault();
        var name = document.getElementById('name').value;
        var memberId = document.getElementById('memberId').value;
        var email = document.getElementById('email').value;

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
            alert(data); // 서버 응답 처리
            document.getElementById('verificationCodeInput').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
    };

    // 인증번호 확인 및 비밀번호 재설정 로직 추가
    // ...
});
/**
 * 
 */


function validateId() {
	var idInput = document.getElementById('id');
	var idValue = idInput.value;

	// 정규표현식을 사용하여 영문과 숫자의 조합으로 6~12글자를 검사
	var idPattern = /^[a-zA-Z0-9]{6,12}$/;


	if (!idPattern.test(idValue)) {
		// 유효하지 않은 경우 메시지를 표시
		document.getElementById('idErrorMessage').innerText = '영문과 숫자의 조합으로 6~12글자로 입력해 주세요.';
		return false;
	} else {
		// 유효한 경우 메시지를 초기화
		document.getElementById('idErrorMessage').innerText = '';
		return true;
	}
}

// 비밀번호 길이와 조합에 따른 위험도 표시
function checkPasswordStrength() {
	var passwordInput = document.getElementById("password");
	var passwordStrength = document.getElementById("passwordStrength");
	var passwordStrengthText = document.getElementById("passwordStrengthText");
	var password = passwordInput.value;

	// 길이에 따른 위험도 판단
	var lengthScore = password.length < 6 ? 2 : password.length <= 12 ? 1 : 0;

	// 숫자와 소문자 포함 여부 체크
	var hasNumber = /\d/.test(password);
	var hasLowerCase = /[a-z]/.test(password);
	var hasUpperCase = /[A-Z]/.test(password);
	var hasSpecialChar = /[^a-zA-Z0-9]/.test(password);

	// 포함된 문자 종류에 따른 위험도 판단
	var charTypeScore = (hasNumber ? 1 : 0) + (hasLowerCase ? 1 : 0) + (hasUpperCase ? 1 : 0) + (hasSpecialChar ? 1 : 0);

	// 연속된 문자나 숫자 판단
	var consecutiveChars = password.match(/(\d)\1{2,}/g);
	var hasConsecutiveChars = consecutiveChars && consecutiveChars.length > 0;

	// 전체 위험도 계산
	var totalScore = lengthScore + charTypeScore;

	// 위험도에 따른 텍스트 표시
	var strengthMessage;

	// 비밀번호가 비어있을 때 메시지를 숨김
	if (password === "") {
		passwordStrength.style.backgroundColor = "";
		passwordStrengthText.innerHTML = "";
		return;
	}

	if (lengthScore === 2 || hasConsecutiveChars) {
		strengthMessage = "비밀번호가 위험해요ㅠㅠ (연속된 문자나 숫자가 3개 이상 사용 불가능)";
	} else if (lengthScore === 1 && !hasSpecialChar) {
		strengthMessage = "적절한 비밀번호예요 :)";
	} else {
		strengthMessage = "아주 좋은 비밀번호!";
	}

	// 위험도에 따른 가로 막대 표시
	var barColor;
	if (lengthScore === 2 || hasConsecutiveChars) {
		barColor = "red";
	} else if (lengthScore === 1 && !hasSpecialChar) {
		barColor = "yellow";
	} else {
		barColor = "green";
	}

	passwordStrength.style.backgroundColor = barColor;
	passwordStrengthText.innerText = strengthMessage;
}
// 비밀번호 확인
function checkPwd() {
	var password = document.getElementById("password").value;
	var confirmPassword = document.getElementById("pwdCheck").value;
	var matchMessage = document.getElementById("pwdMatchMessage");

	// 확인용 비밀번호가 비어있으면 메시지를 숨김
	if (password === "" && confirmPassword === "") {
		matchMessage.innerHTML = "";
		return;
	}

	// 일치하는지 여부 확인
	if (password === confirmPassword) {
		matchMessage.style.color = "green";
		matchMessage.innerHTML = "비밀번호가 일치합니다.";
	} else {
		matchMessage.style.color = "red";
		matchMessage.innerHTML = "비밀번호가 일치하지 않습니다.";
	}
}

// 전화번호 형식과 숫자만 입력을 검증하는 함수
function validatePhoneNumber() {
	var phoneInput = document.getElementById('phone');
	var phoneValue = phoneInput.value.replace(/-/g, ''); // 입력된 '-' 제거

	// 정규표현식을 사용하여 전화번호 형식 검증 (숫자만 허용)
	var phonePattern = /^\d{3}-\d{3,4}-\d{4}$/;

	if (!phonePattern.test(phoneValue)) {
		// 유효하지 않은 경우 메시지를 표시
		document.getElementById('phoneErrorMessage').innerText = '올바른 전화번호 형식이 아닙니다. (숫자만 입력 가능)';
		return false;
	} else {
		// 유효한 경우 메시지를 초기화
		document.getElementById('phoneErrorMessage').innerText = '';
		return true;
	}
}

// 전화번호를 세 칸으로 나누고 각 칸 사이에 '-'를 추가하는 함수
function formatPhoneNumber() {
	var phoneInput = document.getElementById('phone');
	var phoneValue = phoneInput.value.replace(/-/g, ''); // 입력된 '-' 제거

	// 전화번호를 세 칸으로 나누고 각 칸 사이에 '-'를 추가
	var formattedPhoneNumber = phoneValue.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');

	phoneInput.value = formattedPhoneNumber;
}

// 이메일 직접 입력 옵션이 선택되었을 때 실행되는 함수
function checkDirectInput() {
	var emailDomainSelect = document.getElementById('emailDomain');
	var directEmailInput = document.getElementById('directEmail');

	// "직접 입력" 옵션이 선택되었을 때 도메인 입력란을 표시하고, 그 외에는 숨김
	directEmailInput.style.display = emailDomainSelect.value === 'direct' ? 'block' : 'none';
}

// 폼 제출 시 유효성 검사 수행
function validateForm() {
	return validateId(); // 다른 필드에 대한 유효성 검사도 추가할 수 있음
}
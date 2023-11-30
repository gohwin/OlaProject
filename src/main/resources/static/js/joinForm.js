/**
 * 
 */


function validateId() {

  var idInput = document.getElementById('memberId');
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
// 아이디 중복체크 검사

function checkIdAvailability() {
    // id 입력란에 이벤트 리스너 추가
    var memberIdInput = document.getElementById('memberId');
    memberIdInput.addEventListener('input', function () {
        var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');
        idAvailabilityMessage.innerText = '';
        idAvailabilityMessage.style.color = '';  // 색상도 초기화
    });

    var memberId = memberIdInput.value;

    if (memberId === "") {
        alert("ID를 입력하세요!");
        return;
    }

    var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');
    idAvailabilityMessage.innerText = '';
    idAvailabilityMessage.style.color = '';  // 색상도 초기화

    fetch('/checkIdAvailability?memberId=' + memberId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.available) {
                idAvailabilityMessage.innerText = '사용 가능한 ID입니다.';
                idAvailabilityMessage.style.color = 'green';
            } else {
                idAvailabilityMessage.innerText = '이미 사용 중인 ID입니다. 다른 ID를 입력해주세요.';
                idAvailabilityMessage.style.color = 'red';
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
/////////////////////////////////////아이디 검사 끝/////////////////////////////////////////////

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
  var pwdCheck = document.getElementById("pwdCheck").value;
  var matchMessage = document.getElementById("pwdMatchMessage");

      // 확인용 비밀번호가 비어있으면 메시지를 숨김
      if (password === "" && pwdCheck === "") {
        matchMessage.innerHTML = "";
        return;
    }

  // 일치하는지 여부 확인
  if (password === pwdCheck) {
      matchMessage.style.color = "green";
      matchMessage.innerHTML = "비밀번호가 일치합니다.";
  } else {
      matchMessage.style.color = "red";
      matchMessage.innerHTML = "비밀번호가 일치하지 않습니다.";
  }

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

  var phoneInput = document.getElementById('phoneNumber');
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

  var phoneInput = document.getElementById('phoneNumber');
  var phoneValue = phoneInput.value.replace(/-/g, ''); // 입력된 '-' 제거

	var phoneInput = document.getElementById('phone');


	// 전화번호를 세 칸으로 나누고 각 칸 사이에 '-'를 추가
	var formattedPhoneNumber = phoneValue.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');

	phoneInput.value = formattedPhoneNumber;
}

// 이메일 직접 입력 옵션이 선택되었을 때 실행되는 함수
function checkDirectInput() {

  var selectedDomain = document.getElementById("emailDomain").value;
  var emailInput = document.getElementById("email");
  var emailDomainInput = document.getElementById("directEmail");

  // "직접 입력" 옵션이 선택되었을 때 도메인 입력란을 표시하고, 그 외에는 숨김
  if (selectedDomain === "direct") {
    // 도메인 입력란을 표시
    emailDomainInput.style.display = "inline-block";
  } else {
    // 도메인 입력란을 숨김
    emailDomainInput.style.display = "none";
  }

	var emailDomainSelect = document.getElementById('emailDomain');
	var directEmailInput = document.getElementById('directEmail');

	// "직접 입력" 옵션이 선택되었을 때 도메인 입력란을 표시하고, 그 외에는 숨김
	directEmailInput.style.display = emailDomainSelect.value === 'direct' ? 'block' : 'none';

}



// 폼 제출 시 유효성 검사 수행
// 중복확인 여부를 저장하는 변수

var isIdChecked = false;

function validateForm() {

// 사용자가 입력한 ID, 비밀번호, 확인용 비밀번호를 가져옵니다.
	var memberId = document.getElementById("memberId").value;
	var password = document.getElementById("password").value;
	var pwdCheck = document.getElementById("pwdCheck").value;

	// 비밀번호 길이에 따라서 부여되는 점수를 계산합니다.
	// 길이가 6 미만일 경우 2점, 6 이상 12 이하일 경우 1점, 그 이상일 경우 0점입니다.
	var lengthScore = password.length < 6 ? 2 : password.length <= 12 ? 1 : 0;
	
	// 연속된 문자나 숫자가 3개 이상 사용되었는지를 확인합니다.
	var consecutiveChars = password.match(/(.)\1{2,}/g);
	var hasConsecutiveChars = consecutiveChars && consecutiveChars.length > 0;
	
	// ID의 중복 확인 결과 메시지를 가져옵니다.
	var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');
	
	// 비밀번호 확인 부분에 대한 메시지를 가져옵니다.
	var matchMessage = document.getElementById("pwdMatchMessage");

    // ID를 입력하지 않은 경우
    if (memberId === "") {
        alert("ID를 입력하세요.");
        return false;
    }

    // 중복확인을 하지 않은 경우
    if (!isIdChecked) {
        alert("ID 중복확인을 해주세요.");
        return false;
    }

    // 중복확인을 했지만 중복된 ID인 경우
    if (idAvailabilityMessage.innerText === "이미 사용 중인 ID입니다.") {
        alert("이미 사용 중인 ID입니다. 다른 ID를 선택하세요.");
        isIdChecked = false;
        return false;
    }

    // 비밀번호 안전성 검사
    if (lengthScore === 2 || hasConsecutiveChars) {
        alert("비밀번호가 위험합니다. 다른 비밀번호를 선택하세요.");
        return false;
    }

    // 확인용 비밀번호가 비어있으면 메시지를 숨김
    if (password === "" && pwdCheck === "") {
        matchMessage.innerHTML = "";
        return false;
    }

    // 확인용 비밀번호가 입력되지 않았을 때
    if (pwdCheck === "") {
        alert("확인용 비밀번호를 입력하세요.");
        return false;
    }

    // 일치하는지 여부 확인
    if (password !== pwdCheck) {
        alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        return false;
    }
    
    // 이름이 입력되지 않았을때
    if (name === "") {
        alert("이름을 입력해 주세요!");
        return false;
    }

    return true; // 회원가입 진행
}

// 중복확인 결과를 처리하는 함수
function handleIdAvailability(available) {
    var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');

    if (available) {
        idAvailabilityMessage.innerText = '사용 가능한 ID입니다.';
        idAvailabilityMessage.style.color = 'green';
        isIdChecked = true; // 중복확인 완료 상태로 변경
    } else {
        idAvailabilityMessage.innerText = '이미 사용 중인 ID입니다. 다른 ID를 입력해주세요.';
        idAvailabilityMessage.style.color = 'red';
        isIdChecked = false; // 중복확인 결과가 실패했으므로 상태 초기화
    }
}

// 아이디 중복체크 검사
function checkIdAvailability() {
    // id 입력란에 이벤트 리스너 추가
    var memberIdInput = document.getElementById('memberId');
    memberIdInput.addEventListener('input', function () {
        var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');
        idAvailabilityMessage.innerText = '';
        idAvailabilityMessage.style.color = '';  // 색상도 초기화
    });

    var memberId = memberIdInput.value;

    if (memberId === "") {
        alert("ID를 입력하세요!");
        return;
    }

    var idAvailabilityMessage = document.getElementById('idAvailabilityMessage');
    idAvailabilityMessage.innerText = '';
    idAvailabilityMessage.style.color = '';  // 색상도 초기화

    fetch('/checkIdAvailability?memberId=' + memberId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 중복 확인 결과를 처리하는 함수 호출
            handleIdAvailability(data.available);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

	return validateId(); // 다른 필드에 대한 유효성 검사도 추가할 수 있음
}


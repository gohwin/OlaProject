package com.ola.entity;


import jakarta.persistence.Column;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
public class Member {

    @Id
    @Column(name = "memberID", unique = true)
    private String memberId;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String detailedAddress;

    private String memberType;

    // 유효성 검사
    public boolean isValid() {
        return memberId != null &&
               password != null && isValidPassword(password) &&
               name != null &&
               email != null && isValidEmail(email) &&
               phoneNumber != null && isValidPhoneNumber(phoneNumber) &&
               address != null &&
               detailedAddress != null &&
               memberType != null;
    }

    private boolean isValidPassword(String password) {
        // 패스워드의 유효성 검사 로직을 추가
        // 예: 최소 길이, 특수문자, 대소문자 포함 여부 등
        return password.length() >= 8; // 예제로 8글자 이상으로 설정
    }

    private boolean isValidEmail(String email) {
        // 이메일의 유효성 검사 로직을 추가
        // 정규표현식 사용
        return email.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // 전화번호의 유효성 검사 로직을 추가
        // 정규표현식 사용
        return phoneNumber.matches("^\\d{3}-\\d{3,4}-\\d{4}$");
    }
    @Enumerated(EnumType.STRING)
	private Role role;
    
    

}
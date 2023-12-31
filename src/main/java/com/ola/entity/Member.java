package com.ola.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	@Column(name = "memberId")
	private String memberId;

	private String password;

	private String name;

	private String email;

	private String phoneNumber;

	private String zipNum;

	private String address;

	private String detailedAddress;

	// 이메일 인증 관련 부분
	private boolean emailVerified = false;

	private String emailVerificationToken;

	private LocalDateTime emailVerificationTokenExpiration;

	@Enumerated(EnumType.STRING)
	private Role role;

	// 유효성 검사
	public boolean isValid() {
		return memberId != null && password != null && isValidPassword(password) && name != null && email != null
				&& isValidEmail(email) && phoneNumber != null && isValidPhoneNumber(phoneNumber) && zipNum != null
				&& address != null && detailedAddress != null;
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

	public void generateVerificationToken() {
		emailVerificationToken = UUID.randomUUID().toString();
	}

	// 이메일 인증 확인
	public void verifyEmail() {
		this.emailVerified = true;
	}

	@ManyToMany(mappedBy = "likedByMembers")
	private Set<Community> likedCommunities = new HashSet<>();
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
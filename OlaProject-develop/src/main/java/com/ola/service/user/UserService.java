package com.ola.service.user;

import com.ola.entity.Member;

public interface UserService {
	Member getUserById(String memberId);

	void updateUser(Member user);
	
	/* 비밀번호 찾은후 변경*/
	 boolean resetPassword(String memberId, String newPassword);
}

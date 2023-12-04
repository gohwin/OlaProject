package com.ola.service;

import com.ola.entity.Member;

public interface UserService {
	Member getUserById(String memberId);

	void updateUser(Member user);
}

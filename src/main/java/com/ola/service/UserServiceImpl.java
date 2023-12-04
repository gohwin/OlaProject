package com.ola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MemberRepository userRepository;

	@Override
	public Member getUserById(String memberId) {
		return userRepository.findById(memberId).orElse(null);
	}

	@Override
	public void updateUser(Member user) {
		Member existingUser = userRepository.findById(user.getMemberId()).orElse(null);

		if (existingUser != null) {
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setAddress(user.getAddress());
			existingUser.setDetailedAddress(user.getDetailedAddress());
			existingUser.setZipNum(user.getZipNum());
			
			userRepository.save(existingUser);
		}
	}

}

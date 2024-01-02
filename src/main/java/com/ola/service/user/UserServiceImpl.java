package com.ola.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MemberRepository userRepository; // member를 user로 잘못 입력;
	@Autowired
	private PasswordEncoder passwordEncoder;
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
	
	@Override
	public boolean resetPassword(String memberId, String newPassword) {
	    Member member = userRepository.findByMemberId(memberId);
	    if (member == null) {
	        return false;
	    }
	    String encryptedPassword = passwordEncoder.encode(newPassword); // 비밀번호 암호화
	    member.setPassword(encryptedPassword);
	    userRepository.save(member);
	    return true;
	}
	

}

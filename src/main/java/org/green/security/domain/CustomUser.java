package org.green.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.green.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Getter
public class CustomUser extends User {
	//CustomUser User를 상속받으므로, 부모클래스의 생성자를 호출해야만 정상적인 객체를 생성할 수 있음
	//AuthVO --> GrantedAuthority객체로 변환
	private MemberVO member;
	private static final long serialversionUID = 1L;
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);	
	}
	public CustomUser(MemberVO mvo) {
		super(mvo.getUserid(), mvo.getUserpw(), mvo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList()));
		this.member = mvo;
	}
}

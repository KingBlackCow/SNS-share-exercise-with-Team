package com.web.curation.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	MemberDao memberDao;

	public boolean hasSameEmail(String email) {
		Optional<Member> member = memberDao.getMemberByEmail(email);
		
		if(member.isPresent()) return true;
		return false;
	}

	public MemberDto registMember(MemberDto memberDto) {
		Member member = MemberAdapter.dtoToEntity(memberDto);
		memberDao.save(member);
		return memberDto;
	}
	
	public Optional<MemberDto> getUser(String email) {
		Optional<Member> member = memberDao.getMemberByEmail(email);
		Optional<MemberDto> memberDto = null;
		if(member.isPresent()) {
			memberDto = Optional.of(MemberAdapter.entityToDto(member.get()));
			return memberDto;
		}
		return memberDto;
	}

	public Optional<MemberDto> getUser(String email, String password) {
		Optional<Member> member = memberDao.getMemberByEmailAndPassword(email, password);
		Optional<MemberDto> memberDto = null;
		if(member.isPresent()) {
			memberDto = Optional.of(MemberAdapter.entityToDto(member.get()));
			return memberDto;
		}
		return memberDto;
	}

	public void certifyMemberByEmail(String email) {
		memberDao.updateAuthentication(email);
	}

	public Optional<MemberDto> getMemberByEmail(String email) {
		Optional<Member> member = memberDao.getMemberByEmail(email);
		Optional<MemberDto> memberDto = null;
		if(member.isPresent()) {
			memberDto = Optional.of(MemberAdapter.entityToDto(member.get()));
			return memberDto;
		}
		return memberDto;
	}

}

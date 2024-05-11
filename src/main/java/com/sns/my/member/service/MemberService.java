package com.sns.my.member.service;

import com.sns.my.member.model.Member;
import com.sns.my.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public long join(Member member){
        validateDupMember(member);
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDupMember(Member member){
        if(memberRepository.existsByUsername(member.getUsername())){
            throw new IllegalArgumentException("username already exist");
        }
    }

    public Optional<Member> findByUserName(String username){
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findByRealName(String realname){
        return memberRepository.findByUsername(realname);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}

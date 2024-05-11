package com.sns.my.security;

import com.sns.my.member.model.Member;
import com.sns.my.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MemberDetailService implements UserDetailsService {

    private MemberRepository memberRepository;

    public MemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()){
            return new MemberDetails(member.get());
        }
        else{
            throw new UsernameNotFoundException("username doesn't exist");
        }
    }
}

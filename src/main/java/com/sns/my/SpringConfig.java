package com.sns.my;

import com.sns.my.member.repository.MemberRepository;
import com.sns.my.member.repository.MemoryMemberRepository;
import com.sns.my.member.service.MemberService;
import com.sns.my.security.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ComponentScan(basePackages = "com.sns.my")
@Configuration
public class SpringConfig {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository, bCryptPasswordEncoder );
    }

    @Bean
    public MemberDetailService memberDetailService(){
        return new MemberDetailService(memberRepository);
    }
}

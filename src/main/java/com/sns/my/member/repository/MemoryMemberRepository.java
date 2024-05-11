package com.sns.my.member.repository;

import com.sns.my.member.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
//@Primary
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> memberRepository = new HashMap<>();
    private long cnt = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++cnt);
        memberRepository.put(cnt, member);
        log.info("Memory >> member joined: {}", member.getRealname());
        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(memberRepository.get(id));
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.values().stream()
                .filter(member -> member.getUsername().equals(username)).findAny();
    }

    @Override
    public Optional<Member> findByRealname(String realname) {
        return memberRepository.values().stream()
                .filter(member -> member.getUsername().equals(realname)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.values().stream().toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return (memberRepository.values().contains(username));
    }
}

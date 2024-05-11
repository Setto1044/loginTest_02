package com.sns.my.member.controller;

import com.sns.my.member.model.Member;
import com.sns.my.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
public class MemberController {

    private MemberService memberService;

    private Optional<Member> getSecuritySessionMember(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberService.findByUserName(username);
    }
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(){
        return "/loginform";
    }

    @GetMapping("/register")
    public String register(){
        return "/registerform";
    }
    @PostMapping("/register")
    public String goRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("realname") String realname){
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setRealname(realname);
        try{
            memberService.join(member);
            return "redirect:/login";
        }catch (Exception e){
            return "redirect:/register";
        }
    }

    @GetMapping("/main")
    public String goMain(Model model) {
        Optional<Member> member = getSecuritySessionMember();
        if(member.isPresent()){
            log.info("login Member: {}", member.get().getUsername());
            model.addAttribute("member", member.get());
            return "/main";
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/json")
    @ResponseBody
    public String getMemberJson(){
        Member member = getSecuritySessionMember().get();
        return member.toString();
    }
}

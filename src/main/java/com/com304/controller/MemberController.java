package com.com304.controller;

import com.com304.dto.MemberDTO;
import com.com304.entity.Member;
import com.com304.repository.MemberRepository;
import com.com304.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping("/register")
    public void register(MemberDTO memberDTO, Model model){
        model.addAttribute("memberDTO", memberDTO);
    }

    @PostMapping("/register")
    public String saveId(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult result, Model model){

        if (result.hasErrors()){
            return "member/register";
        }

        Member member = Member.createMember(memberDTO, passwordEncoder);
        memberService.register(member);
        return "redirect:/";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/login";
    }

    @GetMapping("/login")
    public String loginMember(){
        return "/member/login";
    }

    @GetMapping("/update")
    public void update(Principal principal, Model model){
        Member userInfo = memberRepository.findById(principal.getName());

        model.addAttribute("userInfo", userInfo);
    }

    @PostMapping("/update")
    public String doUpdate(MemberDTO memberDTO, Principal principal){

        Member member = Member.createMember(memberDTO, passwordEncoder);

        Member updateInfo = memberRepository.findById(principal.getName());

        if(member.getPw() != null){
            updateInfo.setPw(member.getPw());
        }
        if(member.getEmail() != null) {
            updateInfo.setEmail(member.getEmail());
        }
        if(member.getName() != null) {
            updateInfo.setName(member.getName());
        }
        if(member.getGender() != null) {
            updateInfo.setGender(member.getGender());
        }
        if(member.getPno_3() != null) {
            updateInfo.setPno_1(member.getPno_1());
            updateInfo.setPno_2(member.getPno_2());
            updateInfo.setPno_3(member.getPno_3());
        }
        if(member.getPost() != null) {
            updateInfo.setPost(member.getPost());
            updateInfo.setAdrs(member.getAdrs());
            updateInfo.setDetailadrs(member.getDetailadrs());
        }

        memberRepository.save(updateInfo);

        return "redirect:/";
    }
}

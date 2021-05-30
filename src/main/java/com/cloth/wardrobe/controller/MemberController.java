package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.member.MemberDto;
import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public String createForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/createMemberForm";
    }


}

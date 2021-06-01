package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.domain.member.Address;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.member.MemberDto;
import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping(value = "/new")
    public String createForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/createMemberForm";
    }

    @GetMapping(value = "/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);

        MemberDto memberDto = new MemberDto();
        Address address = member.getAddress();

        memberDto.setId(memberId);
        memberDto.setAccount(member.getAccount());
        memberDto.setName(member.getName());
        memberDto.setPicture(member.getPicture());
        memberDto.setCity(address.getCity());
        memberDto.setStreet(address.getStreet());
        memberDto.setZipcode(address.getZipcode());
        memberDto.setMemberAuthority(member.getMemberAuthority());

        model.addAttribute("member", memberDto);
        return "members/updateMemberForm";
    }

    @PostMapping(value = "/{memberId}/edit")
    public String updateMemberForm(@ModelAttribute("member") MemberDto memberDto, @PathVariable Long memberId, BindingResult result) {
        if(result.hasErrors()) {
            return "members/updateMemberForm";
        }

        memberService.updateMember(memberId, memberDto.getName(), memberDto.getPicture(), memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode());
        return "redirect:/";
    }
}

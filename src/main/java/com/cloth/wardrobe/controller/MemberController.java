package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.domain.member.Address;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberAuthority;
import com.cloth.wardrobe.dto.member.MemberDto;
import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/new")
    public String createForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String create(@Valid @ModelAttribute("member") MemberDto dto, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());

        Member member = new Member();
        member.setName(dto.getName());
        member.setAccount(dto.getAccount());
        member.setPassword(dto.getPassword());
        member.setAddress(address);
        member.setMemberAuthority(MemberAuthority.COMMON);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);

        MemberDto memberDto = new MemberDto();
        Address address = member.getAddress();

        memberDto.setId(memberId);
        memberDto.setName(member.getName());
        memberDto.setCity(address.getCity());
        memberDto.setStreet(address.getStreet());
        memberDto.setZipcode(address.getZipcode());
        memberDto.setMemberAuthority(member.getMemberAuthority());
        memberDto.setAccount(member.getAccount());

        model.addAttribute("member", memberDto);
        return "members/updateMemberForm";
    }
}

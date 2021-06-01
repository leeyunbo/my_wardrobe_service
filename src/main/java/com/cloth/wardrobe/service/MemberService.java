package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 멤버 생성
     * @param member
     * @return 회원 번호
     */
    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);

        // 영속성!
        return member.getId();
    }

    /**
     * 멤버 정보 변경
     * @param memberId
     * @param name
     * @param city
     * @param street
     * @param zipcode
     */
    @Transactional
    public void updateMember(Long memberId, String name, String picture, String city, String street, String zipcode) {
        Optional<Member> findItem = memberRepository.findById(memberId);
        findItem.get().change(name, picture, city, street, zipcode);
    }

    /**
     * 멤버 전체 조회
     * @return 멤버 리스트
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 멤버 단일 조회
     * @param memberId
     * @return 멤버 객체
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }
}

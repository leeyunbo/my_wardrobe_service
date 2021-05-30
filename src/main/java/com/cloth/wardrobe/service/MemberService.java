package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberAuthority;
import com.cloth.wardrobe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        validateDuplicateMember(member);
        memberRepository.save(member);

        // 영속성!
        return member.getId();
    }

    /**
     * 물론 복수의 WAS를 통해 접근하여 동시에 같은 계정을 생성하는 경우가 발생하면 이 로직이 틀어질 수도 있다.
     * 그런 경우를 대비하여 디비 필드에 UNIQUE 제약 조건을 걸어주는 것이 권장된다.
     * 중복 회원 검증
     * @param member : 회원 엔티티
     */
    private void validateDuplicateMember(Member member) {
        Member findMembers = memberRepository.findByName(member.getName());
        if (findMembers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 멤버 정보 변경
     * @param memberId
     * @param name
     * @param password
     * @param city
     * @param street
     * @param zipcode
     * @param memberAuthority
     */
    @Transactional
    public void updateMember(Long memberId, String name, String password, String city, String street, String zipcode, MemberAuthority memberAuthority) {
        Member findItem = memberRepository.findOne(memberId);
        findItem.change(name, password, city, street, zipcode, memberAuthority);
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
        return memberRepository.findOne(memberId);
    }

    /**
     * 멤버를 이름을 통해 조회한다.
     * @param memberName
     * @return 멤버 객체
     */
    public Member findOne(String memberName) {
        return memberRepository.findByName(memberName);
    }
}

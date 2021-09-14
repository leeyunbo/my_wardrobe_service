package com.cloth.wardrobe.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 소셜 로그인으로 반환되는 email을 통해 이미 생성된 사용자인지 판단하기 위한 메서드
     * @param email
     * @return Member 객체
     */
    Optional<Member> findByEmail(String email);

}

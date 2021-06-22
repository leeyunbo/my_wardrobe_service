package com.cloth.wardrobe.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 소셜 로그인으로 반환되는 email을 통해 이미 생성된 사용자인지 판단하기 위한 메서드
     * @param memberId
     * @return Like 객체
     */
    Optional<Like> findByMember_Id(Long memberId);
}

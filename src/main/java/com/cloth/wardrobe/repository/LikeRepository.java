package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.community.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByMember_IdAndWardrobe_Id(Long memberId, Long wardrobeId);

    Like findByMember_IdAndCloth_Id(Long memberId, Long clothId);

    Like findByMember_IdAndRecord_Id(Long memberId, Long recordId);

}

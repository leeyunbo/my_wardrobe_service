package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {

    @Query("SELECT c FROM Cloth c ORDER BY c.likeCnt DESC")
    Page<Cloth> findAll(Pageable pageable);

    Page<Cloth> findClothsByWardrobeId(Pageable pageable, Long wardrobeId);

    List<Cloth> findClothsByMemberOrderByLikeCntDesc(Member member);
}

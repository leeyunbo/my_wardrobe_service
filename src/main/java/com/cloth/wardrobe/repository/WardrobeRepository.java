package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardrobeRepository extends JpaRepository<Wardrobe, Long> {

    @Query("SELECT w FROM Wardrobe w WHERE w.isPublic = 'true' ORDER BY w.likeCnt DESC")
    List<Wardrobe> findAll();

    Optional<Wardrobe> findWardrobeByMember(Member member);

}

package com.cloth.wardrobe.domain.clothes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WardrobeRepository extends JpaRepository<Wardrobe, Long> {

    @Query("SELECT w FROM Wardrobe w WHERE w.isPublic = 'true' ORDER BY w.likeCnt DESC")
    Page<Wardrobe> findAll(Pageable pageable);

}

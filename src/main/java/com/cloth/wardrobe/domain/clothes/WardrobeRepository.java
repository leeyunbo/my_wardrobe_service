package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardrobeRepository extends JpaRepository<Wardrobe, Long> {

}

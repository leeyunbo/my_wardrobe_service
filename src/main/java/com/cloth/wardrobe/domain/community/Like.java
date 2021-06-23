package com.cloth.wardrobe.domain.community;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public Like(Member member, Wardrobe wardrobe, Cloth cloth, Record record) {
        this.member = member;
        this.wardrobe = wardrobe;
        this.cloth = cloth;
        this.record = record;
    }
}
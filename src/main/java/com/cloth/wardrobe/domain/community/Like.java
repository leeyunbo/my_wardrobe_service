package com.cloth.wardrobe.domain.community;

import com.cloth.wardrobe.domain.BaseTimeEntity;
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
public class Like extends BaseTimeEntity {
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

    public Like(Member member) {
        this.member = member;
    }

    public Like setPost(Post post) {
        if(post instanceof Cloth) this.cloth = (Cloth) post;
        else if(post instanceof Wardrobe) this.wardrobe = (Wardrobe) post;
        else this.record = (Record) post;

        return this;
    }

}

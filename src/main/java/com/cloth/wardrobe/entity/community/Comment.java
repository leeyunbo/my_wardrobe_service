package com.cloth.wardrobe.entity.community;

import com.cloth.wardrobe.entity.common.BaseTimeEntity;
import com.cloth.wardrobe.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "comment_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    private String content;

    @Builder
    public Comment(Long id, PostEntity post, Member member, String content) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.content = content;
    }
}

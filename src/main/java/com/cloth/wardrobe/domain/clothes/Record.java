package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "records")
@Getter
@NoArgsConstructor
public class Record extends Post {

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private final List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "record", fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    private String content;

    private int likeCnt;

    @Builder
    public Record(Member member, Image image, Cloth cloth, String content) {
        this.member = member;
        this.image = image;
        this.cloth = cloth;
        this.content = content;
    }


    @Override
    public Post changeLikeCnt(Like like, MethodType type) {
        switch (type) {
            case ADD:
                this.likeCnt++;
                this.likes.add(like);
                like.setRecord(this);
                break;
            case DELETE:
                this.likeCnt--;
                this.likes.remove(like);
                like.setRecord(null);
                break;
        }
        return this;
    }

    /**
     * 코디 코멘트 작성
     * @param comment
     * @return
     */
    @Override
    public Record writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setRecord(this);
        return this;
    }

    /**
     * 코디 코멘트 제거
     */
    @Override
    public Record deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setRecord(null);
        return this;
    }
}

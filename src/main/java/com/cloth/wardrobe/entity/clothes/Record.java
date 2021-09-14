package com.cloth.wardrobe.entity.clothes;

import com.cloth.wardrobe.entity.community.Comment;
import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.entity.community.PostEntity;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "records", indexes = {@Index(columnList = "id")})
public class Record extends PostEntity {

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private final List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "record", fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    private String content;

    @Builder
    public Record(Member member, Image image, Cloth cloth, String content) {
        super(member, cloth, image);
        this.content = content;
    }


    @Override
    public PostEntity changeLikeCnt(Like like, MethodType type) {
        switch (type) {
            case ADD:
                super.setLikeCnt(super.getLikeCnt()+1);;
                this.likes.add(like);
                like.setRecord(this);
                break;
            case DELETE:
                super.setLikeCnt(super.getLikeCnt()-1);
                this.likes.remove(like);
                like.setRecord(null);
                break;
        }
        return this;
    }

    @Override
    public Record writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setRecord(this);
        return this;
    }

    @Override
    public Record deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setRecord(null);
        return this;
    }
}

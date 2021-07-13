package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.s3.Image;
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

    /**
     *  LAZY : 프록시 객체를 우선으로 만들어놓고 할당해 놓은 후, 실제로 호출될 때 쿼리가 날라간다.
     *  ManyToOne에서 사용된다.
     *  반대는 EAGER
     */
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private final List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private final List<Like> likes = new ArrayList<>();

    private String subject;

    private String content;

    private int likeCnt;

    @Builder
    public Record(Cloth cloth, String subject, String content) {
        this.cloth = cloth;
        this.subject = subject;
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
}

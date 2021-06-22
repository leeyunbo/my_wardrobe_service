package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "records")
@Getter
@NoArgsConstructor
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    /**
     *  LAZY : 프록시 객체를 우선으로 만들어놓고 할당해 놓은 후, 실제로 호출될 때 쿼리가 날라간다.
     *  ManyToOne에서 사용된다.
     *  반대는 EAGER
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    private String subject;

    private String content;
}

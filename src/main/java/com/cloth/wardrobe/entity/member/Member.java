package com.cloth.wardrobe.entity.member;

import com.cloth.wardrobe.entity.BaseTimeEntity;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.community.Comment;
import com.cloth.wardrobe.entity.community.Like;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter @Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // 옷장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Wardrobe wardrobe;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Cloth> clothes = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    /**
     * 사용자 정보 변경
     * @param name
     */
    public Member change(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

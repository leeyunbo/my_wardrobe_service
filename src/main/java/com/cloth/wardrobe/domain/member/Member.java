package com.cloth.wardrobe.domain.member;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.clothes.Comment;
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
    private MemberAuthority memberAuthority;

    // 옷장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    // 댓글
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String name, String email, String picture, MemberAuthority memberAuthority) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.memberAuthority = memberAuthority;
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

    public String getAuthorityKey() {
        return this.memberAuthority.getKey();
    }

}

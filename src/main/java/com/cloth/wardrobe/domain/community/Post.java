package com.cloth.wardrobe.domain.community;


import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.MethodType;
import com.cloth.wardrobe.domain.clothes.Record;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.MappedSuperclass;
import java.util.List;


@Setter
@Getter
@MappedSuperclass
public abstract class Post extends BaseTimeEntity {

    public abstract Post changeLikeCnt(Like like, MethodType type);
    public abstract Post writeComment(Comment comment);
    public abstract Post deleteComment(Comment comment);
}
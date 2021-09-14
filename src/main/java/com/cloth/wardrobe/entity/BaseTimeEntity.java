package com.cloth.wardrobe.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 모든 Entity들의 상위 클래스로서 생성시간, 수정시간을 가지고 있다.
 */

@Getter
@MappedSuperclass // Entity 클래스들이 이 클래스를 상속받게 되면 상속받은 필드들도 컬럼으로 인식함
@EntityListeners(AuditingEntityListener.class) // Auditing 기능(시간을 자동으로 넣어주는 기능) 포함
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}

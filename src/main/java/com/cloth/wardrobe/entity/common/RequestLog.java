package com.cloth.wardrobe.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(indexes = {@Index(columnList = "requestUri")})
public class RequestLog {
    @Id
    private String uuid;
    private String requestUri;
    private String clientIp;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp reqTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp resTime;

    @Lob
    private String header;

    @Lob
    private String body;

    @Lob
    private String responseMsg;
}

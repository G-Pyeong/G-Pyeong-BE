package com.gpyeong.core.global.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * MongoDB에서 자동 증가 ID를 관리하기 위한 시퀀스 도큐먼트
 */
@Document(collection = "database_sequences")
@Getter
@Setter
public class DatabaseSequence {

    @Id
    private String id; // 시퀀스 탈별자 (users_sequence)

    private long seq; // 현재 시퀀스 번호
}

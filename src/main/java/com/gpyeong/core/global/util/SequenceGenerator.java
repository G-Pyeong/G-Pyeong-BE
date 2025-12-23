package com.gpyeong.core.global.util;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.gpyeong.core.global.mongodb.DatabaseSequence;

import lombok.RequiredArgsConstructor;

/**
 * MongoDB의 시퀀스 값을 원자적으로 증가시키고 가져오는 유틸리티
 */
@Component
@RequiredArgsConstructor
public class SequenceGenerator {

    private final MongoOperations mongoOperations;

    /**
     * 특정 시퀀스 이름에 해당하는 다음 번호를 생성합니다.
     * @param seqName 시퀀스 식별자
     * @return 생성된 시퀀스 번호
     */
    public Integer generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1), 
                options().returnNew(true).upsert(true),
                DatabaseSequence.class);
                
        return !Objects.isNull(counter) ? (int) counter.getSeq() : 1;
    }
}

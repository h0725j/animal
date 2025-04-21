package com.test.animal.service;

import com.test.animal.entity.Animal;
import com.test.animal.entity.enums.ActiveState;
import com.test.animal.es.Document.AnimalDocument;
import com.test.animal.repository.AnimalRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalEsService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final AnimalRepository animalRepository;

    public void saveBulk(List<Animal> animals) {
        if (animals.isEmpty()) {
            return;
        }
        String indexName = "animals";

        try {

            // 필터링 및 변환
            List<IndexQuery> queries = animals.stream()
                    .filter(animal -> ActiveState.ACTIVE.equals(animal.getActiveState()))
                    .map(animal -> {
                        try {
                            AnimalDocument document = AnimalDocument.fromEntity(animal);
                            return new IndexQueryBuilder()
                                    .withIndex(indexName)
                                    .withObject(document)
                                    .build();
                        } catch (Exception e) {
                            log.error("동물 ID: {}의 문서 변환 중 오류 발생: {}", animal.getDesertionNo(), e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            log.info("변환된 쿼리 수: {}", queries.size());

            if (queries.isEmpty()) {
                log.warn("저장할 ACTIVE 상태의 동물 데이터가 없습니다.");
                return;
            }

            // 벌크 인덱싱
            elasticsearchOperations.bulkIndex(queries, AnimalDocument.class);
            log.info("{}개의 ACTIVE 상태 동물 데이터가 Elasticsearch에 성공적으로 저장되었습니다.", queries.size());

        } catch (Exception e) {
            log.error("Elasticsearch 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Elasticsearch 저장 실패", e);
        }
    }
}

package com.test.animal.repository;

import com.test.animal.es.Document.AnimalDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalSearchRepository extends ElasticsearchRepository<AnimalDocument, String> {

}

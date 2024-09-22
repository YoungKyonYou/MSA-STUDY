package com.youyk.categoryservice.repository;


import com.youyk.categoryservice.entity.CatalogEntity;
import java.util.Optional;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends ReactiveCrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}

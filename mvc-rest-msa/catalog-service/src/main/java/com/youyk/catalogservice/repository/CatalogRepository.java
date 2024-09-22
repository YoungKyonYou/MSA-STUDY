package com.youyk.catalogservice.repository;

import com.youyk.catalogservice.entity.CatalogEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Long>{
    Optional<CatalogEntity> findByProductId(String productId);
}

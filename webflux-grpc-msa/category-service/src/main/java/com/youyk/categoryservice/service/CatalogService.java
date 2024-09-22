package com.youyk.categoryservice.service;


import com.youyk.categoryservice.entity.CatalogEntity;
import java.util.List;
import reactor.core.publisher.Flux;

public interface CatalogService {
    Flux<CatalogEntity> getAllCatalogs();
}

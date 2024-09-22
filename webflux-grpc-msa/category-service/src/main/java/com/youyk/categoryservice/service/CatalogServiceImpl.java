package com.youyk.categoryservice.service;


import com.youyk.categoryservice.entity.CatalogEntity;
import com.youyk.categoryservice.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;
    @Override
    public Flux<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}

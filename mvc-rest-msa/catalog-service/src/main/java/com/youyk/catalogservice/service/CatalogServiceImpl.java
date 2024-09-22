package com.youyk.catalogservice.service;

import com.youyk.catalogservice.dto.CatalogDto;
import com.youyk.catalogservice.entity.CatalogEntity;
import com.youyk.catalogservice.repository.CatalogRepository;
import com.youyk.catalogservice.vo.ResponseCatalog;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;
    @Override
    public List<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}

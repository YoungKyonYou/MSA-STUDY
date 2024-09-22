package com.youyk.catalogservice.service;

import com.youyk.catalogservice.dto.CatalogDto;
import com.youyk.catalogservice.entity.CatalogEntity;
import java.util.List;
import org.springframework.stereotype.Service;


public interface CatalogService {
    List<CatalogEntity> getAllCatalogs();
}

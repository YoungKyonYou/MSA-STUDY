package com.youyk.catalogservice.controller;

import com.youyk.catalogservice.dto.CatalogDto;
import com.youyk.catalogservice.entity.CatalogEntity;
import com.youyk.catalogservice.service.CatalogService;
import com.youyk.catalogservice.vo.ResponseCatalog;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Catalog Service on PORT %s".formatted(env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){

        final List<CatalogEntity> entities = catalogService.getAllCatalogs();

        final List<ResponseCatalog> response = ResponseCatalog.from(entities);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}

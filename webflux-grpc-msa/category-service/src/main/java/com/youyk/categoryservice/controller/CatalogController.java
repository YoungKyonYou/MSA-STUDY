package com.youyk.categoryservice.controller;


import com.youyk.categoryservice.entity.CatalogEntity;
import com.youyk.categoryservice.service.CatalogService;
import com.youyk.categoryservice.vo.ResponseCatalog;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    public Flux<ResponseCatalog> getCatalogs(){
        return catalogService.getAllCatalogs()
                .map(ResponseCatalog::from);
    }


}

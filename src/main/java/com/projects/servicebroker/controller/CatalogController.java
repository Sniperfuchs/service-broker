package com.projects.servicebroker.controller;

import com.projects.servicebroker.model.Catalog;
import com.projects.servicebroker.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<Catalog> getServiceOfferings() {
        return new ResponseEntity<>(catalogService.getCatalog(), HttpStatus.OK);
    }

}

package com.projects.servicebroker.model;

import lombok.Data;

import java.util.List;

@Data
public class Catalog {
    private List<ServiceOffering> services;

    public Catalog(List<ServiceOffering> services) {
        this.services = services;
    }
}

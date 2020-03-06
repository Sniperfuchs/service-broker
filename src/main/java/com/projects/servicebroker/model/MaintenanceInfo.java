package com.projects.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class MaintenanceInfo {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty(message = "maintenance_info version must not be empty")
    private String version; //TODO must conform to https://semver.org/spec/v2.0.0.html
    private String description;

    public MaintenanceInfo(String version, String description) {
        this.version = version;
        this.description = description;
    }

    public MaintenanceInfo() {} //Necessary
}

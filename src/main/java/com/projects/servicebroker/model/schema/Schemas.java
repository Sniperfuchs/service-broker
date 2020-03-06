package com.projects.servicebroker.model.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Schemas {
    @JsonIgnore
    @Id
    private Long id;
}

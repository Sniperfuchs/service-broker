package com.projects.servicebroker.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class ServiceBinding {
    //TODO
    @Id
    private String id;
    private String service_id;
    private String plan_id;
    //TODO add optional stuff

}

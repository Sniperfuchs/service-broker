package com.projects.servicebroker.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class ServiceInstance {
    //TODO
    @Id
    private String id;
    private String service_id;
    private String plan_id;
    //Private Object context; //TODO
    private String organization_guid;
    private String space_guid;
    //private Object parameters //TODO
    @OneToOne
    @Cascade(CascadeType.ALL)
    private MaintenanceInfo maintenance_info;
}

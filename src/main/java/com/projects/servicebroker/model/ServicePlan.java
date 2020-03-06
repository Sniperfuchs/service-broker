package com.projects.servicebroker.model;

import com.projects.servicebroker.model.schema.Schemas;
import lombok.Data;
import net.bytebuddy.utility.RandomString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class ServicePlan {
    @Id
    private String id;
    private String name;
    private String description;

    /*
    private Object metadata; //TODO Make own class

    private boolean free;
    private boolean bindable;
    private boolean plan_updateable;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Schemas schemas;
    private int maximum_polling_duration;


     */
    @OneToOne
    @Cascade(CascadeType.ALL)
    private MaintenanceInfo maintenance_info;


    public ServicePlan(String id, String name, String description, MaintenanceInfo maintenance_info) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maintenance_info = maintenance_info;
    }

    public ServicePlan() {
        this.id = RandomString.make();
    }
}

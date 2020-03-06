package com.projects.servicebroker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class DashboardClient {
    @Id
    private String id;
    private String secret;
    private String redirect_uri;
}

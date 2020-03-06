package com.projects.servicebroker.controller.response;

import lombok.Data;

@Data
public class ServiceInstanceGetResponse {
    private String service_id;
    private String plan_id;
    private String dashboard_url;
    private Object parameters; //TODO probably hashmap
}

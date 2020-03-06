package com.projects.servicebroker.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ServiceBindingPutRequest {
    //private Object context;
    @NotEmpty(message = "service_id must not be empty")
    private String service_id; //TODO implement comments
    @NotEmpty(message = "plan_id must not be empty")
    private String plan_id;
    /*
    private String app_guid;
    private BindResource bind_resource;
    private Object parameters;

     */
}

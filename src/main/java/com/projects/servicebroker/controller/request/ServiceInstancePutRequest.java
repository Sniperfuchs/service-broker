package com.projects.servicebroker.controller.request;

import com.projects.servicebroker.model.MaintenanceInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ServiceInstancePutRequest {
    @NotNull(message = "service_id must not be null")
    private String service_id;
    @NotNull(message = "plan_id must not be null")
    private String plan_id;
    private Object context;
    @NotEmpty(message = "organization_guid must not be empty")
    private String organization_guid;
    @NotEmpty(message = "space_guid must not be empty")
    private String space_guid;
    private Object parameters;
    private MaintenanceInfo maintenance_info; //TODO
}

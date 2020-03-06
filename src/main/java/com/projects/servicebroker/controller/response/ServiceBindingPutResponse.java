package com.projects.servicebroker.controller.response;

import lombok.Data;

@Data
public class ServiceBindingPutResponse {
    //TODO
    //private BindingMetaData metadata;
    private Object credentials;
    //private String syslog_drain_url;
    //private String route_service_url;
    /*
    private VolumeMount volume_mounts;
    private List<Endpoint> endpoints;

     */

    public ServiceBindingPutResponse() {}
}

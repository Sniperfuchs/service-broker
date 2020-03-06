package com.projects.servicebroker.service;

import com.projects.servicebroker.controller.request.ServiceInstancePutRequest;
import com.projects.servicebroker.controller.response.ServiceInstanceGetResponse;
import com.projects.servicebroker.controller.response.ServiceInstancePutResponse;
import com.projects.servicebroker.exception.*;
import com.projects.servicebroker.model.ServiceInstance;
import com.projects.servicebroker.repository.ServiceInstanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceInstanceService {
    private final ServiceInstanceRepository serviceInstanceRepository;
    private final CatalogService catalogService;

    public ServiceInstanceService(ServiceInstanceRepository serviceInstanceRepository, CatalogService catalogService) {
        this.serviceInstanceRepository = serviceInstanceRepository;
        this.catalogService = catalogService;
    }

    public ResponseEntity getServiceInstance(String instance_id, String service_id, String plan_id) throws ServiceInstanceNotFoundException {
        Optional<ServiceInstance> serviceInstance = serviceInstanceRepository.findById(instance_id);
        if(!serviceInstance.isPresent()) {
            throw new ServiceInstanceNotFoundException("Service instance with id " + instance_id + " not found");
        }
        ServiceInstanceGetResponse response = new ServiceInstanceGetResponse();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    public ResponseEntity createServiceInstance(String instance_id, ServiceInstancePutRequest request) throws ServiceInstanceConflictException, NoSuchServiceException, MaintenanceInfoMismatchException {

        if(!catalogService.containsServiceAndPlan(request.getService_id(), request.getPlan_id())) {
            throw new NoSuchServiceException("Catalog does not contain service of id " + request.getService_id() + " or plan of id " + request.getPlan_id());
        }

        if(request.getMaintenance_info() != null) {
            if(!catalogService.maintenanceInfoMatchesExisting(request.getPlan_id(), request.getMaintenance_info())) {
                throw new MaintenanceInfoMismatchException("Provided maintenance_info version " + request.getMaintenance_info().getVersion() + " does not match existing in catalog");
            }
        }

        if(instanceExistsWithSameParameters(instance_id, request.getService_id(), request.getPlan_id(), request.getOrganization_guid(), request.getSpace_guid())) {
            ServiceInstancePutResponse response = new ServiceInstancePutResponse();
            response.setDashboard_url("asdgasdg");
            response.setOperation("sdgdsf");
            return new ResponseEntity(response, HttpStatus.OK);
        }
        if(serviceInstanceRepository.existsById(instance_id)) {
            throw new ServiceInstanceConflictException("Service instance with same id but different attributes already exists");
        }

        //TODO put this all in constructor
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setId(instance_id);
        serviceInstance.setService_id(request.getService_id());
        serviceInstance.setPlan_id(request.getPlan_id());
        serviceInstance.setOrganization_guid(request.getOrganization_guid());
        serviceInstance.setSpace_guid(request.getSpace_guid());
        serviceInstance.setMaintenance_info(request.getMaintenance_info());
        serviceInstanceRepository.save(serviceInstance);
        //TODO use helm to instantiate service

        ServiceInstancePutResponse response = new ServiceInstancePutResponse();
        response.setDashboard_url("asdgasdg");
        response.setOperation("sdgdsf");

        return new ResponseEntity(response, HttpStatus.CREATED); //TODO fill response
    }

    public void deleteServiceInstance(String instance_id, String service_id, String plan_id) throws ServiceInstanceGoneException {
        Optional<ServiceInstance> serviceInstance = serviceInstanceRepository.findById(instance_id);
        if(!serviceInstance.isPresent()) {
            throw new ServiceInstanceGoneException("Service instance with id " + instance_id + " gone");
        }
        //TODO add 422 and 202
        serviceInstanceRepository.delete(serviceInstance.get());
    }

    private boolean instanceExistsWithSameParameters(String instance_id, String service_id, String plan_id, String organization_guid, String space_guid) {
        Optional<ServiceInstance> serviceInstance = serviceInstanceRepository.findById(instance_id);
        if(serviceInstance.isPresent()) {
            ServiceInstance instance = serviceInstance.get();
            return serviceInstance.isPresent() && serviceInstance.get().getService_id().equals(service_id) && serviceInstance.get().getPlan_id().equals(plan_id)
                    && serviceInstance.get().getOrganization_guid().equals(organization_guid) && serviceInstance.get().getSpace_guid().equals(space_guid);
        }
        return false;
    }
}

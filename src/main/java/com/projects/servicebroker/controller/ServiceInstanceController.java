package com.projects.servicebroker.controller;

import com.projects.servicebroker.controller.request.ServiceInstancePutRequest;
import com.projects.servicebroker.exception.*;
import com.projects.servicebroker.service.ServiceInstanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@RestController
public class ServiceInstanceController {
    private final ServiceInstanceService serviceInstanceService;

    public ServiceInstanceController(ServiceInstanceService serviceInstanceService) {
        this.serviceInstanceService = serviceInstanceService;
    }

    @GetMapping(value = "/v2/service_instances/{instance_id}")
    public ResponseEntity getServiceInstance(@PathVariable String instance_id,
                                             @RequestParam(required = false) String service_id, //TODO optional parameters
                                             @RequestParam(required = false) String plan_id) throws ServiceInstanceNotFoundException, MissingParameterException {

        if(instance_id == null || instance_id.isEmpty()) {
            throw new MissingParameterException("instance_id must not be empty");
        }

        return serviceInstanceService.getServiceInstance(instance_id, service_id, plan_id);
    }

    @PutMapping(value = "/v2/service_instances/{instance_id}")
    public ResponseEntity putServiceInstance(@PathVariable String instance_id,
                                             @RequestBody ServiceInstancePutRequest request,
                                             @RequestParam(required = false) boolean accepts_incomplete) throws ServiceInstanceConflictException, RequestValidationException, MissingParameterException, NoSuchServiceException, MaintenanceInfoMismatchException {

        if(instance_id == null || instance_id.isEmpty()) {
            throw new MissingParameterException("instance_id must not be empty");
        }

        Set<ConstraintViolation<ServiceInstancePutRequest>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(request);
        if(!validate.isEmpty()) {
            throw new RequestValidationException(validate);
        }

        return serviceInstanceService.createServiceInstance(instance_id, request);
    }

    @DeleteMapping({"/v2/service_instances/{instance_id}"})
    public ResponseEntity deleteServiceInstance(@PathVariable String instance_id,
                                                @RequestParam String service_id,
                                                @RequestParam String plan_id,
                                                @RequestParam(required = false) boolean accepts_incomplete) throws ServiceInstanceGoneException, MissingParameterException {

        if(instance_id == null || instance_id.isEmpty()) {
            throw new MissingParameterException("instance_id must not be empty");
        }

        if(service_id == null || service_id.isEmpty()) {
            throw new MissingParameterException("service_id must not be empty");
        }

        if(plan_id == null || plan_id.isEmpty()) {
            throw new MissingParameterException("plan_id must not be empty");
        }

        serviceInstanceService.deleteServiceInstance(instance_id, service_id, plan_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

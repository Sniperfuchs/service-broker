package com.projects.servicebroker.controller;

import com.projects.servicebroker.controller.request.ServiceBindingPutRequest;
import com.projects.servicebroker.exception.MissingParameterException;
import com.projects.servicebroker.exception.RequestValidationException;
import com.projects.servicebroker.exception.ServiceBindingConflictException;
import com.projects.servicebroker.exception.ServiceBindingGoneException;
import com.projects.servicebroker.service.ServiceBindingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Controller
public class ServiceBindingController {
    private final ServiceBindingService serviceBindingService;

    public ServiceBindingController(ServiceBindingService serviceBindingService) {
        this.serviceBindingService = serviceBindingService;
    }

    @PutMapping(value = "/v2/service_instances/{instance_id}/service_bindings/{binding_id}")
    public ResponseEntity putServiceBinding(@PathVariable String instance_id,
                                            @PathVariable String binding_id,
                                            @RequestParam(required = false) boolean accepts_incomplete,
                                            @RequestBody ServiceBindingPutRequest request) throws ServiceBindingConflictException, MissingParameterException, RequestValidationException {

        if(instance_id == null || instance_id.isEmpty()) {
            throw new MissingParameterException("instance_id must not be empty");
        }

        if(binding_id == null || binding_id.isEmpty()) {
            throw new MissingParameterException("binding_id must not be empty");
        }

        if(request == null) {
            int i = 0;
        }

        Set<ConstraintViolation<ServiceBindingPutRequest>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(request);
        if(!validate.isEmpty()) {
            throw new RequestValidationException(validate);
        }

        return serviceBindingService.createServiceBinding(binding_id, request);
    }

    @DeleteMapping(value = "/v2/service_instances/{instance_id}/service_bindings/{binding_id}")
    public ResponseEntity deleteServiceBinding(@PathVariable String instance_id,
                                               @PathVariable String binding_id,
                                               @RequestParam String service_id,
                                               @RequestParam String plan_id,
                                               @RequestParam(required = false) boolean accepts_incomplete) throws ServiceBindingGoneException, MissingParameterException {

        if(instance_id == null || instance_id.isEmpty()) {
            throw new MissingParameterException("instance_id must not be empty");
        }

        if(binding_id == null || binding_id.isEmpty()) {
            throw new MissingParameterException("binding_id must not be empty");
        }

        if(service_id == null || service_id.isEmpty()) {
            throw new MissingParameterException("service_id must not be empty");
        }

        if(plan_id == null || plan_id.isEmpty()) {
            throw new MissingParameterException("plan_id must not be empty");
        }

        serviceBindingService.deleteServiceBinding(binding_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

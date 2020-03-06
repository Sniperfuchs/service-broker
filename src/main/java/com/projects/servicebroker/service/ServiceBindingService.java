package com.projects.servicebroker.service;

import com.projects.servicebroker.controller.request.ServiceBindingPutRequest;
import com.projects.servicebroker.controller.response.ServiceBindingPutResponse;
import com.projects.servicebroker.controller.response.ServiceInstancePutResponse;
import com.projects.servicebroker.exception.ServiceBindingConflictException;
import com.projects.servicebroker.exception.ServiceBindingGoneException;
import com.projects.servicebroker.exception.ServiceInstanceConflictException;
import com.projects.servicebroker.exception.ServiceInstanceGoneException;
import com.projects.servicebroker.model.Credentials;
import com.projects.servicebroker.model.ServiceBinding;
import com.projects.servicebroker.model.ServiceInstance;
import com.projects.servicebroker.repository.ServiceBindingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceBindingService {
    private final ServiceBindingRepository serviceBindingRepository;

    public ServiceBindingService(ServiceBindingRepository serviceBindingRepository) {
        this.serviceBindingRepository = serviceBindingRepository;
    }

    public ResponseEntity createServiceBinding(String binding_id, ServiceBindingPutRequest request) throws ServiceBindingConflictException {
        if(bindingExistsWithSameParameters(binding_id, request.getService_id(), request.getPlan_id())) {
            ServiceBindingPutResponse response = new ServiceBindingPutResponse();
            //TODO give proper response

            response.setCredentials(new Credentials("username", "password"));
            return new ResponseEntity(response, HttpStatus.OK);
        }
        if(serviceBindingRepository.existsById(binding_id)) {
            throw new ServiceBindingConflictException("Service instance with same id but different attributes already exists");
        }

        //TODO 422 and 202

        ServiceBinding serviceBinding = new ServiceBinding();
        serviceBinding.setId(binding_id);
        serviceBinding.setPlan_id(request.getPlan_id());
        serviceBinding.setService_id(request.getService_id());
        serviceBindingRepository.save(serviceBinding);
        ServiceBindingPutResponse response = new ServiceBindingPutResponse(); //TODO fill
        response.setCredentials(new Credentials("username", "password"));
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    public void deleteServiceBinding(String binding_id) throws ServiceBindingGoneException {
        Optional<ServiceBinding> serviceBinding = serviceBindingRepository.findById(binding_id);
        if(!serviceBinding.isPresent()) {
            throw new ServiceBindingGoneException("Service binding with id " + binding_id + " gone");
        }
        //TODO add 422 and 202. Also might add service id and check if it's not fitting the given binding
        serviceBindingRepository.delete(serviceBinding.get());
    }

    private boolean bindingExistsWithSameParameters(String binding_id, String service_id, String plan_id) {//TODO add more parameters
        Optional<ServiceBinding> serviceBinding = serviceBindingRepository.findById(binding_id);
        return serviceBinding.isPresent() && serviceBinding.get().getService_id().equals(service_id) && serviceBinding.get().getPlan_id().equals(plan_id);
    }
}

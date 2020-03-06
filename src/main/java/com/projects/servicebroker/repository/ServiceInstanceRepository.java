package com.projects.servicebroker.repository;

import com.projects.servicebroker.model.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {
}

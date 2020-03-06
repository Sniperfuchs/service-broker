package com.projects.servicebroker.repository;

import com.projects.servicebroker.model.ServiceBinding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceBindingRepository extends JpaRepository<ServiceBinding, String> {
}

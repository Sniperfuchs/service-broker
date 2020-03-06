package com.projects.servicebroker.repository;

import com.projects.servicebroker.model.ServiceOffering;
import com.projects.servicebroker.model.ServicePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, String> {
    @Query("select p from ServiceOffering s left join s.plans p where p.id like ?1")
    Optional<ServicePlan> findPlanById(String plan_id);
}

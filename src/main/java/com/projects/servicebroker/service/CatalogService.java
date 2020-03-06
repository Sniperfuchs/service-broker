package com.projects.servicebroker.service;

import com.projects.servicebroker.model.Catalog;
import com.projects.servicebroker.model.MaintenanceInfo;
import com.projects.servicebroker.model.ServiceOffering;
import com.projects.servicebroker.model.ServicePlan;
import com.projects.servicebroker.repository.ServiceOfferingRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CatalogService {
    private final ServiceOfferingRepository serviceOfferingRepository;

    public CatalogService(ServiceOfferingRepository serviceOfferingRepository) {
        this.serviceOfferingRepository = serviceOfferingRepository;
    }

    public Catalog getCatalog() {
        return new Catalog(serviceOfferingRepository.findAll());
    }

    public boolean containsServiceAndPlan(String service_id, String plan_id) {
        return serviceOfferingRepository.existsById(service_id) && serviceOfferingRepository.findPlanById(plan_id).isPresent(); //TODO check if method works
    }

    public boolean maintenanceInfoMatchesExisting(String plan_id, MaintenanceInfo maintenance_info) {
        Optional<ServicePlan> servicePlan = serviceOfferingRepository.findPlanById(plan_id);
        return servicePlan.isPresent() && servicePlan.get().getMaintenance_info().getVersion().equals(maintenance_info.getVersion());
    }

    @PostConstruct
    public void createDummyDefinitions(){
        ServiceOffering s = new ServiceOffering();
        s.setName("test-a");
        s.setDescription("A test service definition");
        s.setBindable(true);
        s.setPlans(Arrays.asList(
                new ServicePlan(RandomString.make(),"small", "The smallest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")),
                new ServicePlan(RandomString.make(),"medium", "The mediumest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")),
                new ServicePlan(RandomString.make(),"big", "The biggest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")))
        );
        serviceOfferingRepository.save(s);

        ServiceOffering s2 = new ServiceOffering();
        s2.setName("test-b");
        s2.setDescription("A test service definition");
        s2.setBindable(true);
        s2.setPlans(Arrays.asList(
                new ServicePlan(RandomString.make(),"small", "The smallest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")),
                new ServicePlan(RandomString.make(),"medium", "The mediumest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")),
                new ServicePlan(RandomString.make(),"big", "The biggest plan", new MaintenanceInfo("2.1.1+abcdef", "TestMaintenanceInfo")))
        );
        serviceOfferingRepository.save(s2);
    }
}


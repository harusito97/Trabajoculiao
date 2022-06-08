package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Entity.ServiceVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface ServiceVisitRepository extends JpaRepository<ServiceVisit, Long> {
    Long countServiceVisitsByServiceAndVisitTimestampBetween(Service service, ZonedDateTime startDate, ZonedDateTime endDate);
}

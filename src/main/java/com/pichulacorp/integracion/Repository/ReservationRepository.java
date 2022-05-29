package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Reservation;
import com.pichulacorp.integracion.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    List<Reservation> findAllReservationByServiceAndDateBetween(Service service, ZonedDateTime startdate, ZonedDateTime enddate);


}

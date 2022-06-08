package com.pichulacorp.integracion.Service;

import com.pichulacorp.integracion.Entity.Reservation;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    public List<Reservation> getMyReservations(Service service, ZonedDateTime startdate, ZonedDateTime enddate){
        return repository.findAllReservationByServiceAndDateBetween(service, startdate, enddate);
    };
}

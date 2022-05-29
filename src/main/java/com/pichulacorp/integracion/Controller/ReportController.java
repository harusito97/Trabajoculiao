package com.pichulacorp.integracion.Controller;

import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Plan;
import com.pichulacorp.integracion.Entity.Reservation;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Reporting.SimpleReport;
import com.pichulacorp.integracion.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    public ReservationService service;

    @GetMapping("/ReportPreview")
    public String reportPreview(Model model, @AuthenticationPrincipal CustomerDetails customer) {
        model.addAttribute("customer", customer.getCustomer());
        model.addAttribute("activePage", "ReportPreview");
        return "ReportPreview";
    }

    @GetMapping("/SimpleReport")
    public String simpleReport(Model model, @AuthenticationPrincipal CustomerDetails customer) {
        SimpleReport simpleReport = new SimpleReport();
        ZonedDateTime end = ZonedDateTime.now();
        ZonedDateTime start = end.minus(1, ChronoUnit.MONTHS);
        List<SimpleReport.DetalleServicio> detalleServicios = new ArrayList<>();
        int totalreservas = 0;
        float totaltotalplata = 0;
        for (Service servicio : customer.getCustomer().getServices()) {
            List<Reservation> myReservations = service.getMyReservations(servicio, start, end);
            SimpleReport.DetalleServicio detalle = new SimpleReport.DetalleServicio();
            detalle.setName(servicio.getName());
            detalle.setReservas(myReservations.size());
            Float totalplata = myReservations.stream()
                    .map(reservation -> {
                        float price = reservation.getPlan().getPrice();
                        long days = ChronoUnit.DAYS.between(reservation.getStartdate(), reservation.getEnddate());
                        return price * days;
                    }).reduce(0f, Float::sum);
            detalle.setPlata(totalplata);
            detalleServicios.add(detalle);
            totalreservas += myReservations.size();
            totaltotalplata += totalplata;
        }
        simpleReport.setServiceReports(detalleServicios);
        simpleReport.setPlatatotal(totaltotalplata);
        simpleReport.setReservastotal(totalreservas);
        model.addAttribute("customer", customer.getCustomer());
        model.addAttribute("activePage", "ReportPreview");
        model.addAttribute("reportData", simpleReport);
        return "ReportPreview";
    }


}

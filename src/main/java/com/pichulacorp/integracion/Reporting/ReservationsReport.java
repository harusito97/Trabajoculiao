package com.pichulacorp.integracion.Reporting;


import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsReport {

    private List<ServiceDetail> serviceReports;
    private String startDate;
    private String endDate;
    private int reservastotal;
    private Long platatotal;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceDetail {
        private String name;
        private int reservas;
        private Long plata;
    }

}

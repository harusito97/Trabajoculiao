package com.pichulacorp.integracion.Reporting;


import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsReport {

    private List<ItemDetail> serviceReports;
    private String startDate;
    private String endDate;
    private int reservastotal;
    private Long platatotal;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetail {
        private String name;
        private int reservas;
        private Long plata;
    }

}

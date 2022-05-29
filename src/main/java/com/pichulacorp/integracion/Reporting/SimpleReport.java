package com.pichulacorp.integracion.Reporting;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleReport {

    private List<DetalleServicio> serviceReports;
    private int reservastotal;
    private float platatotal;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DetalleServicio {
        private String name;
        private int reservas;
        private float plata;
    }

}

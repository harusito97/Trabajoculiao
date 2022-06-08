package com.pichulacorp.integracion.Reporting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VisitsReport {
    private String startDate;
    private String endDate;

    private List<ServiceVisitsDetail> serviceDetail;
    private Long totalVisits;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ServiceVisitsDetail {
        private String name;
        private Long visits;
    }
}

package com.pichulacorp.integracion.Reporting;

import com.pichulacorp.integracion.Entity.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceReport {

    private List<Service> allServices;
    private List<PlanDetail> planDetailList;
    private ServiceDetail serviceDetail;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ServiceDetail {
        private String name;
        private Long thisWeekCount;
        private Long thisMonthCount;
        private Long lastWeekCount;
        private Long lastMonthCount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PlanDetail {
        private String name;
        private Long price;
        private Long thisWeekCount;
        private Long thisMonthCount;
        private Long thisWeekSum;
        private Long lastWeekSum;
    }
}

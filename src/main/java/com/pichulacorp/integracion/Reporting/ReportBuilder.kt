package com.pichulacorp.integracion.Reporting

import com.pichulacorp.integracion.Controller.ReportController
import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Entity.Service
import com.pichulacorp.integracion.Repository.PlanRepository
import com.pichulacorp.integracion.Repository.ServiceVisitRepository
import com.pichulacorp.integracion.Service.ReservationService
import com.pichulacorp.integracion.Service.ServiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Component
class ReportBuilder {

    @Autowired
    lateinit var serviceService: ServiceService

    @Autowired
    lateinit var reservationService: ReservationService

    @Autowired
    lateinit var planRepository: PlanRepository

    @Autowired
    lateinit var serviceVisitRepository: ServiceVisitRepository

    private val simpleHumanReadableFormat: DateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME

    fun buildServiceReport(servicio: Service, customer: CustomerDetails): ServiceReport {
        val today = ZonedDateTime.now()
        val dates = ReportController.ReportIntervals.fromDate(today)

        val servicioObj = serviceService.getServiceById(servicio.id)

        val lastMonth = reservationService.getMyReservations(servicioObj, dates.lastMonthStart, dates.lastMonthEnd)
        val lastWeek = reservationService.getMyReservations(servicioObj, dates.lastWeekStart, dates.lastWeekEnd)
        val thisMonth = reservationService.getMyReservations(servicioObj, dates.thisMonthStart, dates.thisMonthEnd)
        val thisWeek = reservationService.getMyReservations(servicioObj, dates.thisWeekStart, dates.thisWeekEnd)

        val planDetails = servicioObj.plan.map { plan ->
            val planObj = planRepository.findPlanById(plan.id)
            ServiceReport.PlanDetail(
                planObj.name,
                planObj.price,
                thisWeek.count { it.plan.id == plan.id }.toLong(),
                thisMonth.count { it.plan.id == plan.id }.toLong(),
                planObj.price * thisWeek.filter { it.plan.id == plan.id }.sumOf { ChronoUnit.DAYS.between(it.startdate, it.enddate) },
                planObj.price * lastWeek.filter { it.plan.id == plan.id }.sumOf { ChronoUnit.DAYS.between(it.startdate, it.enddate) },
            )
        }

        val serviceDetail = ServiceReport.ServiceDetail(
            servicioObj.name,
            thisWeek.count().toLong(),
            thisMonth.count().toLong(),
            lastWeek.count().toLong(),
            lastMonth.count().toLong(),
        )

        val allServices = serviceService.getAllMyServices(customer.customer)

        return ServiceReport(
            allServices,
            planDetails,
            serviceDetail,
        )
    }

    fun visitsReport(customer: CustomerDetails): VisitsReport {
        val end = ZonedDateTime.now()
        val start = end.minus(1, ChronoUnit.MONTHS)

        val serviceClickDetails = serviceService.getServiceByOwnerRut(customer.customer.rut)?.map { servicio ->
            val visitCount =
                serviceVisitRepository.countServiceVisitsByServiceAndVisitTimestampBetween(servicio, start, end)
            VisitsReport.ItemDetail(
                servicio.name,
                visitCount
            )
        } ?: listOf()

        return VisitsReport(
            start.format(simpleHumanReadableFormat),
            end.format(simpleHumanReadableFormat),
            serviceClickDetails,
            serviceClickDetails.sumOf { it.visits }
        )
    }

    fun reservationsReport(customer: CustomerDetails): ReservationsReport {
        val end = ZonedDateTime.now()
        val start = end.minus(1, ChronoUnit.MONTHS)

        val itemDetailList = serviceService.getServiceByOwnerRut(customer.customer.rut)?.map { servicio ->
            val myReservations = reservationService.getMyReservations(servicio, start, end) ?: listOf()
            ReservationsReport.ItemDetail(
                servicio.name,
                myReservations.size,
                myReservations.sumOf {
                    it.plan.price * ChronoUnit.DAYS.between(it.startdate, it.enddate)
                }
            )
        } ?: listOf()

        return ReservationsReport(
            itemDetailList,
            start.format(simpleHumanReadableFormat),
            end.format(simpleHumanReadableFormat),
            itemDetailList.sumOf { it.reservas },
            itemDetailList.sumOf { it.plata },
        )
    }
}
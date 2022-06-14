package com.pichulacorp.integracion.Controller

import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Entity.Reservation
import com.pichulacorp.integracion.Entity.Service
import com.pichulacorp.integracion.Reporting.VisitsReport
import com.pichulacorp.integracion.Reporting.ReservationsReport
import com.pichulacorp.integracion.Reporting.ReservationsReport.ItemDetail
import com.pichulacorp.integracion.Reporting.ServiceReport
import com.pichulacorp.integracion.Repository.PlanRepository
import com.pichulacorp.integracion.Repository.ServiceVisitRepository
import com.pichulacorp.integracion.Service.ReservationService
import com.pichulacorp.integracion.Service.ServiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

@Controller
class ReportController {
    @Autowired
    lateinit var reservationService: ReservationService

    @Autowired
    lateinit var serviceVisitRepository: ServiceVisitRepository

    @Autowired
    lateinit var planRepository: PlanRepository

    @Autowired
    lateinit var serviceService: ServiceService

    private val simpleHumanReadableFormat: DateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME

    @GetMapping("/ReportPreview")
    fun reportPreview(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {
        model.addAttribute("customer", customer.customer)
        model.addAttribute("activePage", "ReportPreview")
        return "ReservationReport"
    }

    @GetMapping("/ReservationReport")
    fun reservationReport(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {

        val end = ZonedDateTime.now()
        val start = end.minus(1, ChronoUnit.MONTHS)

        val itemDetailList = serviceService.getServiceByOwnerRut(customer.customer.rut)?.map { servicio ->
            val myReservations = reservationService.getMyReservations(servicio, start, end) ?: listOf()
            ItemDetail(
                servicio.name,
                myReservations.size,
                myReservations.sumOf {
                    it.plan.price * ChronoUnit.DAYS.between(it.startdate, it.enddate)
                }
            )
        } ?: listOf()

        val reservationsReport = ReservationsReport(
            itemDetailList,
            start.format(simpleHumanReadableFormat),
            end.format(simpleHumanReadableFormat),
            itemDetailList.sumOf { it.reservas },
            itemDetailList.sumOf { it.plata },
        )

        model.apply {
            addAttribute("customer", customer.customer)
            addAttribute("activePage", "ReportPreview")
            addAttribute("reportData", reservationsReport)
        }
        return "ReservationReport"
    }

    @GetMapping("/VisitsReport")
    fun visitsReport(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {

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

        val reportData = VisitsReport(
                start.format(simpleHumanReadableFormat),
                end.format(simpleHumanReadableFormat),
                serviceClickDetails,
                serviceClickDetails.sumOf { it.visits }
        )

        model.apply {
            addAttribute("customer", customer.customer)
            addAttribute("activePage", "VisitsReport")
            addAttribute("reportData", reportData)
        }

        return "VisitsReport"
    }

    @GetMapping("/ServiceReport")
    fun serviceReportNoArg(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {
        val allMyServices = serviceService.getAllMyServices(customer.customer)
        if (allMyServices.isNotEmpty()) {
            return "redirect:/ServiceReport/${allMyServices.first().id}"
        } else {
            return "redirect:/CustomerReports"
        }
    }

    @GetMapping("/ServiceReport/{id}")
    fun serviceReport(model: Model, servicio: Service, @AuthenticationPrincipal customer: CustomerDetails): String{
        val today = ZonedDateTime.now()
        val dates = ReportIntervals.fromDate(today)

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

        val reportData = ServiceReport(
            allServices,
            planDetails,
            serviceDetail,
        )

        model.apply {
            addAttribute("customer", customer.customer)
            addAttribute("activePage", "DetailedServiceReport")
            addAttribute("reportData", reportData)
        }

        return "ServiceReport";
    }

    data class ReportIntervals(
        val lastWeekStart: ZonedDateTime,
        val lastWeekEnd: ZonedDateTime,
        val lastMonthStart: ZonedDateTime,
        val lastMonthEnd: ZonedDateTime,
        val thisWeekStart: ZonedDateTime,
        val thisWeekEnd: ZonedDateTime,
        val thisMonthStart: ZonedDateTime,
        val thisMonthEnd: ZonedDateTime,
    ) {
        companion object {
            fun fromDate(now: ZonedDateTime): ReportIntervals {
                val dateOnly = now.truncatedTo(ChronoUnit.DAYS)

                val thisWeekStart = dateOnly.with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.value.toLong())
                val thisWeekEnd = thisWeekStart.plusWeeks(1)

                val thisMonthStart = dateOnly.withDayOfMonth(1)
                val thisMonthEnd = thisMonthStart.plusMonths(1)

                val lastWeekStart = thisWeekStart.minusWeeks(1)
                val lastWeekEnd = lastWeekStart.plusWeeks(1)

                val lastMonthStart = dateOnly.minusMonths(1).withDayOfMonth(1)
                val lastMonthEnd = lastMonthStart.plusMonths(1)

                return ReportIntervals(
                    lastWeekStart, lastWeekEnd,
                    lastMonthStart, lastMonthEnd,
                    thisWeekStart, thisWeekEnd,
                    thisMonthStart, thisMonthEnd
                )
            }
        }
    }

}
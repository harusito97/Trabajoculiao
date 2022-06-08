package com.pichulacorp.integracion.Controller

import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Reporting.VisitsReport
import com.pichulacorp.integracion.Reporting.ReservationsReport
import com.pichulacorp.integracion.Reporting.ReservationsReport.ServiceDetail
import com.pichulacorp.integracion.Repository.ServiceVisitRepository
import com.pichulacorp.integracion.Service.ReservationService
import com.pichulacorp.integracion.Service.ServiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Controller
class ReportController {
    @Autowired
    var reservationService: ReservationService? = null

    @Autowired
    var serviceVisitRepository: ServiceVisitRepository? = null

    @Autowired
    var serviceService: ServiceService? = null

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

        val serviceDetailList = serviceService?.getServiceByOwnerRut(customer.customer.rut)?.map { servicio ->
            val myReservations = reservationService?.getMyReservations(servicio, start, end) ?: listOf()
            ServiceDetail(
                    servicio.name,
                    myReservations.size,
                    myReservations.map {
                        it.plan.price * ChronoUnit.DAYS.between(it.startdate, it.enddate)
                    }.sum()
            )
        }?: listOf()

        val reservationsReport = ReservationsReport(
            serviceDetailList,
            start.format(simpleHumanReadableFormat),
            end.format(simpleHumanReadableFormat),
            serviceDetailList.sumOf { it.reservas },
            serviceDetailList.map { it.plata }.sum(),
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

        val serviceClickDetails = serviceService?.getServiceByOwnerRut(customer.customer.rut)?.map { servicio ->
            val visitCount =
                serviceVisitRepository?.countServiceVisitsByServiceAndVisitTimestampBetween(servicio, start, end)
            VisitsReport.ServiceVisitsDetail(
                servicio.name,
                visitCount
            )
        }?: listOf()

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
}
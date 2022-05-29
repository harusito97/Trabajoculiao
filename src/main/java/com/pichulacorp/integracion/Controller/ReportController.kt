package com.pichulacorp.integracion.Controller

import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Reporting.SimpleReport
import com.pichulacorp.integracion.Reporting.SimpleReport.DetalleServicio
import com.pichulacorp.integracion.Service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Controller
class ReportController {
    @Autowired
    var service: ReservationService? = null

    @GetMapping("/ReportPreview")
    fun reportPreview(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {
        model.addAttribute("customer", customer.customer)
        model.addAttribute("activePage", "ReportPreview")
        return "ReportPreview"
    }

    @GetMapping("/SimpleReport")
    fun simpleReport(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {

        val end = ZonedDateTime.now()
        val start = end.minus(1, ChronoUnit.MONTHS)

        val detalleServicioList = customer.customer.services.map { servicio ->
            val myReservations = service?.getMyReservations(servicio, start, end) ?: listOf()
            DetalleServicio(
                servicio.name,
                myReservations.size,
                myReservations.map {
                    it.plan.price * ChronoUnit.DAYS.between(it.startdate, it.enddate)
                }.sum()
            )
        }

        val simpleReport = SimpleReport(
            detalleServicioList,
            detalleServicioList.sumOf { it.reservas },
            detalleServicioList.map { it.plata }.sum()
        )

        model.apply {
            addAttribute("customer", customer.customer)
            addAttribute("activePage", "ReportPreview")
            addAttribute("reportData", simpleReport)
        }
        return "ReportPreview"
    }
}
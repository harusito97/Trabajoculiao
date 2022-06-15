package com.pichulacorp.integracion.Controller

import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Entity.Service
import com.pichulacorp.integracion.Reporting.ReportBuilder
import com.pichulacorp.integracion.Reporting.ReservationsReport
import com.pichulacorp.integracion.Reporting.ReservationsReport.ItemDetail
import com.pichulacorp.integracion.Reporting.VisitsReport
import com.pichulacorp.integracion.Repository.PlanRepository
import com.pichulacorp.integracion.Repository.ServiceVisitRepository
import com.pichulacorp.integracion.Service.ReservationService
import com.pichulacorp.integracion.Service.ServiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.WebContext
import org.thymeleaf.context.WebEngineContext
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.OutputStreamWriter
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


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

    @Autowired
    lateinit var reportBuilder: ReportBuilder

    @Autowired
    lateinit var templateEngine: TemplateEngine

    @Autowired
    lateinit var httpServletContext: ServletContext

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

    @GetMapping("/ServiceReportPdf/{id}")
    fun serviceReportPdf(model: Model, servicio: Service, @AuthenticationPrincipal customer: CustomerDetails, servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {

        val reportData = reportBuilder.buildServiceReport(servicio, customer)

        val context = WebContext(servletRequest, servletResponse, httpServletContext)

        context.apply {
            setVariable("customer", customer.customer)
            setVariable("activePage", "DetailedServiceReport")
            setVariable("reportData", reportData)
        }

        val id = servicio.id
        val date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        servletResponse.contentType = "application/pdf"
        servletResponse.setHeader("Content-Disposition", "attachment; filename=service_report_${id}_${date}.pdf")

        val processedHtml = templateEngine.process("ServiceReport", context)

        ITextRenderer().apply {
            setDocumentFromString(processedHtml)
            layout()
            createPDF(servletResponse.outputStream)
        }
    }

    @GetMapping("/ServiceReport/{id}")
    fun serviceReport(model: Model, servicio: Service, @AuthenticationPrincipal customer: CustomerDetails): String{
        val reportData = reportBuilder.buildServiceReport(servicio, customer)

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
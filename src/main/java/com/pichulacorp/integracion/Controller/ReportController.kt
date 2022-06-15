package com.pichulacorp.integracion.Controller

import com.pichulacorp.integracion.CustomerDetails
import com.pichulacorp.integracion.Entity.Service
import com.pichulacorp.integracion.Reporting.ReportBuilder
import com.pichulacorp.integracion.Service.ServiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.WebContext
import org.xhtmlrenderer.pdf.ITextRenderer
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class ReportController {

    @Autowired
    lateinit var serviceService: ServiceService

    @Autowired
    lateinit var reportBuilder: ReportBuilder

    @Autowired
    lateinit var templateEngine: TemplateEngine

    @Autowired
    lateinit var httpServletContext: ServletContext

    @GetMapping("/ReportPreview")
    fun reportPreview(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {
        model.addAttribute("customer", customer.customer)
        model.addAttribute("activePage", "ReportPreview")
        return "ReservationReport"
    }

    @GetMapping("/ReservationReport")
    fun reservationReport(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {
        val reservationsReport = reportBuilder.reservationsReport(customer)

        model.apply {
            addAttribute("customer", customer.customer)
            addAttribute("activePage", "ReportPreview")
            addAttribute("reportData", reservationsReport)
        }
        return "ReservationReport"
    }

    @GetMapping("/VisitsReport")
    fun visitsReport(model: Model, @AuthenticationPrincipal customer: CustomerDetails): String {

        val reportData = reportBuilder.visitsReport(customer)

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
        val id = servicio.id
        val date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        val context = WebContext(servletRequest, servletResponse, httpServletContext)

        context.apply {
            setVariable("customer", customer.customer)
            setVariable("activePage", "DetailedServiceReport")
            setVariable("reportData", reportData)
        }

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
}
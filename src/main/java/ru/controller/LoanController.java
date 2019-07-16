package ru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dto.ScheduleItem;
import ru.request.GetScheduleRequest;
import ru.response.GetScheduleResponse;
import ru.service.PaymentScheduleService;
import ru.response.builder.GetScheduleResponseBuilder;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoanController {

    private final PaymentScheduleService paymentScheduleService;

    public LoanController(PaymentScheduleService paymentScheduleService) {
        this.paymentScheduleService = paymentScheduleService;
    }

    @GetMapping("/")
    public String getSchedule(Model model) {
        GetScheduleRequest request = new GetScheduleRequest();
        model.addAttribute("loanForm", request);
        return "index";
    }

    @PostMapping("/calculate")
    public String getSchedule(Model model, @ModelAttribute("loanForm") @Valid GetScheduleRequest request) {
        List<ScheduleItem> schedule = paymentScheduleService.getSchedule(request);
        GetScheduleResponse response = GetScheduleResponseBuilder.build(schedule);
        model.addAttribute("response", response);

        return "schedule";
    }
}

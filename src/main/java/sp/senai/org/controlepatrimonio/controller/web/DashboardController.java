package sp.senai.org.controlepatrimonio.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sp.senai.org.controlepatrimonio.dto.response.DashboardResponse;
import sp.senai.org.controlepatrimonio.service.DashboardService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        DashboardResponse dashboard =
                dashboardService.obterDashboard();

        model.addAttribute("dashboard", dashboard);

        return "dashboard/index";
    }
}

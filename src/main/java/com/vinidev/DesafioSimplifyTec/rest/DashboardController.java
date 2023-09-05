package com.vinidev.DesafioSimplifyTec.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping(value = "/dashboard")
    public String dashboardPage(){
        return "dashboard";
    }
}

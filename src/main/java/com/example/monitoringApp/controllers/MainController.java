package com.example.monitoringApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getStartPage(Model model) {
        model.addAttribute("humidityValue", "70");
        model.addAttribute("temperatureValue", "30");
        return "StartPage";
    }
}

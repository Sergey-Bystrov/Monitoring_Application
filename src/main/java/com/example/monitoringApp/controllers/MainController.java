package com.example.monitoringApp.controllers;

import com.example.monitoringApp.data.dto.EspBusinessDataDTO;
import com.example.monitoringApp.data.mappers.EspDataMapper;
import com.example.monitoringApp.hardware.service.EspDataService;
import com.example.monitoringApp.service.ApplicationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainController {
    private final EspDataService espDataService;
    private EspDataMapper espDataMapper;
    private ApplicationDataService applicationDataService;

    @Autowired
    public MainController(EspDataService espDataService, EspDataMapper espDataMapper, ApplicationDataService applicationDataService) {
        this.espDataService = espDataService;
        this.espDataMapper = espDataMapper;
        this.applicationDataService = applicationDataService;
    }

    @GetMapping("/")
    public String getStartPage(Model model) {
        EspBusinessDataDTO espBusinessDataDTO = applicationDataService.fromDbGetLatestMesurement();
        model.addAttribute("humidityValue", espBusinessDataDTO.getHumidity());
        model.addAttribute("temperatureValue", espBusinessDataDTO.getTemperature());
        model.addAttribute("weightValue", espBusinessDataDTO.getWeight());
        model.addAttribute("timeValue", espBusinessDataDTO.getTime());
        return "StartPage";
    }

    @GetMapping("/MeasurementHistoryPage")
    public String getMeasurementHistoryPage(Model model) {
        return "MeasurementHistoryPage";
    }

    @GetMapping("/measurement/history")
    @ResponseBody
    public List<EspBusinessDataDTO> getMeasurementHistoryPage(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
                                                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate) {
        return applicationDataService.fromDbGetMeasurementsInPeriod(startDate, endDate);
    }

    @GetMapping("/update_info")
    @ResponseBody
    public Map<String, Object> updateInfo() {
        EspBusinessDataDTO espBusinessDataDTO = applicationDataService.fromDbGetLatestMesurement();
        Map<String, Object> response = new HashMap<>();
        response.put("temperatureValue", espBusinessDataDTO.getTemperature());
        response.put("humidityValue", espBusinessDataDTO.getHumidity());
        response.put("weightValue", espBusinessDataDTO.getWeight());
        response.put("timeValue", espBusinessDataDTO.getTime());
        return response;
    }
}

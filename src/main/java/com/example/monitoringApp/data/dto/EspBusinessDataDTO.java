package com.example.monitoringApp.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EspBusinessDataDTO {
    private String temperature;
    private String humidity;
    private String weight;
    private String time;
}

package com.example.monitoringApp.hardware.settings;

import org.springframework.beans.factory.annotation.Value;

public class EspSettings {
    @Value("${esp.connect.port}")
    private String espConnectPort;
}

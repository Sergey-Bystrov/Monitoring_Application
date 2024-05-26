package com.example.monitoringApp.hardware.schedduler;

import com.example.monitoringApp.hardware.service.EspDataService;
import com.example.monitoringApp.service.ApplicationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class EspReceiveScheduler {

    private ScheduledExecutorService scheduledExecutorService;
    private EspDataService espDataService;
    private ApplicationDataService applicationDataService;

    @Autowired
    public EspReceiveScheduler(ScheduledExecutorService scheduledExecutorService, ApplicationDataService applicationDataService, EspDataService espDataService) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.applicationDataService = applicationDataService;
        this.espDataService = espDataService;
    }

    public void start() {
        Runnable task = () -> applicationDataService.toDbSaveNewDateFromBoard(espDataService.readDataFromBoard());
        scheduledExecutorService.scheduleAtFixedRate(task, 15, 15, TimeUnit.SECONDS);
    }

}

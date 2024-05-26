package com.example.monitoringApp.hardware.schedduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SchedulerStartupRunner implements CommandLineRunner {
    @Autowired
    private EspReceiveScheduler espReceiveScheduler;
    @Override
    public void run(String... args) throws Exception {
        espReceiveScheduler.start();
    }
}

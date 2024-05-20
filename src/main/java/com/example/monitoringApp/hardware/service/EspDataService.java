package com.example.monitoringApp.hardware.service;

import com.example.monitoringApp.data.EspReceiveMessageDTO;
import com.example.monitoringApp.hardware.receive.EspDataReceiver;
import com.example.monitoringApp.hardware.send.EspDataSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Slf4j
@Component
public class EspDataService {

    @Autowired
    private EspDataReceiver espDataReceiver;
    @Autowired
    private EspDataSender espDataSender;

    public void saveDataFromEsp() {
        CompletableFuture<EspReceiveMessageDTO> future = espDataReceiver.receiveData();
        future.thenAccept(dto -> {
            if (dto != null) {
                //для записи в базу данных
                log.info("Received DTO: " + dto);
            } else {
                log.error("Failed to receive DTO");
            }
        });
    }

}

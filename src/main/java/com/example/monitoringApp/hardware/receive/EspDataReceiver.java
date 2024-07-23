package com.example.monitoringApp.hardware.receive;

import arduino.AlertBox;
import arduino.Arduino;
import com.example.monitoringApp.data.dto.EspReceiveMessageDTO;
import com.example.monitoringApp.logMessages.Errors;
import com.example.monitoringApp.logMessages.Info;
import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
public class EspDataReceiver {

    @Value("${esp.connect.port}")
    private String espConnectPort;
    private String messsage;
    private volatile EspReceiveMessageDTO messageDto;
    private SerialPort comPort = SerialPort.getCommPort(espConnectPort);

    @Async
    public CompletableFuture<EspReceiveMessageDTO> receiveData() {
        Arduino espBoard = new Arduino(espConnectPort, 9600);
        log.info("Try connected to board on : " + espConnectPort);
        //log.info("Result : " + espBoard.openConnection());
        boolean connected = openConnection();//espBoard.openConnection();
        log.info("Connected to board on : " + espConnectPort + " = " + connected);
        try {
            Thread.sleep(4000);
            log.info(Info.ESP_BORD_CONNECT_SUCCESSFULLY.getLogMessage() + connected);
        } catch (InterruptedException e) {
            log.error(Errors.FAILED_CONNECT_TO_ESP_BORD.getError() + espConnectPort, e);
            Thread.currentThread().interrupt();
        }

        try {
            espBoard.serialWrite('1');
            messsage = espBoard.serialRead();
            log.info("ESP primary messsage" + messsage);
            messageDto = mapMessageFromBoard(messsage);
            return CompletableFuture.completedFuture(messageDto);
        } catch (IllegalArgumentException e) {
            log.error(Errors.FAILED_TO_MAP_MESSAGE_FROM_ESP_BORD.getError(), e);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error(Errors.FAILED_TO_READ_MESSAGE_FROM_ESP_BORD.getError(), e);
            return CompletableFuture.completedFuture(null);
        }
    }

    protected EspReceiveMessageDTO mapMessageFromBoard(@NonNull String message) {
        String[] data = message.split(",");
        if (data.length != 4) {
            log.error(Errors.INVALID_MESSAGE_FORMAT_FROM_ESP_BORD.getError());
            throw new IllegalArgumentException();
        }
        return new EspReceiveMessageDTO(data[0], data[1], data[2]);
    }

    public EspReceiveMessageDTO getMessageDto() {
        return messageDto;
    }

    public boolean openConnection() {
        log.info("Try to open connection");
        if (this.comPort.openPort()) {
            try {
                log.info("Port opened, sleeping for 100ms...");
                Thread.sleep(100L);
            } catch (Exception var2) {
                log.info("Exception during sleep: " + var2);
            }

            return true;
        } else {
            log.info("Failed to open port.");
            AlertBox alert = new AlertBox(new Dimension(400, 100), "Error Connecting", "Try Another port");
            alert.display();
            return false;
        }
    }
}

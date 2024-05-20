package com.example.monitoringApp.hardware.receive;

import arduino.Arduino;
import com.example.monitoringApp.logMessages.Errors;
import com.example.monitoringApp.data.EspReceiveMessageDTO;
import com.example.monitoringApp.logMessages.Info;
import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
public class EspDataReceiver {

    @Value("${esp.connect.port}")
    private String espConnectPort;
    private String messsage;
    private volatile EspReceiveMessageDTO messageDto;

    @Async
    public CompletableFuture<EspReceiveMessageDTO> receiveData() {
        Arduino espBoard = new Arduino(espConnectPort, 9600);

        System.out.println("Hello world!");

        boolean connected = espBoard.openConnection();
        try {
            Thread.sleep(4000);
            log.info(Info.ESP_BORD_CONNECT_SUCCESSFULLY.getInfo() + connected);
        } catch (InterruptedException e) {
            log.error(Errors.FAILED_CONNECT_TO_ESP_BORD.getError() + espConnectPort, e);
            Thread.currentThread().interrupt();
        }

        try {
            messsage = espBoard.serialRead();
            messageDto = mapMessageFromBoard(messsage);
            return CompletableFuture.completedFuture(messageDto);
        } catch (IllegalArgumentException e){
            log.error(Errors.FAILED_TO_MAP_MESSAGE_FROM_ESP_BORD.getError(), e);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e){
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
        return new EspReceiveMessageDTO(data[0], data[1], data[2], data[3]);
    }

    public EspReceiveMessageDTO getMessageDto() {
        return messageDto;
    }
}

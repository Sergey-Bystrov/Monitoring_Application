package com.example.monitoringApp.data.mappers;

import com.example.monitoringApp.data.dto.EspBusinessDataDTO;
import com.example.monitoringApp.data.dto.EspReceiveMessageDTO;
import com.example.monitoringApp.data.entity.EspData;
import com.example.monitoringApp.logMessages.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
public class EspDataMapper {
    private EspReceiveMessageDTO espReceiveMessageDTO;

    public EspReceiveMessageDTO mapMessageFromBoard(@NonNull String message) {
        String[] data = message.split("/");
        if (data.length != 3) {
            log.error(Errors.INVALID_MESSAGE_FORMAT_FROM_ESP_BORD.getError());
            throw new IllegalArgumentException();
        }
        return new EspReceiveMessageDTO(data[0], data[1], data[2]);
    }

    public EspData mapReciveMessageToEntity(EspReceiveMessageDTO espReceiveMessageDTO) {
        EspData espData = new EspData();
        espData.setHumidity(Float.valueOf(espReceiveMessageDTO.getHumidity()));
        espData.setTemperature(Float.valueOf(espReceiveMessageDTO.getTemperature()));
        espData.setWeight(Float.valueOf(espReceiveMessageDTO.getWeight()));
        ZoneId moscowZoneId = ZoneId.of("Europe/Moscow");
        ZonedDateTime moscowTime = ZonedDateTime.now(moscowZoneId);
        espData.setTime(moscowTime);
        log.info("Current Moscow time (ZonedDateTime): {}", moscowTime);
        log.info("End mapping EspData"+espData.getTime());
        return espData;
    }

    public EspBusinessDataDTO mapEspDataFromDB(@NonNull EspData espData) {
        EspBusinessDataDTO espBusinessDataDTO = new EspBusinessDataDTO();
        espBusinessDataDTO.setTemperature(String.valueOf(espData.getTemperature()));
        espBusinessDataDTO.setHumidity(String.valueOf(espData.getHumidity()));
        espBusinessDataDTO.setWeight(String.valueOf(espData.getWeight()));
        ZonedDateTime utcTime = espData.getTime().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime moscowTime = utcTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTime = moscowTime.format(formatter);
        espBusinessDataDTO.setTime(formattedTime);
        //espBusinessDataDTO.setTime(String.valueOf(espData.getTime()));
        return espBusinessDataDTO;
    }
}

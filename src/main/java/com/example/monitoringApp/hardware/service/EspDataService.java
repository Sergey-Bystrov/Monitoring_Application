package com.example.monitoringApp.hardware.service;

import com.example.monitoringApp.data.dto.EspReceiveMessageDTO;
import com.example.monitoringApp.data.mappers.EspDataMapper;
import com.example.monitoringApp.repository.EspRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EspDataService {

    private EspRepository espRepository;

    private EspDataMapper espDataMapper;

    private final RestTemplate restTemplate;

    @Autowired
    public EspDataService(RestTemplate restTemplate, EspDataMapper espDataMapper, EspRepository espRepository) {
        this.restTemplate = restTemplate;
        this.espDataMapper = espDataMapper;
        this.espRepository = espRepository;
    }

    public String readDataFromBoard() {
        String url = "http://192.168.0.104:8081/esp/read";
        log.info("Start receive from board");
        String data = restTemplate.getForObject(url, String.class);
        log.info("End receive from board, data = " + data);
        return data;
    }

    public void writeDataToBoard(String data) {
        String url = "http://192.168.0.104/arduino/write?data=" + data;
        restTemplate.getForObject(url, String.class);
    }

    public void save(EspReceiveMessageDTO espReceiveMessageDTO) {
        espRepository.save(espDataMapper.mapReceiveMessageToEntity(espReceiveMessageDTO));
    }
}

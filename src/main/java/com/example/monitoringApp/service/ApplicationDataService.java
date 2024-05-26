package com.example.monitoringApp.service;

import com.example.monitoringApp.data.dto.EspBusinessDataDTO;
import com.example.monitoringApp.data.dto.EspReceiveMessageDTO;
import com.example.monitoringApp.data.entity.EspData;
import com.example.monitoringApp.data.mappers.EspDataMapper;
import com.example.monitoringApp.hardware.service.EspDataService;
import com.example.monitoringApp.repository.EspRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ApplicationDataService {

    private EspRepository espRepository;
    private EspDataMapper espDataMapper;
    private EspDataService espDataService;

    @Autowired
    public ApplicationDataService(EspRepository espRepository, EspDataMapper espDataMapper, EspDataService espDataService) {
        this.espRepository = espRepository;
        this.espDataMapper = espDataMapper;
        this.espDataService = espDataService;
    }

    public List<EspBusinessDataDTO> fromDbGetAllMesurement() {
        List<EspData> espData = espRepository.findAll(Sort.by(Sort.Direction.DESC, "measurement_time"));

        List<EspBusinessDataDTO> espBusinessDataDTOList = new ArrayList<>();
        for (EspData espDataItem : espData) {
            espBusinessDataDTOList.add(espDataMapper.mapEspDataFromDB(espDataItem));
        }
        return espBusinessDataDTOList;
    }

    public List<EspBusinessDataDTO> fromDbGetMeasurementsInPeriod(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<EspData> measurementData = espRepository.findByTimeBetween(startDate, endDate);
        List<EspBusinessDataDTO> espBusinessDataDTOList = new ArrayList<>();
        for (EspData measurement : measurementData) {
            espBusinessDataDTOList.add(espDataMapper.mapEspDataFromDB(measurement));
        }
        return espBusinessDataDTOList;
    }

    public EspBusinessDataDTO fromDbGetLatestMesurement() {
        EspData espDataItem = espRepository.findAll(Sort.by(Sort.Direction.DESC, "Time")).get(0);
        EspBusinessDataDTO result = espDataMapper.mapEspDataFromDB(espDataItem);
            log.info("Get latest mesurement data from DB" + result.getTime());
        return result;
    }

    public void toDbSaveEspData(EspReceiveMessageDTO espReceiveMessageDTO) {
        espDataService.save(espReceiveMessageDTO);
    }

    public EspBusinessDataDTO tmpGetData() {
        EspReceiveMessageDTO espReceiveMessageDTO = espDataMapper.mapMessageFromBoard(espDataService.readDataFromBoard());
        toDbSaveEspData(espReceiveMessageDTO);
        log.info("Start read from data base");
        return fromDbGetLatestMesurement();
    }

    public void toDbSaveNewDateFromBoard(String data) {
        EspReceiveMessageDTO espReceiveMessageDTO;
        try{
            espReceiveMessageDTO = espDataMapper.mapMessageFromBoard(data);
            toDbSaveEspData(espReceiveMessageDTO);
            log.info("EspReceiveScheduler -> Save new data from Board to Date Base");
        }catch (Exception e){
            log.info("Exception wile parse data from board" + e.getMessage());
        }
    }
}

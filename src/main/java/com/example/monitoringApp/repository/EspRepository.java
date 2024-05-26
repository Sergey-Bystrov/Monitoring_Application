package com.example.monitoringApp.repository;

import com.example.monitoringApp.data.entity.EspData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface EspRepository extends JpaRepository<EspData, Long> {
    @Override
    List<EspData> findAll();

    @Query("SELECT e FROM EspData e WHERE e.time BETWEEN :startDate AND :endDate")
    List<EspData> findByTimeBetween(ZonedDateTime startDate, ZonedDateTime endDate);
}

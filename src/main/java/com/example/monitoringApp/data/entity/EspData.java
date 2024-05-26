package com.example.monitoringApp.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "EspData")
@Getter
@Setter
public class EspData {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Temperature")
    private float temperature;

    @Column(name = "Humidity")
    private float humidity;

    @Column(name = "Weight")
    private float weight;

    @Column(name = "MeasurementTime")
    private ZonedDateTime time;
}

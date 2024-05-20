package com.example.monitoringApp.data.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "EspData")
public class EspData {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Temperature")
    private float temperature;

    @Column(name = "Humidity")
    private float humidity;

    @Column(name = "Weight")
    private float weight;

    @Column(name = "MeasurementTime")
    private Date time;
}

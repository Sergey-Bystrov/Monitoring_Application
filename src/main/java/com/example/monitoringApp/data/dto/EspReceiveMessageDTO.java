package com.example.monitoringApp.data.dto;

import java.util.Objects;

public class EspReceiveMessageDTO {
    private String temperature;
    private String humidity;
    private String weight;

    public EspReceiveMessageDTO(String temperature, String humidity, String weight) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.weight = weight;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWeight() {
        return weight;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EspReceiveMessageDTO{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspReceiveMessageDTO that = (EspReceiveMessageDTO) o;
        return Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity) && Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity, weight);
    }
}

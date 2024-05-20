package com.example.monitoringApp.data;

import java.util.Objects;

public class EspReceiveMessageDTO {
    private String temperature;
    private String humidity;
    private String weight;
    private String date;

    public EspReceiveMessageDTO(String temperature, String humidity, String weight, String date) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.weight = weight;
        this.date = date;
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

    public String getDate() {
        return date;
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

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EspReceiveMessageDTO{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", weight='" + weight + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspReceiveMessageDTO that = (EspReceiveMessageDTO) o;
        return Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity) && Objects.equals(weight, that.weight) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity, weight, date);
    }
}

package com.example.monitoringApp.logMessages;

public enum Info {
    ESP_BORD_CONNECT_SUCCESSFULLY("Соединение установлено: ");
    private final String info;

    Info(String inffo) {
        this.info = inffo;
    }

    public String getInfo() {
        return info;
    }
}

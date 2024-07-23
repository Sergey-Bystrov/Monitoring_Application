package com.example.monitoringApp.logMessages;

public enum Info {
    ESP_MAPPER_CURRENT_MOSCOW_TIME("Current Moscow time (ZonedDateTime): {}"),
    ESP_MAPPER_("Current Moscow time (ZonedDateTime): {}"),
    ESP_BORD_CONNECT_SUCCESSFULLY("Соединение установлено: ");
    private final String info;

    Info(String inffo) {
        this.info = inffo;
    }

    public String getLogMessage() {
        return info;
    }
}

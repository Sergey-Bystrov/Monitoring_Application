package com.example.monitoringApp.logMessages;

public enum Errors {
    FAILED_CONNECT_TO_ESP_BORD("Не удалось подключиться к плате по порту: "),
    FAILED_TO_READ_MESSAGE_FROM_ESP_BORD("Не удалось олучить данные от платы: "),
    FAILED_TO_MAP_MESSAGE_FROM_ESP_BORD("Не удалось размапить сообщение от платы: "),
    INVALID_MESSAGE_FORMAT_FROM_ESP_BORD("Некорректный формат данных от платы");
    private final String error;

    Errors(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

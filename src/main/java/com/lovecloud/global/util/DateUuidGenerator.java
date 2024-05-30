package com.lovecloud.global.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUuidGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String generateDateUuid() {
        String date = LocalDate.now().format(DATE_FORMATTER);
        String shortUUID = UUIDUtil.generateShortUUID();
        return date + shortUUID;
    }
}

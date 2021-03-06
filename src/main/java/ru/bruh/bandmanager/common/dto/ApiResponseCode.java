package ru.bruh.bandmanager.common.dto;

import java.io.Serializable;

public enum ApiResponseCode implements Serializable {
    INSUFFICIENT_RIGHTS(1, "Недостаточно прав"),
    ACCESS_DENIED(2, "Доступ запрещен"),
    INTERNAL_SERVER_ERROR(3, "Неизвестная ошибка сервера"),

    COUNTRY_NOT_FOUND(4, "Страна на найдена"),
    BAND_NOT_FOUND(5, "Группа не найдена"),
    MEMBER_ROLE_NOT_FOUND(6, "Амплуа не найдено"),
    MEMBER_NOT_FOUND(7, "Участник не найден"),
    SONG_NOT_FOUND(8, "Песня не найдена"),
    NEW_HIT_PARADE_POSITION_EQUALS_CURRENT_POSITION(9,"Новая позиция в хитпараде равна текущей"),
    INVALID_HIT_PARADE_POSITION(10,"Некорректная позиция в хитпараде"),
    INVALID_TICKET_PRICE(11, "Некорректная цена за билет"),
    INVALID_TOUR_DATES(12, "Некорректные даты начала и конца гастролей"),
    TOUR_NOT_FOUND(13, "Гастроли не найдены"),
    SONG_DOES_NOT_BELONG_TO_BAND(14, "Песня не принадлежит группе"),
    TOUR_DOES_NOT_BELONG_TO_BAND(15, "Гастроли не принадлежат группе");

    private final int code;
    private final String description;

    ApiResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription(Object... params) {
        if (params == null || params.length == 0) {
            return description;
        } else {
            return String.format(description, params);
        }
    }
}

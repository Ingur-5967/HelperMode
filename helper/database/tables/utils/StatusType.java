package ru.solomka.helper.database.tables.utils;

import lombok.Getter;

public enum StatusType {

    USER("Игрок"),
    HELPER_JUNIOR("&9&l[СТАЖЕР]&f "),
    HELPER_MIDDLE("&a&l[ХЕЛПЕР]&f "),
    HELPER_PROFESSIONAL("&6&l[СТ.ХЕЛПЕР]&f "),
    HELPER_MAIN("&c&l[ГЛАВНЫЙ ХЕЛПЕР]&f ");

    @Getter private final String status;

    StatusType(String status) {
        this.status = status;
    }
}

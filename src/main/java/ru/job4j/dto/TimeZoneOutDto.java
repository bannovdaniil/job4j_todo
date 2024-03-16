package ru.job4j.dto;

import lombok.*;

/**
 * Для отображения списка Часового пояса, в thymeleaf с версии 3.0.15 закрыт доступ на прямую к свойствам timeZone
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TimeZoneOutDto {
    private String id;
    private String displayName;
}

package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@UtilityClass
public class DateMapper {
    public static String asString(OffsetDateTime date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" )
                .format( date ) : null;
    }

    public static OffsetDateTime asDate(String date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.parse(date),
                TimeZone.getTimeZone("UTC").toZoneId()
        );
        return OffsetDateTime.of(
                localDateTime,
                ZoneOffset.UTC
        );
    }
}

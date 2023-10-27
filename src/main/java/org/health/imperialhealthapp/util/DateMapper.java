package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

@UtilityClass
public class DateMapper {
    public static String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" )
                .format( date ) : null;
    }

    public static Date asDate(String date) {
        return Date.valueOf(LocalDate.ofInstant(
                Instant.parse(date),
                TimeZone.getTimeZone("UTC").toZoneId()
        ));
    }
}

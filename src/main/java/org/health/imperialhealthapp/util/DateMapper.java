package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;

@UtilityClass
public class DateMapper {
    public static String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
                .format( date ) : null;
    }

    public static Date asDate(String date) {
        return Date.valueOf(date);
    }
}

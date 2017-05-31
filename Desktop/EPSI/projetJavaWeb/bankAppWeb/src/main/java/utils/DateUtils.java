package utils;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

/**
 *
 * @author Sylvain
 */

public class DateUtils {

    public static Date LocalDate2Date(LocalDate local) {
	return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDate DateToLocalDate(Date date) {
	return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }	
    public static Date today() {
        return Calendar.getInstance().getTime();
    }       
}

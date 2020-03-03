package planning.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
	
	/** The date pattern that is used for conversion. */
	private static final String DATE_PATTERN = "EEEE, MMM dd";
	
	/** The time pattern that is used for conversion. */
	private static final String TIME_PATTERN = "h:mm a";
	
	/** The date formatter. */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/** The time formatter. */
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
	
	/**
	 * Returns given date as a well formatted string
	 * @param date
	 * @return
	 */
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	public static String format(LocalTime time) {
		if (time == null) {
			return null;
		}
		return TIME_FORMATTER.format(time);
	}
	
	/**
	 * Parses LocalDate from string
	 * @param dateString
	 * @return LocalDate
	 */
	public static LocalDate parseDate(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}
		catch (DateTimeParseException e) {
			return null;
		}
	}
	
	/**
	 * Parses Time from String
	 * @param timeString
	 * @return
	 */
	public static LocalTime parseTime(String timeString) {
		try {
			//return TIME_FORMATTER.parse(timeString, LocalTime::from);
			return LocalTime.parse(timeString);
		}
		catch (DateTimeParseException e) {
			System.out.println("EREORIJR");
			return null;
		}
	}
	
	/**
	 * Checks whether a string is a valid date
	 * 
	 * @param dateString
	 * @return true if the String is a valid date
	 */
	public static boolean validDate(String dateString) {
		return DateTimeUtil.parseDate(dateString) != null;
	}
	
	/**
	 * Checks whether a string is a valid time
	 */
	public static boolean validTime(String timeString) {
		return DateTimeUtil.parseTime(timeString) != null;
	}

}

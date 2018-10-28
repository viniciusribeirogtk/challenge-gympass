package br.com.gympass.utils;

import java.time.LocalTime;

public class TimeUtils {

	public static LocalTime parseStringToDuration(String time) {
		String[] durationValues = time.replace(".", ":").split(":");
		LocalTime timeDuration = LocalTime.of(
				0, 
				Integer.valueOf(durationValues[0]),
				Integer.valueOf(durationValues[1]),
				Integer.valueOf(durationValues[2]));
//		Duration duration = Duration.ofMinutes(Long.parseLong(durationValues[0]));
//		duration = duration.plusSeconds(Long.parseLong(durationValues[1]));
//		duration = duration.plusMillis(Long.parseLong(durationValues[2]));
		return timeDuration;

	}

}

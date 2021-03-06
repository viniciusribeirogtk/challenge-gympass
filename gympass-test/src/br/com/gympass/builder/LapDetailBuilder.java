package br.com.gympass.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.gympass.domain.LapDetails;
import br.com.gympass.utils.TimeUtils;

public class LapDetailBuilder {
	
	private static final Pattern PILOT_ID_REGEX =  Pattern.compile("\\d{3}");
	private static final Pattern PILOT_NAME_REGEX =  Pattern.compile("\\w\\.\\w.*");
	
	public static LapDetails buildLapDetails(String[] line) {
		return new LapDetails(
				line[0], 
				Integer.parseInt(matchRegex(PILOT_ID_REGEX, line[1])),
				matchRegex(PILOT_NAME_REGEX, line[1]), 
				Integer.parseInt(line[2]), 
				TimeUtils.parseStringToDuration(line[3]),
				Double.parseDouble(line[4].replace(",", ".")));
	}
	
	private static String matchRegex(Pattern regex, String value) {
		Matcher matcher = regex.matcher(value);
		matcher.find();
		return matcher.group();
	}

}

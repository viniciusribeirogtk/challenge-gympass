package br.com.gympass;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.com.gympass.builder.LapDetailBuilder;
import br.com.gympass.domain.LapDetails;
import br.com.gympass.domain.PilotRaceResult;
import br.com.gympass.service.RaceService;

public class Start {
	
	private static final Pattern lapTimeRegex = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]\\.\\d+");
	private static final Pattern pilotNameCodeAndLapRegex = Pattern.compile("(\\d{3} – \\w.\\w+\\s+\\d)");
	
    public static void main(String[] args) {
        String file = "log.txt";
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file))) {

            lines = bufferedReader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        mountRaceResult(lines);

    }

	private static void mountRaceResult(List<String> lines) {
		//Removing Header
		lines.remove(0);
		List<LapDetails> lapDetails = new ArrayList<>();
		
		for (String line : lines) {
			String[] resultLine = line.split("\\s{2,}");
			lapDetails.add(LapDetailBuilder.buildLapDetails(resultLine));
		}
		
		List<PilotRaceResult> resultRace = RaceService.fillResultRace(lapDetails);
//		lapDetails.forEach(System.out::println);
		resultRace.forEach(System.out::println);
		System.out.println(RaceService.getBestLapRace());
		
	}

//	private static String extractMatchesLine(Pattern regex, String line) {
//		Matcher matcher = regex.matcher(line);
//		matcher.find();
//		return matcher.group();
//	}
}

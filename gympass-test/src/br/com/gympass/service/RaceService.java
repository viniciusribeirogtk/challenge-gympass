package br.com.gympass.service;

import static java.util.Objects.nonNull;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gympass.domain.LapDetails;
import br.com.gympass.domain.PilotRaceResult;

public class RaceService {
	
	static LocalTime bestLapRace = LocalTime.MAX;
	
	public static List<PilotRaceResult> fillResultRace(List<LapDetails> laps){
		List<PilotRaceResult> pilotRaceResults = new ArrayList<>();
		Map<Integer, PilotRaceResult> pilotRaceResultMap = new HashMap<>();
		
		laps.forEach(lap -> {
			PilotRaceResult pilotRaceResult = pilotRaceResultMap.get(lap.getPilotCode());
			if (isBestLapRace(lap, bestLapRace)) {
				bestLapRace = lap.getLapTime();
			}
			
			if(nonNull(pilotRaceResult)){
				if (lap.getLap() <= 4) {
					pilotRaceResult.setLaps(pilotRaceResult.getLaps() + 1);
					pilotRaceResult.setTotalTime(pilotRaceResult.getTotalTime().plus(lap.getLapTime().toSecondOfDay(), ChronoUnit.SECONDS));
					pilotRaceResult.setAverageTimeTotal(pilotRaceResult.getAverageTimeTotal() + lap.getSpeedLapAverage());
					if(isBestLapPilot(lap, pilotRaceResult)) {
						pilotRaceResult.setBestLapTime(lap.getLapTime());
					}
					pilotRaceResultMap.replace(lap.getPilotCode(), pilotRaceResult);
				}
			} else {
				pilotRaceResultMap.put(lap.getPilotCode(), new PilotRaceResult(0, 
						lap.getPilotCode(), 
						lap.getPilotName(), 
						lap.getLap(), 
						lap.getLapTime(),
						lap.getLapTime(),
						0.0,
						lap.getLapTime()));
			}
		});
		pilotRaceResultMap.entrySet().forEach(pilotPosition -> {
			pilotRaceResults.add(pilotPosition.getValue());
		});
		
		pilotRaceResults.sort(new Comparator<PilotRaceResult>() {
			@Override
			public int compare(PilotRaceResult o1, PilotRaceResult o2) {
				return o1.getTotalTime().compareTo(o2.getTotalTime());
			}
		});
		
		for (int i = 0; i < pilotRaceResults.size(); i++) {
			PilotRaceResult pilotRaceResult = pilotRaceResults.get(i);
			pilotRaceResult.setPosition(i+1);
			pilotRaceResult.setAverageTimeTotal(pilotRaceResult.getAverageTimeTotal() / 4);
		}
		
		return pilotRaceResults;
	}

	private static boolean isBestLapPilot(LapDetails lap, PilotRaceResult pilotRaceResult) {
		int result = lap.getLapTime().compareTo(pilotRaceResult.getBestLapTime());
		return result == -1 ? true : false;
	}
	
	private static boolean isBestLapRace(LapDetails lap, LocalTime bestLapRace) {
		int result = lap.getLapTime().compareTo(bestLapRace);
		return result == -1 ? true : false;
	}

	public static LocalTime getBestLapRace() {
		return bestLapRace;
	}
}

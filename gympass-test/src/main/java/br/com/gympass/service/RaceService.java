package br.com.gympass.service;

import static java.util.Objects.nonNull;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gympass.vo.LapDetailsVO;
import br.com.gympass.vo.PilotRaceResultVO;
import br.com.gympass.vo.RaceResultVO;

public class RaceService {
	
	private LocalTime bestLapRace = LocalTime.MAX;
	
	public void printRaceResult(RaceResultVO raceResultVO) {
		List<PilotRaceResultVO> pilotRaceResultVOs = raceResultVO.getPilotRaceResultVOs();

		System.out.println("============================= RACE RESULT ============================= ");
		System.out.println("POSITION                | PILOT CODE                | PILOT NAME                      | LAPS COMPLETED                | TOTAL TIME             ");
		pilotRaceResultVOs.forEach(pilotResult -> {
			System.out.println(pilotResult.getPosition()+
					"                         "+
					pilotResult.getPilotCode()+
					"                          "+
					pilotResult.getPilotName()+
					"                               "+
					pilotResult.getLaps()+
					"                          "+
					pilotResult.getTotalTimeFormated());
		});
		System.out.println("======================================================================= ");
		System.out.println("============================= BONUS RESULT ============================ ");
		System.out.println("PILOT NAME                | PILOT POSITION                | BEST LAP TIME                      | TIME REMAINS                | AVERAGE RACE             ");
		pilotRaceResultVOs.forEach(pilotResult -> {
			System.out.println(
					pilotResult.getPilotName()+
					"                         "+
					pilotResult.getPosition()+
					"                           "+
					pilotResult.getBestLapTimeFormated()+
					"                           "+
					pilotResult.getTimeRemains()+
					"                         "+
					+pilotResult.getAverageTimeTotal());
			
		});
		System.out.println("\n\n");
		System.out.println("BEST RACE LAP TIME: " +raceResultVO.getBestLapRace());
	}
	
	public RaceResultVO fillResultRace(List<LapDetailsVO> laps){
		RaceResultVO raceResultVO = new RaceResultVO();
		List<PilotRaceResultVO> pilotRaceResultVOs = new ArrayList<>();
		Map<Integer, PilotRaceResultVO> pilotRaceResultMap = new HashMap<>();
		
		laps.forEach(lap -> {
			PilotRaceResultVO pilotRaceResultVO = pilotRaceResultMap.get(lap.getPilotCode());
			if (isBestLapRace(lap.getLapTime(), bestLapRace)) {
				bestLapRace = lap.getLapTime();
				raceResultVO.setBestLapRace(lap.getLapTime());
			}
			if(nonNull(pilotRaceResultVO)){
				fillPilotLap(pilotRaceResultMap, lap, pilotRaceResultVO);
			} else {
				addPilot(pilotRaceResultMap, lap);
			}
		});
		fillPilotRaceResult(pilotRaceResultVOs, pilotRaceResultMap);
		sortRaceTime(pilotRaceResultVOs);
		fillPositionPilotAndAverageTime(pilotRaceResultVOs);
		raceResultVO.setPilotRaceResultVOs(pilotRaceResultVOs);
		return raceResultVO;
	}

	private void fillPositionPilotAndAverageTime(List<PilotRaceResultVO> pilotRaceResultVOs) {
		for (int i = 0; i < pilotRaceResultVOs.size(); i++) {
			PilotRaceResultVO pilotRaceResultVO = pilotRaceResultVOs.get(i);
			pilotRaceResultVO.setPosition(i+1);
			pilotRaceResultVO.setAverageTimeTotal(pilotRaceResultVO.getAverageTimeTotal() / 4);
		}
		//Fill time after winner
		LocalTime winnerTime = pilotRaceResultVOs.get(0).getTotalTime();
		for (int i = 1; i < pilotRaceResultVOs.size(); i++) {
			PilotRaceResultVO pilotRaceResultVO = pilotRaceResultVOs.get(i);
			pilotRaceResultVO.setTimeRemains(pilotRaceResultVO.getTotalTime().minus(winnerTime.toSecondOfDay(), ChronoUnit.SECONDS));
		}
	}

	private void sortRaceTime(List<PilotRaceResultVO> pilotRaceResultVOs) {
		pilotRaceResultVOs.sort(new Comparator<PilotRaceResultVO>() {
			@Override
			public int compare(PilotRaceResultVO o1, PilotRaceResultVO o2) {
				return o1.getTotalTime().compareTo(o2.getTotalTime());
			}
		});
	}

	private void fillPilotRaceResult(List<PilotRaceResultVO> pilotRaceResultVOs,
			Map<Integer, PilotRaceResultVO> pilotRaceResultMap) {
		pilotRaceResultMap.entrySet().forEach(pilotPosition -> {
			pilotRaceResultVOs.add(pilotPosition.getValue());
		});
	}

	private void addPilot(Map<Integer, PilotRaceResultVO> pilotRaceResultMap, LapDetailsVO lap) {
		pilotRaceResultMap.put(lap.getPilotCode(), new PilotRaceResultVO(0, 
				lap.getPilotCode(), 
				lap.getPilotName(), 
				lap.getLap(), 
				lap.getLapTime(),
				lap.getLapTime(),
				0.0,
				LocalTime.MIN));
	}

	private void fillPilotLap(Map<Integer, PilotRaceResultVO> pilotRaceResultMap, LapDetailsVO lap,
			PilotRaceResultVO pilotRaceResultVO) {
		if (lap.getLap() <= 4) {
			pilotRaceResultVO.setLaps(pilotRaceResultVO.getLaps() + 1);
			pilotRaceResultVO.addTotalTime(lap.getLapTime());
			pilotRaceResultVO.setAverageTimeTotal(pilotRaceResultVO.getAverageTimeTotal() + lap.getSpeedLapAverage());
			if(isBestLapPilot(lap, pilotRaceResultVO)) {
				pilotRaceResultVO.setBestLapTime(lap.getLapTime());
			}
			pilotRaceResultMap.replace(lap.getPilotCode(), pilotRaceResultVO);
		}
	}

	private static boolean isBestLapPilot(LapDetailsVO lap, PilotRaceResultVO pilotRaceResultVO) {
		int result = lap.getLapTime().compareTo(pilotRaceResultVO.getBestLapTime());
		return result == -1 ? true : false;
	}
	
	private static boolean isBestLapRace(LocalTime pilotLapTime, LocalTime bestLapRace) {
		int result = pilotLapTime.compareTo(bestLapRace);
		return result == -1 ? true : false;
	}
}

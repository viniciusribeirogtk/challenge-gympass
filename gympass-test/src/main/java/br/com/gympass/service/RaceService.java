package br.com.gympass.service;

import static java.util.Objects.isNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gympass.vo.LapDetailsVO;
import br.com.gympass.vo.PilotResultVO;
import br.com.gympass.vo.RaceResultVO;

public class RaceService {
	
	private LocalTime bestLapRace = LocalTime.MAX;
	private final Integer TOTAL_LAPS_RACE = 4;
	private DecimalFormat df = new DecimalFormat("##.###");
	
	public RaceResultVO fillResultRace(List<LapDetailsVO> laps){
		RaceResultVO raceResultVO = new RaceResultVO();
		Map<Integer, PilotResultVO> pilotResultMap = new HashMap<>();
		laps.forEach(lap -> {
			PilotResultVO pilotResultVO = pilotResultMap.get(lap.getPilotCode());
			if(isNull(pilotResultVO)){
				addPilot(pilotResultMap, lap);
			} else {
				fillPilotLap(pilotResultMap, lap, pilotResultVO);
			}
			if (isBestLapRace(lap.getLapTime(), bestLapRace)) {
				bestLapRace = lap.getLapTime();
				raceResultVO.setBestLapRace(lap.getLapTime());
			}
		});
		List<PilotResultVO> pilotResultVOs = extractRaceResultByMap(pilotResultMap);
		completeRaceInformations(pilotResultVOs);
		raceResultVO.setPilotResultVOs(pilotResultVOs);
		return raceResultVO;
	}
	
	public void printRaceResult(RaceResultVO raceResultVO) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumIntegerDigits(2);
		List<PilotResultVO> pilotResultVOs = raceResultVO.getPilotRaceResultVOs();
		System.out.println("========================================================== RACE RESULT ========================================================== ");
		System.out.println("POSITION                | PILOT CODE                | PILOT NAME                      | LAPS COMPLETED                | TOTAL TIME             ");
		pilotResultVOs.forEach(pilotResult -> {
			System.out.println(
					numberFormat.format(pilotResult.getPosition())+
					"                         "+
					numberFormat.format(pilotResult.getPilotCode())+
					"                          "+
					pilotResult.getPilotName()+
					"                               "+
					numberFormat.format(pilotResult.getLaps())+
					"                          "+
					pilotResult.getTotalTimeFormated());
		});
		System.out.println("========================================================== BONUS RESULT ========================================================= ");
		System.out.println("PILOT NAME                | PILOT POSITION                | BEST LAP TIME                      | TIME REMAINS                | AVERAGE RACE             ");
		pilotResultVOs.forEach(pilotResult -> {
			System.out.println(
					pilotResult.getPilotName()+
					"                         "+
					numberFormat.format(pilotResult.getPosition())+
					"                           "+
					pilotResult.getBestLapTimeFormated()+
					"                           "+
					pilotResult.getTimeRemainsFormated()+
					"                         "+
					df.format(pilotResult.getAverageTimeTotal()));
			
		});
		System.out.println("================================================================================================================================= ");
		System.out.println("\n");
		System.out.println("BEST RACE LAP TIME: " +raceResultVO.getBestLapRace());
	}
	
	private void completeRaceInformations(List<PilotResultVO> pilotResultVOs) {
		sortRaceByTotalTime(pilotResultVOs);
		addPilotPositionAndAverageTime(pilotResultVOs);
		collectRemainginTime(pilotResultVOs);
		
	}

	private void collectRemainginTime(List<PilotResultVO> pilotResultVOs) {
		LocalTime winnerTime = pilotResultVOs.get(0).getTotalTime();
		for (int i = 1; i < pilotResultVOs.size(); i++) {
			PilotResultVO pilotResultVO = pilotResultVOs.get(i);
			pilotResultVO.setTimeRemains(pilotResultVO.getTotalTime().minus(winnerTime.toSecondOfDay(), ChronoUnit.SECONDS));
		}
	}

	private void addPilotPositionAndAverageTime(List<PilotResultVO> pilotResultVOs) {
		for (int i = 0; i < pilotResultVOs.size(); i++) {
			PilotResultVO pilotResultVO = pilotResultVOs.get(i);
			pilotResultVO.setPosition(i+1);
			pilotResultVO.setAverageTimeTotal(pilotResultVO.getAverageTimeTotal() / TOTAL_LAPS_RACE);
		}
	}

	private void sortRaceByTotalTime(List<PilotResultVO> pilotResultVOs) {
		pilotResultVOs.sort(new Comparator<PilotResultVO>() {
			@Override
			public int compare(PilotResultVO o1, PilotResultVO o2) {
				return o1.getTotalTime().compareTo(o2.getTotalTime());
			}
		});
	}

	private List<PilotResultVO> extractRaceResultByMap(Map<Integer, PilotResultVO> pilotRaceResultMap) {
		List<PilotResultVO> pilotResultVOs = new ArrayList<>();
		pilotRaceResultMap.entrySet().forEach(pilotPosition -> {
			pilotResultVOs.add(pilotPosition.getValue());
		});
		return pilotResultVOs;
	}

	private void addPilot(Map<Integer, PilotResultVO> pilotRaceResultMap, LapDetailsVO lap) {
		pilotRaceResultMap.put(lap.getPilotCode(), new PilotResultVO(0, 
				lap.getPilotCode(), 
				lap.getPilotName(), 
				lap.getLap(), 
				lap.getLapTime(),
				lap.getLapTime(),
				0.0,
				LocalTime.MIN));
	}

	private void fillPilotLap(Map<Integer, PilotResultVO> pilotRaceResultMap, LapDetailsVO lap,
			PilotResultVO pilotResultVO) {
		if (lap.getLap() <= TOTAL_LAPS_RACE) {
			pilotResultVO.setLaps(pilotResultVO.getLaps() + 1);
			pilotResultVO.addTotalTime(lap.getLapTime());
			pilotResultVO.setAverageTimeTotal(pilotResultVO.getAverageTimeTotal() + lap.getSpeedLapAverage());
			if(isBestLapPilot(lap, pilotResultVO)) {
				pilotResultVO.setBestLapTime(lap.getLapTime());
			}
			pilotRaceResultMap.replace(lap.getPilotCode(), pilotResultVO);
		}
	}

	private static boolean isBestLapPilot(LapDetailsVO lap, PilotResultVO pilotResultVO) {
		int result = lap.getLapTime().compareTo(pilotResultVO.getBestLapTime());
		return result == -1 ? true : false;
	}
	
	private static boolean isBestLapRace(LocalTime pilotLapTime, LocalTime bestLapRace) {
		int result = pilotLapTime.compareTo(bestLapRace);
		return result == -1 ? true : false;
	}
}

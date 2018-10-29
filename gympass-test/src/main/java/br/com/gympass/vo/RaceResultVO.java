package br.com.gympass.vo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RaceResultVO {

	private List<PilotResultVO> pilotResultVOs = new ArrayList<>();
	private LocalTime bestLapRace;
	
	public List<PilotResultVO> getPilotRaceResultVOs() {
		return pilotResultVOs;
	}

	public void setPilotResultVOs(List<PilotResultVO> pilotResultVOs) {
		this.pilotResultVOs = pilotResultVOs;
	}
	
	public void setBestLapRace(LocalTime bestLapRace) {
		this.bestLapRace = bestLapRace;
	}

	public String getBestLapRace() {
		return String.format("%02d:%02d:%03d", 
				bestLapRace.getMinute(), 
				bestLapRace.getSecond(),
				bestLapRace.getNano());
	}
}

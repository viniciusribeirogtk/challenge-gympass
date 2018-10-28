package br.com.gympass.vo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RaceResultVO {

	private List<PilotRaceResultVO> pilotRaceResultVOs = new ArrayList<>();
	private LocalTime bestLapRace;
	
	public List<PilotRaceResultVO> getPilotRaceResultVOs() {
		return pilotRaceResultVOs;
	}

	public void setPilotRaceResultVOs(List<PilotRaceResultVO> pilotRaceResultVOs) {
		this.pilotRaceResultVOs = pilotRaceResultVOs;
	}
	
	public void setBestLapRace(LocalTime bestLapRace) {
		this.bestLapRace = bestLapRace;
	}

	public String getBestLapRace() {
		return String.format("%02d:%02d:%03d\"", 
				bestLapRace.getMinute(), 
				bestLapRace.getSecond(),
				bestLapRace.getNano());
	}
}

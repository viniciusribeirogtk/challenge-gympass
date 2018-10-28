package br.com.gympass.vo;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class PilotRaceResultVO {

	private Integer position;
	private Integer pilotCode;
	private String pilotName;
	private Integer laps;
	private LocalTime totalTime;
	private LocalTime bestLapTime;
	private Double averageTimeTotal;
	private LocalTime timeRemains;
	
	public PilotRaceResultVO(Integer position, Integer pilotCode, String pilotName, Integer laps, LocalTime totalTime,
			LocalTime bestLapTime, Double averageTimeTotal, LocalTime timeRemains) {
		this.position = position;
		this.pilotCode = pilotCode;
		this.pilotName = pilotName;
		this.laps = laps;
		this.totalTime = totalTime;
		this.bestLapTime = bestLapTime;
		this.averageTimeTotal = averageTimeTotal;
		this.timeRemains = timeRemains;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPilotCode() {
		return pilotCode;
	}

	public void setPilotCode(Integer pilotCode) {
		this.pilotCode = pilotCode;
	}

	public String getPilotName() {
		return pilotName;
	}

	public void setPilotName(String pilotName) {
		this.pilotName = pilotName;
	}

	public Integer getLaps() {
		return laps;
	}

	public void setLaps(Integer laps) {
		this.laps = laps;
	}

	public LocalTime getTotalTime() {
		return totalTime;
	}
	
	public void addTotalTime(LocalTime time) {
		this.totalTime = this.totalTime.plus(time.toSecondOfDay(), ChronoUnit.SECONDS);
	}
	
	public String getTotalTimeFormated() {
		return String.format("%02d:%02d:%03d\"", 
				totalTime.getMinute(), 
				totalTime.getSecond(),
				totalTime.getNano());
	}

	public void setTotalTime(LocalTime totalTime) {
		this.totalTime = totalTime;
	}

	public LocalTime getBestLapTime() {
		return bestLapTime;
	}
	
	public String getBestLapTimeFormated() {
		return String.format("%02d:%02d:%03d\"", 
				bestLapTime.getMinute(), 
				bestLapTime.getSecond(),
				bestLapTime.getNano());
	}

	public void setBestLapTime(LocalTime bestLapTime) {
		this.bestLapTime = bestLapTime;
	}

	public Double getAverageTimeTotal() {
		return averageTimeTotal;
	}

	public void setAverageTimeTotal(Double averageTimeTotal) {
		this.averageTimeTotal = averageTimeTotal;
	}

	public LocalTime getTimeRemains() {
		return timeRemains;
	}

	public void setTimeRemains(LocalTime timeRemains) {
		this.timeRemains = timeRemains;
	}

	@Override
	public String toString() {
		return "PilotRaceResult [position=" + position + ", pilotCode=" + pilotCode + ", pilotName=" + pilotName
				+ ", laps=" + laps + ", totalTime=" + getTotalTimeFormated() + ", bestLapTime=" + getBestLapTimeFormated() + ", averageTimeTotal="
				+ averageTimeTotal + ", timeRemains=" + timeRemains + "]";
	}
	
}
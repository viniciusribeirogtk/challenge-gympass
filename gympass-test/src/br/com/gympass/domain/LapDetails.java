package br.com.gympass.domain;

import java.time.LocalTime;

public class LapDetails {

	private String hourLap;
	private Integer pilotCode;
	private String pilotName;
	private Integer lap;
	private LocalTime lapTime;
	private Double speedLapAverage;

	public LapDetails(String hourLap, Integer pilotCode, String pilotName, Integer lap, LocalTime lapTime,
			Double speedLapAverage) {
		this.hourLap = hourLap;
		this.pilotCode = pilotCode;
		this.pilotName = pilotName;
		this.lap = lap;
		this.lapTime = lapTime;
		this.speedLapAverage = speedLapAverage;
	}

	public String getHourLap() {
		return hourLap;
	}

	public void setHourLap(String hourLap) {
		this.hourLap = hourLap;
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

	public Integer getLap() {
		return lap;
	}

	public void setLap(Integer lap) {
		this.lap = lap;
	}

	public LocalTime getLapTime() {
		return lapTime;
	}

	public void setLapTime(LocalTime lapTime) {
		this.lapTime = lapTime;
	}

	public Double getSpeedLapAverage() {
		return speedLapAverage;
	}

	public void setSpeedLapAverage(Double speedLapAverage) {
		this.speedLapAverage = speedLapAverage;
	}

	@Override
	public String toString() {
		return "LapDetails [hourLap=" + hourLap + ", pilotCode=" + pilotCode + ", pilotName=" + pilotName + ", lap="
				+ lap + ", lapTime=" + lapTime + ", speedLapAverage=" + speedLapAverage + "]";
	}
	
	

}

package br.com.gympass.domain;

import java.util.ArrayList;
import java.util.List;

public class RaceDetails {

	private List<LapDetails> lapDetails = new ArrayList<>();

	public List<LapDetails> getLapDetails() {
		return lapDetails;
	}

	public void setLapDetails(List<LapDetails> lapDetails) {
		this.lapDetails = lapDetails;
	}

}

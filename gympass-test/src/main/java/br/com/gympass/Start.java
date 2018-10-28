package br.com.gympass;

import java.util.List;

import br.com.gympass.service.RaceService;
import br.com.gympass.utils.RaceFileUtils;
import br.com.gympass.vo.LapDetailsVO;
import br.com.gympass.vo.RaceResultVO;

public class Start {
	
    public static void main(String[] args) {
    	List<LapDetailsVO> lapDetailsVO = RaceFileUtils.readKartFileAndBuildLapDetailsVO();
    	mountRaceResult(lapDetailsVO);
    }

	private static void mountRaceResult(List<LapDetailsVO> lapDetailsVO) {
		RaceService raceService = new RaceService();
		RaceResultVO raceResultVO = raceService.fillResultRace(lapDetailsVO);
		raceService.printRaceResult(raceResultVO);
	}

}

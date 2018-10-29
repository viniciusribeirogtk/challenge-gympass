package br.com.gympass;

import java.util.List;

import br.com.gympass.service.RaceService;
import br.com.gympass.utils.RaceFileUtils;
import br.com.gympass.vo.LapDetailsVO;
import br.com.gympass.vo.RaceResultVO;

public class StartRaceResult {
	
    public static void main(String[] args) {
    	RaceService raceService = new RaceService();
    	List<LapDetailsVO> lapDetailsVO = RaceFileUtils.buildLapDetailsVOByFile();
		RaceResultVO raceResultVO = raceService.fillResultRace(lapDetailsVO);
		raceService.printRaceResult(raceResultVO);
    }
}

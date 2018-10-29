package br.com.gympass.service;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.gympass.vo.LapDetailsVO;
import br.com.gympass.vo.PilotRaceResultVO;
import br.com.gympass.vo.RaceResultVO;

public class RaceServiceTest {

	private RaceService raceService = new RaceService();

	@Test
	public void testFillResultRace() {
		List<LapDetailsVO> laps = buildLapDetailsVO();
		RaceResultVO resultRace = raceService.fillResultRace(laps);

		PilotRaceResultVO first = resultRace.getPilotRaceResultVOs().get(0);
		PilotRaceResultVO second = resultRace.getPilotRaceResultVOs().get(1);
		PilotRaceResultVO third = resultRace.getPilotRaceResultVOs().get(2);
		PilotRaceResultVO fourth = resultRace.getPilotRaceResultVOs().get(3);
		
		assertEquals(first.getPosition(), Integer.valueOf(1));
		assertEquals(first.getPilotName(), "F.MASSA");
		assertEquals(first.getLaps(), Integer.valueOf(2));
		
		assertEquals(second.getPosition(), Integer.valueOf(2));
		assertEquals(second.getPilotName(), "K.RAIKKONEN");
		assertEquals(second.getAverageTimeTotal(), new Double(43.493));
		assertEquals(second.getTimeRemains(), LocalTime.of(00, 00, 02, 108));
		assertEquals(second.getLaps(), Integer.valueOf(2));
		
		assertEquals(third.getPosition(), Integer.valueOf(3));
		assertEquals(third.getPilotName(), "R.BARRICHELLO");
		assertEquals(third.getLaps(), Integer.valueOf(2));
		
		assertEquals(fourth.getPosition(), Integer.valueOf(4));
		assertEquals(fourth.getPilotName(), "M.WEBBER");
		assertEquals(fourth.getLaps(), Integer.valueOf(2));
	}

	private List<LapDetailsVO> buildLapDetailsVO() {
		return Arrays.asList(new LapDetailsVO("23:49:10.858", 33, "R.BARRICHELLO", 1, LocalTime.of(00, 1, 4, 352), 43.243),
					  new LapDetailsVO("23:49:12.667", 23, "M.WEBBER", 1, LocalTime.of(00, 1, 4, 414), 43.202),
					  new LapDetailsVO("23:49:11.075", 02, "K.RAIKKONEN", 1, LocalTime.of(00, 1, 4, 108), 43.408),
					  new LapDetailsVO("23:49:08.277", 38, "F.MASSA", 1, LocalTime.of(00, 1, 2, 275), 44.275),
					  new LapDetailsVO("23:49:11.075", 02, "K.RAIKKONEN", 2, LocalTime.of(00, 1, 3, 982), 43.493),
					  new LapDetailsVO("23:50:11.447", 38, "F.MASSA", 2, LocalTime.of(00, 1, 3, 170), 44.053),
					  new LapDetailsVO("23:50:14.860", 33, "R.BARRICHELLO", 2, LocalTime.of(00, 1, 4, 002), 43.48),
					  new LapDetailsVO("23:50:17.472", 23, "M.WEBBER", 2, LocalTime.of(00, 1, 4, 805), 42.941));

	}

}

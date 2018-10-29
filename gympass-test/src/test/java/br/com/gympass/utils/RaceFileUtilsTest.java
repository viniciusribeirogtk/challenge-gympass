package br.com.gympass.utils;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import br.com.gympass.vo.LapDetailsVO;

public class RaceFileUtilsTest {

	@Test
	public void testReadKartFileAndBuildLapDetailsVO() {
		List<LapDetailsVO> lapDetailsVO = RaceFileUtils.buildLapDetailsVOByFile();
		assertNotNull(lapDetailsVO);
		assertEquals(lapDetailsVO.get(0).getLap(), Integer.valueOf(1));
		assertEquals(lapDetailsVO.get(0).getLapTime(), LocalTime.of(00, 01,02,852));
		assertEquals(lapDetailsVO.get(0).getSpeedLapAverage(), new Double(44.275));
	}

}

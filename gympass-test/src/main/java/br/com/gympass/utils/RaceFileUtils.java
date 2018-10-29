package br.com.gympass.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gympass.builder.LapDetailVOBuilder;
import br.com.gympass.vo.LapDetailsVO;

public class RaceFileUtils {

	private static final String COLUMN_DELIMITER = "\\s{2,}";
	private static final String FILENAME = "kart.txt";

	public static List<LapDetailsVO> buildLapDetailsVOByFile(){
		List<String> kartResultLines = readKartFile();
		//Remove Header Line
		kartResultLines.remove(0);
		List<LapDetailsVO> lapDetailsVO = cutStringAndBuildLapDetails(kartResultLines);
		return lapDetailsVO;
	}

	private static List<LapDetailsVO> cutStringAndBuildLapDetails(List<String> kartResultLines) {
		List<LapDetailsVO> lapDetailsVO = new ArrayList<>();
		kartResultLines.forEach(resultLine -> {
			if (!resultLine.isEmpty()) {
				String[] line = resultLine.split(COLUMN_DELIMITER);
				lapDetailsVO.add(LapDetailVOBuilder.buildLapDetails(line));
			}
		});
		return lapDetailsVO;
	}
	
	private static List<String> readKartFile(){
		List<String> linesReaded = new ArrayList<>();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILENAME);
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            linesReaded = bufferedReader.lines().collect(Collectors.toList());
            System.out.println("Kart file Readed");
            linesReaded.forEach(System.out::println);
            System.out.println("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return linesReaded;
	}

}

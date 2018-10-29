package br.com.gympass.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gympass.builder.LapDetailVOBuilder;
import br.com.gympass.vo.LapDetailsVO;

public class RaceFileUtils {

	private static final String COLUMN_DELIMITER = "\\s{2,}";
	private static final String FILENAME = "kart.txt";

	public static List<LapDetailsVO> readKartFileAndBuildLapDetailsVO(){
		List<String> kartResultLines = readKartFileList();
		//Remove Header Line
		kartResultLines.remove(0);
		List<LapDetailsVO> lapDetailsVO = new ArrayList<>();
		
		for (String line : kartResultLines) {
			if (!line.isEmpty()) {
				String[] resultLine = line.split(COLUMN_DELIMITER);
				lapDetailsVO.add(LapDetailVOBuilder.buildLapDetails(resultLine));
			}
		}
		return lapDetailsVO;
	}
	
	private static List<String> readKartFileList(){
		List<String> linesReaded = new ArrayList<>();
		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(FILENAME))) {
            linesReaded = bufferedReader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
		return linesReaded;
	}

}

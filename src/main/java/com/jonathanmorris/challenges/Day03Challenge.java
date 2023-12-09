package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day03Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final List<List<Integer>> DIRECTIONS =
      List.of(
          List.of(-1, -1),
          List.of(-1, 0),
          List.of(-1, 1),
          List.of(0, -1),
          List.of(0, 1),
          List.of(1, -1),
          List.of(1, 0),
          List.of(1, 1));

  @Override
  public void execute(List<String> lines) {
    List<Integer> allPartNumbers = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      String currentLine = lines.get(i);
      logger.debug("Current line is: {}", currentLine);
      String currentNumber = "";
      List<String> characters = Arrays.asList(currentLine.split(""));
      Boolean isPartNumber = false;

      for (int j = 0; j < characters.size(); j++) {
        String character = characters.get(j);
        if (isNumber(character)) {
          currentNumber += character;
          logger.info("Current character is: {}", character);
          isPartNumber = isPartNumber || this.checkSurrounding(lines, i, j);
        } else if (currentNumber.length() > 0) {
          logger.info("Found part number: {}", currentNumber);
          if (isPartNumber) {
            allPartNumbers.add(Integer.parseInt(currentNumber));
          }
          currentNumber = "";
          isPartNumber = false;
        }
      }
    }
    logger.info("Answer to part 1: {}", allPartNumbers.stream().reduce(0, Integer::sum));
  }

  private Boolean checkSurrounding(List<String> lines, int ix, int iy) {
    for (List<Integer> direction : DIRECTIONS) {
      int jx = direction.get(0) + ix;
      int jy = direction.get(1) + iy;
      if (jx < 0 || jy < 0 || jx >= lines.size() || jy >= lines.get(ix).length()) {
        continue;
      }
      String currentChar = String.valueOf(lines.get(jx).charAt(jy));
      logger.debug("Current char is: {}", currentChar);
      if (currentChar.matches("[^.|\\d|\\w|\\s]*")) {
        logger.debug("Found a part number!");
        return true;
      }
    }
    return false;
  }

  private Boolean isNumber(String string) {
    return string.matches("[0-9]");
  }
}

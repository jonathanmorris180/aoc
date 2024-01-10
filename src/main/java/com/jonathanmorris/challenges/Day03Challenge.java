package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day03Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day03Challenge.class);
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
    this.executePart2(lines);
    List<Integer> allPartNumbers = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      String currentLine = lines.get(i).trim();
      String currentNumber = "";
      String[] characters = currentLine.split("");
      Boolean isPartNumber = false;

      for (int j = 0; j < characters.length; j++) {
        String character = characters[j];
        Boolean isLastCharacter = j == characters.length - 1;
        Boolean isNumber = isNumber(character);
        if (isNumber) {
          // Small optimization
          if (isPartNumber && !isLastCharacter) {
            currentNumber += character;
            continue;
          }
          currentNumber += character;
          isPartNumber = isPartNumber || this.checkSurrounding(lines, i, j);
        }
        if ((currentNumber.length() > 0 && !isNumber && isPartNumber)
            || (isLastCharacter && isNumber && isPartNumber)) {
          allPartNumbers.add(Integer.parseInt(currentNumber));
          currentNumber = "";
          isPartNumber = false;
        }
        if (!isNumber && !isPartNumber) {
          currentNumber = "";
        }
      }
    }
    logger.info("Answer to part 1: {}", allPartNumbers.stream().reduce(0, Integer::sum));
  }

  private void executePart2(List<String> lines) {
    List<Integer> gearRatios = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      String currentLine = lines.get(i).trim();
      String[] characters = currentLine.split("");
      for (int j = 0; j < characters.length; j++) {
        String character = characters[j];
        Boolean isStar = character.equals("*");
        if (isStar) {
          List<Integer> surroundingNumbers = this.getSurroundingNumbers(lines, i, j);
          if (surroundingNumbers.size() == 2) {
            gearRatios.add(surroundingNumbers.stream().reduce(1, (a, b) -> a * b));
          }
        }
      }
    }
    logger.info("Answer to part 2: {}", gearRatios.stream().reduce(0, Integer::sum));
  }

  private List<Integer> getSurroundingNumbers(List<String> lines, int ix, int iy) {
    List<Integer> surroundingNumbers = new ArrayList<>();
    List<List<Integer>> visited = new ArrayList<>();
    for (int i = 0; i < DIRECTIONS.size(); i++) {
      List<Integer> direction = DIRECTIONS.get(i);
      int jx = direction.get(0) + ix;
      int jy = direction.get(1) + iy;
      if (visited.contains(List.of(jx, jy))) {
        continue;
      }
      if (jx < 0 || jy < 0 || jx >= lines.size() || jy >= lines.get(ix).length()) {
        continue;
      }
      String currentChar = String.valueOf(lines.get(jx).charAt(jy));
      if (isNumber(currentChar)) {
        String currentLine = lines.get(jx);
        List<String> numberChars = new ArrayList<>(List.of(currentChar));
        int back = jy - 1;
        int forwards = jy + 1;
        while (back >= 0 && isNumber(String.valueOf(currentLine.charAt(back)))) {
          String backChar = String.valueOf(currentLine.charAt(back));
          if (isNumber(backChar)) {
            numberChars.add(0, backChar);
          } else {
            break;
          }
          visited.add(List.of(jx, back));
          back--;
        }
        while (forwards < currentLine.length()
            && isNumber(String.valueOf(currentLine.charAt(forwards)))) {
          String forwardsChar = String.valueOf(currentLine.charAt(forwards));
          if (isNumber(forwardsChar)) {
            numberChars.add(forwardsChar);
          } else {
            break;
          }
          visited.add(List.of(jx, forwards));
          forwards++;
        }
        surroundingNumbers.add(Integer.parseInt(String.join("", numberChars)));
      }
    }
    return surroundingNumbers;
  }

  private Boolean checkSurrounding(List<String> lines, int ix, int iy) {
    for (List<Integer> direction : DIRECTIONS) {
      int jx = direction.get(0) + ix;
      int jy = direction.get(1) + iy;
      if (jx < 0 || jy < 0 || jx >= lines.size() || jy >= lines.get(ix).length()) {
        continue;
      }
      String currentChar = String.valueOf(lines.get(jx).charAt(jy));
      if (currentChar.matches("[^.|\\d|\\w|\\s]*")) {
        return true;
      }
    }
    return false;
  }

  private Boolean isNumber(String string) {
    return string.matches("[0-9]");
  }
}

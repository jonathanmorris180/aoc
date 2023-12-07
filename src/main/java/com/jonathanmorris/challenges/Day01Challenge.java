package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day01Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final Map<String, Integer> WORDS_TO_NUMBER =
      Map.of(
          "one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8,
          "nine", 9);

  @Override
  public void execute(final List<String> lines) {
    List<Integer> numbers = new ArrayList<>();
    for (String line : lines) {
      logger.info("Line is: {}", line);
      List<Integer> numberStrings = getNumbersFrom(line);
      List<Integer> firstAndLast = List.of(numberStrings.get(0), numberStrings.get(numberStrings.size() - 1));
      String numberString =
          String.join("", firstAndLast.stream().map(String::valueOf).toArray(String[]::new));
      logger.info("Number string is: {}", numberString);
      numbers.add(Integer.parseInt(numberString));
    }
    int result = numbers.stream().reduce(0, Integer::sum);
    logger.info("Result is: {}", result);
  }

  private List<Integer> getNumbersFrom(final String line) {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < line.length(); i++) {
      String currentString = line.substring(i);
      String firstChar = String.valueOf(currentString.charAt(0));
      for (String numberWord : WORDS_TO_NUMBER.keySet()) {
        if (currentString.startsWith(numberWord)) {
          numbers.add(WORDS_TO_NUMBER.get(numberWord));
        }
      }
      if (firstChar.matches("[0-9]")) {
        numbers.add(Integer.parseInt(firstChar));
      }
    }
    return numbers;
  }
}

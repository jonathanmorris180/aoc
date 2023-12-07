package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day01Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final Map<String, Integer> WORDS_TO_NUMBER =
      Map.of(
          "one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8,
          "nine", 9);
  private static final String WORDS_TO_NUMBER_REGEX = String.join("|", WORDS_TO_NUMBER.keySet());

  @Override
  public void execute(final List<String> lines) {
    List<Integer> numbers = new ArrayList<>();
    numbers.toString();
    String firstNumber = "";
    String lastNumber = "";
    logger.info("wordsToNumberRegex is: {}", WORDS_TO_NUMBER_REGEX);
    for (String line : lines) {
      line = this.getStringWithNumbers(line);
      logger.info("Line is: {}", line);
      List<String> chars = Arrays.asList(line.trim().split(""));
      for (String currentChar : chars) {
        if (this.isNumber(currentChar)) {
          firstNumber = currentChar;
          break;
        }
      }
      for (int i = chars.size() - 1; i >= 0; i--) {
        String currentChar = chars.get(i);
        if (this.isNumber(currentChar)) {
          lastNumber = currentChar;
          break;
        }
      }
      numbers.add(Integer.parseInt(String.format("%s%s", firstNumber, lastNumber)));
    }
    int result = numbers.stream().reduce(0, Integer::sum);
    logger.info("Result is: {}", result);
  }

  private Boolean isNumber(final String currentChar) {
    return currentChar.matches("[0-9]");
  }

  private String getStringWithNumbers(final String line) {
    Pattern pattern = Pattern.compile(WORDS_TO_NUMBER_REGEX);
    Matcher matcher = pattern.matcher(line);

    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
      String match = matcher.group();
      matcher.appendReplacement(buffer, String.valueOf(WORDS_TO_NUMBER.get(match)));
    }
    matcher.appendTail(buffer);
    logger.info("Buffer is: {}", buffer.toString());
    return buffer.toString();
  }
}

package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day04Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final String REGEX = "[^\\S]{2,}";

  @Override
  public void execute(List<String> lines) {
    List<Integer> cardPointValues = new ArrayList<>();
    for (String line : lines) {
      int numWinningNumbers = 0;
      int totalValue = 1;
      String cardContents = line.substring(line.indexOf(":") + 1).trim();
      String[] numbers = cardContents.split("\\|");
      String winningNumString = numbers[0].trim().replaceAll(REGEX, " ");
      String haveNumString = numbers[1].trim().replaceAll(REGEX, " ");
      List<String> winningNums = Arrays.asList(winningNumString.split(" "));
      List<String> haveNums = Arrays.asList(haveNumString.split(" "));
      for (String num : haveNums) {
        if (winningNums.contains(num)) {
          numWinningNumbers++;
        }
      }

      for (int i = 0; i < numWinningNumbers - 1; i++) {
        totalValue = totalValue * 2;
      }
      if (numWinningNumbers > 0) {
        cardPointValues.add(totalValue);
      }
    }
    logger.info("Answer to part 1: {}", cardPointValues.stream().reduce(0, Integer::sum));
  }
}

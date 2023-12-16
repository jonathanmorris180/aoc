package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day04Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final String REGEX = "[\\s]{2,}";

  @Override
  public void execute(List<String> lines) {
    List<Integer> cardPointValues = new ArrayList<>();
    List<Integer> numCardsByIndex = new ArrayList<>();
    List<Integer> numMatchingNumbersByIndex = new ArrayList<>();
    for (String line : lines) {
      int numWinningNumbers = 0;
      int totalValue = 1;
      numCardsByIndex.add(1);
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
      numMatchingNumbersByIndex.add(numWinningNumbers);

      for (int i = 0; i < numWinningNumbers - 1; i++) {
        totalValue = totalValue * 2;
      }
      if (numWinningNumbers > 0) {
        cardPointValues.add(totalValue);
      }
    }
    this.executePart2(numMatchingNumbersByIndex, numCardsByIndex, lines);
    logger.info("Answer to part 1: {}", cardPointValues.stream().reduce(0, Integer::sum));
  }

  private void executePart2(List<Integer> numMatchingNumbersByIndex, List<Integer> numCardsByIndex, List<String> lines) {
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      Integer numMatching = numMatchingNumbersByIndex.get(i);
      Integer cardNumber = Integer.valueOf(line.split(":")[0].replaceAll(REGEX, " ").split(" ")[1]);
      for (int j = 0; j < numCardsByIndex.get(cardNumber - 1); j++) {
        for (int idxToIncrease = cardNumber; idxToIncrease < cardNumber + numMatching; idxToIncrease++) {
          numCardsByIndex.set(idxToIncrease, numCardsByIndex.get(idxToIncrease) + 1);
        }
      }
    }
    logger.info("Answer to part 2: {}", numCardsByIndex.stream().reduce(0, Integer::sum));
  }
}

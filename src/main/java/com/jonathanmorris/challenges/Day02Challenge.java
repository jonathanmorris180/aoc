package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day02Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final Map<String, Integer> MAX_VALUES = Map.of("red", 12, "green", 13, "blue", 14);

  @Override
  public void execute(final List<String> lines) {
    // 12 red cubes, 13 green, 14 blue
    List<Integer> possibleGames = new ArrayList<>();

    for (String line : lines) {
      Integer gameId = Integer.parseInt(line.split(":")[0].split(" ")[1]);
      List<String> individualRounds =
          Arrays.stream(line.substring(line.indexOf(":") + 1).split(";"))
              .map(String::trim)
              .toList();
      Boolean isPossible = true;

      logger.debug("Individual rounds are: {}", individualRounds);
      logger.debug("Number of rounds is: {}", individualRounds.size());

      for (String game : individualRounds) {
        List<String> colorInfos = Arrays.stream(game.split(",")).map(String::trim).toList();
        logger.debug("Color infos are: {}", colorInfos);
        for (String colorInfo : colorInfos) {
          String color = colorInfo.split(" ")[1];
          Integer value = Integer.parseInt(colorInfo.split(" ")[0]);
          logger.debug("Color is: {}, value is: {}", color, value);
          if (value > MAX_VALUES.get(color)) {
            isPossible = false;
            break;
          }
        }
      }
      if (isPossible) {
        possibleGames.add(gameId);
      }
    }
    logger.info("Game ID total value is: {}", possibleGames.stream().reduce(0, Integer::sum));
  }
}

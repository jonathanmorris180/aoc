package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day02Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day02Challenge.class);
  private static final Map<String, Integer> MAX_VALUES = Map.of("red", 12, "green", 13, "blue", 14);

  @Override
  public void execute(final List<String> lines) {
    // 12 red cubes, 13 green, 14 blue
    List<Integer> possibleGames = new ArrayList<>();
    List<Integer> gamePowers = new ArrayList<>();

    for (String line : lines) {
      Integer gameId = Integer.parseInt(line.split(":")[0].split(" ")[1]);
      List<String> individualRounds =
          Arrays.stream(line.substring(line.indexOf(":") + 1).split(";"))
              .map(String::trim)
              .toList();
      Boolean isPossible = true;
      Map<String, Integer> highestValues =
          new HashMap<String, Integer>() {
            {
              put("red", null);
              put("green", null);
              put("blue", null);
            }
          };
      logger.debug("Individual rounds are: {}", individualRounds);

      for (String game : individualRounds) {
        List<String> colorInfos = Arrays.stream(game.split(",")).map(String::trim).toList();
        for (String colorInfo : colorInfos) {
          String color = colorInfo.split(" ")[1];
          Integer value = Integer.parseInt(colorInfo.split(" ")[0]);
          if (highestValues.get(color) == null || value > highestValues.get(color)) {
            highestValues.put(color, value);
          }
          if (value > MAX_VALUES.get(color)) {
            isPossible = false;
          }
        }
      }
      Integer gamePower = null;
      logger.debug("Highest values are: {}", highestValues);
      for (String color : highestValues.keySet()) {
        if (gamePower == null) {
          gamePower = highestValues.get(color);
          continue;
        }
        gamePower *= highestValues.get(color);
      }
      logger.debug("Game power is: {}", gamePower);
      gamePowers.add(gamePower);
      if (isPossible) {
        possibleGames.add(gameId);
      }
    }
    logger.info("Game power total value is: {}", gamePowers.stream().reduce(0, Integer::sum));
    logger.info("Game ID total value is: {}", possibleGames.stream().reduce(0, Integer::sum));
  }
}

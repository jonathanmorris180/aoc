package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day06Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final String REGEX = "[\\s]{2,}";

  @Override
  public void execute(List<String> lines) {
    List<Race> races = new ArrayList<>();
    String time = lines.get(0).replaceAll(REGEX, " ");
    String[] times = time.substring(time.indexOf(":") + 1).trim().split(" ");
    String distance = lines.get(1).replaceAll(REGEX, " ");
    String[] distances = distance.substring(distance.indexOf(":") + 1).trim().split(" ");
    for (int i = 0; i < times.length; i++) {
      Race newRace = new Race(Integer.valueOf(times[i]), Integer.valueOf(distances[i]));
      newRace.calculateWinners();
      races.add(newRace);
    }
    Integer result = races.stream().map(Race::getNumWinners).reduce(1, (a, b) -> a * b);
    logger.info("Answer for part 1 is {}", result);
  }

  private class Race {
    private Integer time;
    private Integer currentRecord;
    private Integer numWinners;

    private Race(Integer time, Integer distance) {
      this.time = time;
      this.currentRecord = distance;
      this.numWinners = 0;
    }

    private Integer getNumWinners() {
      logger.info("numWinners is {}", this.numWinners);
      return this.numWinners;
    }

    private void calculateWinners() {
      for (int i = 1; i < this.time; i++) {
        int remainingTime = this.time - i;
        int distance = i * remainingTime;
        if (distance > this.currentRecord) {
          this.numWinners++;
        }
      }
    }
  }
}

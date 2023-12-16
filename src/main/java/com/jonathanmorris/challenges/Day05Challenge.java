package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day05Challenge implements Challenge {

  private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);
  private static final String MAP = "map";
  private static final List<String> MAPS =
      List.of(
          "seed-to-soil",
          "soil-to-fertilizer",
          "fertilizer-to-water",
          "water-to-light",
          "light-to-temperature",
          "temperature-to-humidity",
          "humidity-to-location");

  @Override
  public void execute(List<String> lines) {
    List<Long> seeds =
        Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Long::valueOf).toList();
    Map<String, GardenerMap> gardenerMaps = new HashMap<>();
    List<Long> locations = new ArrayList<>();
    String mapName = "";
    for (String line : lines) {
      if (line.contains(MAP)) {
        mapName = line.split(MAP)[0].trim();
        GardenerMap map = new GardenerMap(mapName);
        gardenerMaps.put(map.getName(), map);
      } else if (line.trim().length() > 0 && mapName.length() > 0) {
        GardenerMap currentMap = gardenerMaps.get(mapName);
        currentMap.addLine(line);
      }
    }
    for (Long seed : seeds) {
      Long value = 0L;
      for (String map : MAPS) {
        logger.info("current map is {}", map);
        GardenerMap gardenerMap = gardenerMaps.get(map);
        if (value == 0) {
          Long nextVal = gardenerMap.getValue(seed);
          value = nextVal == null ? seed : nextVal;
          continue;
        }
        Long nextVal = gardenerMap.getValue(value);
        value = nextVal == null ? value : nextVal;
        logger.debug("current value is {}", value);
      }
      logger.debug("Adding value {}", value);
      locations.add(value);
    }
    logger.debug("locations are {}", locations);
    logger.debug("Answer for part one is {}", locations.stream().reduce(Long.MAX_VALUE, Long::min));
  }

  private class GardenerMap {
    private final String name;
    private final List<Line> lines;

    private GardenerMap(String name) {
      this.name = name;
      this.lines = new ArrayList<>();
    }

    private void addLine(String line) {
      this.lines.add(new Line(line));
    }

    private String getName() {
      return this.name;
    }

    private Long getValue(Long val) {
      // TODO: Figure out why this doesn't seem to return the correct value for seed 82
      for (Line line : lines) {
        Long sourceNum = line.getSourceNum();
        Long destNum = line.getDestNum();
        Long rangeLength = line.getRangeLength();
        if (val > sourceNum && val < sourceNum + rangeLength) {
          Long offset = val - sourceNum;
          Long result = destNum + offset;
          return result;
        }
      }
      return val;
    }
  }

  private class Line {
    private Long destNum;
    private Long sourceNum;
    private Long rangeLength;

    private Line(String line) {
      List<Long> numbers = Arrays.stream(line.split(" ")).map(Long::valueOf).toList();
      this.destNum = numbers.get(0);
      this.sourceNum = numbers.get(1);
      this.rangeLength = numbers.get(2);
    }

    private Long getRangeLength() {
      return this.rangeLength;
    }

    private Long getSourceNum() {
      return this.sourceNum;
    }

    private Long getDestNum() {
      return this.destNum;
    }
  }
}

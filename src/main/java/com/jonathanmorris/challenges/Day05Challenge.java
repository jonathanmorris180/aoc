package com.jonathanmorris.challenges;

import com.jonathanmorris.Challenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    List<Integer> seeds =
        Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Integer::valueOf).toList();
    Map<String, GardenerMap> gardenerMaps = new HashMap<>();
    List<Integer> locations = new ArrayList<>();
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
    this.parse(gardenerMaps.values());
    for (Integer seed : seeds) {
      Integer value = 0;
      for (String map : MAPS) {
        logger.info("current map is {}", map);
        GardenerMap gardenerMap = gardenerMaps.get(map);
        if (value == 0) {
          Integer nextVal = gardenerMap.getValue(seed);
          value = nextVal == null ? seed : nextVal;
          continue;
        }
        Integer nextVal = gardenerMap.getValue(value);
        value = nextVal == null ? value : nextVal;
      }
      locations.add(value);
    }
    logger.debug("locations are {}", locations);
    logger.debug(
        "Answer for part one is {}", locations.stream().reduce(Integer.MAX_VALUE, Integer::min));
  }

  private void parse(Collection<GardenerMap> gardenerMaps) {
    for (GardenerMap map : gardenerMaps) {
      map.parseLines();
    }
  }

  private class GardenerMap {
    private final String name;
    private final List<Line> lines;
    private final Map<Integer, Integer> mappedValues;

    private GardenerMap(String name) {
      this.name = name;
      this.lines = new ArrayList<>();
      this.mappedValues = new HashMap<>();
    }

    public void addLine(String line) {
      this.lines.add(new Line(line));
    }

    public String getName() {
      return this.name;
    }

    public Integer getValue(Integer val) {
      return this.mappedValues.get(val);
    }

    public void parseLines() {
      for (Line line : lines) {
        Integer sourceNum = line.getSourceNum();
        Integer destNum = line.getDestNum();
        for (int i = 0; i < line.getRangeLength(); i++) {
          this.mappedValues.put(sourceNum, destNum);
          sourceNum++;
          destNum++;
        }
      }
      logger.info("mapping name is {}", this.name);
      logger.info("mappedValues are {}", this.mappedValues);
    }
  }

  private class Line {
    private Integer destNum;
    private Integer sourceNum;
    private Integer rangeLength;

    private Line(String line) {
      List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::valueOf).toList();
      this.destNum = numbers.get(0);
      this.sourceNum = numbers.get(1);
      this.rangeLength = numbers.get(2);
    }

    public Integer getRangeLength() {
      return this.rangeLength;
    }

    public Integer getSourceNum() {
      return this.sourceNum;
    }

    public Integer getDestNum() {
      return this.destNum;
    }
  }
}

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

  private static final Logger logger = LoggerFactory.getLogger(Day05Challenge.class);
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

  // Possible solution for part 2
  // seeds: 79 14 55 13
  //
  // seed-to-soil map:
  // 50 98 2
  // 52 50 48
  //
  // soil-to-fertilizer map:
  // 0 15 37
  // 37 52 2
  // 39 0 15
  //
  // fertilizer-to-water map:
  // 49 53 8
  // 0 11 42
  // 42 0 7
  // 57 7 4
  //
  // water-to-light map:
  // 88 18 7
  // 18 25 70
  //
  // light-to-temperature map:
  // 45 77 23
  // 81 45 19
  // 68 64 13
  //
  // temperature-to-humidity map:
  // 0 69 1
  // 1 0 69
  //
  // humidity-to-location map:
  // 60 56 37
  // 56 93 4
  //
  // range = [start, end, displacement]
  // make seed ranges, e.g.:
  // [79, 92, null]
  // ranges for soil are:
  // -> [[98, 99, -48], [50, 97, +2]]
  // overlaps: [[79, 92]]
  // destinations for [79, 92] are: [81, 94]
  // ranges for fertilizer are:
  // -> [[15, 51, -15], [52, 53, -15], [0, 14, +39]]
  // intersects with nothing, so it's still [81, 94]
  // destinations for [81, 94] are: [81, 94] (no change since no overlaps)
  // ranges for water are:
  // -> [[53, 60, -4], [11, 52, -11], [0, 6, +42], [7, 10, +50]]
  // intersects with nothing, so it's still [81, 94]
  // destinations for [81, 94] are: [81, 94] (no change since no overlaps)
  // ranges for light are:
  // -> [[18, 24, +70], [25, 94, -7]]
  // so it intersects with [25, 94]
  // overlaps: [[81, 94]] 
  // destinations for [81, 94] are: [74, 87] 
  // ranges for temperature are:
  // -> [[77, 99, -32], [45, 63, +36], [64, 76, +4]]
  // overlaps: [[74, 76], [77, 87]]
  // destinations for [[74, 76], [77, 87]] are: [[78, 80], [45, 55]]
  // ranges for humidity are:
  // -> [[69, 69, -69], [0, 68, +1]]
  // overlaps: [[78, 80], [45, 55]]
  // destinations for [[78, 80], [45, 55]] are: [[78, 80], [46, 56]] 
  // ranges for location are:
  // -> [[56, 92, +4], [93, 96, -37]]
  // overlaps: [[78, 80], [46, 56]]
  // destinations for [[78, 80], [46, 56]] are: [82, 84], [46, 56]
  // lowest value is 46, so answer is 46

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
      logger.info("current seed is {}", seed);
      Long value = 0L;
      for (String map : MAPS) {
        String currentDest = map.split("to-")[1];
        logger.info("getting value for {}", currentDest);
        GardenerMap gardenerMap = gardenerMaps.get(map);
        if (value == 0) {
          Long nextVal = gardenerMap.getValue(seed);
          value = nextVal == null ? seed : nextVal;
          continue;
        }
        Long nextVal = gardenerMap.getValue(value);
        value = nextVal == null ? value : nextVal;
      }
      logger.debug("Adding value {}", value);
      locations.add(value);
    }
    logger.debug("locations are {}", locations);
    logger.debug("Answer for part one is {}", locations.stream().reduce(Long.MAX_VALUE, Long::min));
    this.executePartTwo(seeds, gardenerMaps);
  }

  private void executePartTwo(List<Long> seeds, List<GardenerMap> gardenerMaps) {
    List<List<Long>> initialSeedRanges = new ArrayList<>();

    for (int i = 0; i < seeds.size(); i++) {
      if (i % 2 == 0) {
        initialSeedRanges.add(new ArrayList<>(List.of(seeds.get(i))));
      } else {
        initialSeedRanges.get(initialSeedRanges.size() - 1).add(seeds.get(i));
      }
    }

    for (List<Long> initialSeedRange : initialSeedRanges) {
      for (String map : MAPS) {
        String currentDest = map.split("to-")[1];
        logger.info("getting value for {}", currentDest);
        GardenerMap gardenerMap = gardenerMaps.get(map);
      }
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
      for (Line line : lines) {
        Long sourceNum = line.getSourceNum();
        Long destNum = line.getDestNum();
        Long rangeLength = line.getRangeLength();

        if (val >= sourceNum && val < sourceNum + rangeLength) {
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

  private class Range {
    private Long start;
    private Long range;
    private Long displacement;

    private Range(Long start, Long range, Long displacement) {
      this.start = start;
      this.range = range;
      this.displacement = displacement;
    }

    private Long getStart() {
      return this.start;
    }

    private Long getRange() {
      return this.range;
    }

    private Long getDisplacement() {
      return this.displacement;
    }
  }
}

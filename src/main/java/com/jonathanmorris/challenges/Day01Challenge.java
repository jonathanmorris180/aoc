package com.jonathanmorris.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jonathanmorris.Challenge;

public class Day01Challenge implements Challenge {

    private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);

    @Override
    public void execute(final List<String> lines) {
        List<Integer> numbers = new ArrayList<>();
        numbers.toString();
        String firstNumber = "";
        String lastNumber = "";
        for (String line : lines) {
            logger.info("Current line is: {}", line);
            List<String> chars = Arrays.asList(line.trim().split(""));
            for (String currentChar : chars) {
                if (isNumber(currentChar)) {
                    firstNumber = currentChar;
                    break;
                }
            }
            for (int i = chars.size() - 1; i >= 0; i--) {
                String currentChar = chars.get(i);
                if (isNumber(currentChar)) {
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
}

package com.jonathanmorris;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String FILE_NAME_PLACEHOLDER = "inputs/dayXX.txt";
    private static final String CLASS_NAME_PLACEHOLDER = "com.jonathanmorris.challenges.DayXXChallenge";

    public static void main(final String... args) {
        logger.info("Running with args: {}", (Object) args);
        String className = CLASS_NAME_PLACEHOLDER.replace("XX", args[0]);

        try {
            String fileName = FILE_NAME_PLACEHOLDER.replace("XX", args[0]);
            logger.info("Reading file: {}", className);
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            Object challenge = Class.forName(className).getConstructor().newInstance();
            logger.info("Executing challenge: {}", className);
            ((Challenge) challenge).execute(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

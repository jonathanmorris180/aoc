package com.jonathanmorris.challenges;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jonathanmorris.Challenge;

public class Day01Challenge implements Challenge {

    private static final Logger logger = LoggerFactory.getLogger(Day01Challenge.class);

    @Override
    public void execute(final List<String> lines) {
        logger.debug("Lines: {}", lines);
    }
}

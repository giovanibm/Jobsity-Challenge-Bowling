package com.giovanimartini.bowlingchallenge.interfaces;

import com.giovanimartini.bowlingchallenge.entity.Game;
import com.giovanimartini.bowlingchallenge.entity.LineThrow;

import java.util.List;

public interface FileParser {
    List<Game> parseToGames(String fileName) throws Exception;

    List<LineThrow> parseFile(String fileName) throws Exception;

    LineThrow parseLine(String line);
}

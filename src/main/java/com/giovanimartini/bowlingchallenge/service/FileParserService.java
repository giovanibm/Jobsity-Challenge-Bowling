package com.giovanimartini.bowlingchallenge.service;

import com.giovanimartini.bowlingchallenge.entity.Frame;
import com.giovanimartini.bowlingchallenge.entity.Game;
import com.giovanimartini.bowlingchallenge.entity.LineThrow;
import com.giovanimartini.bowlingchallenge.interfaces.FileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParserService implements FileParser {
    public List<Game> parseToGames(String fileName) throws Exception {
        List<LineThrow> lineThrows = this.parseFile(fileName);
        List<Game> games = new ArrayList<>();

        for (int i = 0, l = lineThrows.size(); i < l; i++) {
            LineThrow lineThrow = lineThrows.get(i);
            Game game = games.stream()
                    .filter(it -> it.getPlayer().equals(lineThrow.getPlayer()))
                    .findFirst()
                    .orElse(new Game(lineThrow.getPlayer()));

            Frame currentFrame = game.getCurrentFrame();

            if (currentFrame != null) {
                if (currentFrame.isFinished()) {
                    game.addFrame(new Frame(currentFrame.getFrameNumber() + 1, lineThrow.getScore()));
                } else {
                    if (currentFrame.getFrameNumber() < 10 || currentFrame.getSecondThrow() == null) {
                        currentFrame.setSecondThrow(lineThrow.getScore());
                    } else {
                        currentFrame.setThirdThrow(lineThrow.getScore());
                    }
                }
            } else {
                game.addFrame(new Frame(1, lineThrow.getScore()));
            }

            if (games.stream().noneMatch(it -> it.getPlayer().equals(game.getPlayer()))) {
                games.add(game);
            }
        }

        return games;
    }

    public List<LineThrow> parseFile(String fileName) throws Exception {
        List<LineThrow> rolls = new ArrayList<>();

        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {

                try {
                    rolls.add(parseLine(line));
                } catch (NumberFormatException nfr) {
                    throw new Exception("Line '" + line + "' is invalid. " + nfr.getMessage());
                }
            }
        } catch (IOException e) {
            throw new Exception("File is invalid: " + e.getMessage());
        }

        return rolls;
    }

    public LineThrow parseLine(String line) {
        String[] lineParts = line.split(" ");
        String player = lineParts[0];
        String roll = lineParts[lineParts.length - 1];

        int score = 0;
        if (!roll.equals("F")) {
            score = Integer.parseInt(roll);
        }

        return new LineThrow(player, score);
    }
}

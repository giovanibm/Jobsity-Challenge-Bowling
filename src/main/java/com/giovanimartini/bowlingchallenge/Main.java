package com.giovanimartini.bowlingchallenge;

import com.giovanimartini.bowlingchallenge.entity.Game;
import com.giovanimartini.bowlingchallenge.service.FileParserService;
import com.giovanimartini.bowlingchallenge.service.ScoreService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        FileParserService fileParserService = new FileParserService();
        List<Game> games = fileParserService.parseToGames("input1.txt");

        games.forEach(game -> {
            ScoreService scoreService = new ScoreService(game);
            scoreService.computeGameScore();
            scoreService.printScore(games.indexOf(game) == 0);
        });
    }
}
package com.giovanimartini.bowlingchallenge.entity;

public class LineThrow {
    private String player;
    private int score;

    public LineThrow(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return this.player;
    }

    public int getScore() {
        return this.score;
    }
}

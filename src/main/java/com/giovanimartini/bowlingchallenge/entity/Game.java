package com.giovanimartini.bowlingchallenge.entity;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String player;
    private List<Frame> frames;

    public Game(String player) {
        this.player = player;
        this.frames = new ArrayList<>();
    }

    public String getPlayer() {
        return this.player;
    }

    public List<Frame> getFrames() {
        return this.frames;
    }

    public void addFrame(Frame frame) throws Exception {
        if (this.frames.size() == 10)
            throw new Exception("Game can't have more than ten frames");

        this.frames.add(frame);
    }

    public Frame getCurrentFrame() {
        if (this.frames.isEmpty())
            return null;
        return this.frames.get(this.frames.size() - 1);
    }
}

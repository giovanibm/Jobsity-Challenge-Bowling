package com.giovanimartini.bowlingchallenge.entity;

public class Frame {
    private int number;
    private Integer firstThrow;
    private Integer secondThrow;
    private Integer thirdThrow;
    private Integer score;

    public Frame(int number, int firstRoll) {
        this.number = number;
        this.firstThrow = firstRoll;
    }

    public int getFrameNumber() {
        return this.number;
    }

    public Integer getFirstThrow() {
        return this.firstThrow;
    }

    public void setSecondThrow(int pins) {
        this.secondThrow = pins;
    }

    public Integer getSecondThrow() {
        return this.secondThrow;
    }

    public void setThirdThrow(int pins) throws Exception {
        if (number == 10 && (this.firstThrow + this.secondThrow >= 10))
            this.thirdThrow = pins;
        else
            throw new Exception("Third throw is only available at tenth frame when you have a Spare or Strike.");
    }

    public Integer getThirdThrow() {
        return this.thirdThrow;
    }

    public boolean isFinished() {
        return (this.isStrike() || this.isOpen()) && this.number < 10;
    }

    public boolean isOpen() {
        return this.firstThrow != null && this.secondThrow != null;
    }

    public boolean isSpare() {
        if (this.firstThrow != null && this.secondThrow != null)
            return this.firstThrow + this.secondThrow == 10;
        return false;
    }

    public boolean isStrike() {
        return this.firstThrow != null && this.firstThrow == 10;
    }

    public int getPinsKnockedDown() {
        int pinsKnockedDown = 0;
        if (this.isOpen()) {
            pinsKnockedDown = this.firstThrow + this.secondThrow;
        }
        return pinsKnockedDown;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getScore() {
        return this.score;
    }
}

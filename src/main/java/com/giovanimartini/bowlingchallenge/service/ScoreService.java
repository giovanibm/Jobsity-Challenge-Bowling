package com.giovanimartini.bowlingchallenge.service;

import com.giovanimartini.bowlingchallenge.entity.Frame;
import com.giovanimartini.bowlingchallenge.entity.Game;

public class ScoreService {
    private Game game;

    public ScoreService(Game game) {
        this.game = game;
    }

    public void computeGameScore() {
        this.game.getFrames().forEach(this::computeFrameScore);
    }

    public void computeFrameScore(Frame frame) {
        if (frame.getFrameNumber() == 10) {
            computeLastFrameScore(frame);
        } else {
            if (frame.isSpare()) {
                computeSpareFrame(frame);
            } else if (frame.isStrike()) {
                computeStrikeFrame(frame);
            } else {
                computeOpenFrame(frame);
            }
        }
    }

    private void computeLastFrameScore(Frame frame) {
        int previousScore = getPreviousScore(frame);
        Integer thirdThrow = frame.getThirdThrow();
        frame.setScore(previousScore + frame.getPinsKnockedDown() + (thirdThrow != null ? thirdThrow : 0));
    }

    private void computeSpareFrame(Frame frame) {
        int previousScore = getPreviousScore(frame);
        Frame nextFrame = this.getFrameByNumber(frame.getFrameNumber() + 1);
        frame.setScore(previousScore + 10 + nextFrame.getFirstThrow());
    }

    private void computeStrikeFrame(Frame frame) {
        int previousScore = getPreviousScore(frame);
        Frame nextFrame = this.getFrameByNumber(frame.getFrameNumber() + 1);

        if (nextFrame.getFrameNumber() < 10) {
            if (nextFrame.isSpare()) {
                frame.setScore(previousScore + 10 + 10);

            } else if (nextFrame.isStrike()) {
                Frame secondNextFrame = this.getFrameByNumber(frame.getFrameNumber() + 2);
                frame.setScore(previousScore + 10 + 10 + secondNextFrame.getFirstThrow());

            } else {
                frame.setScore(previousScore + 10 + nextFrame.getPinsKnockedDown());
            }
        } else {
            frame.setScore(previousScore + 10 + nextFrame.getPinsKnockedDown());
        }
    }

    private void computeOpenFrame(Frame frame) {
        int previousScore = getPreviousScore(frame);
        frame.setScore(previousScore + frame.getPinsKnockedDown());
    }

    private int getPreviousScore(Frame frame) {
        int score = 0;
        if (frame.getFrameNumber() > 1) {
            score = getFrameByNumber(frame.getFrameNumber() - 1).getScore();
        }
        return score;
    }

    private Frame getFrameByNumber(int frameNumber) {
        return game.getFrames()
                .stream()
                .filter(it -> it.getFrameNumber() == frameNumber)
                .findFirst()
                .get();
    }

    public void printScore(boolean printFrameNumbers) {
        StringBuilder frames = new StringBuilder();
        StringBuilder pinfalls = new StringBuilder();
        StringBuilder score = new StringBuilder();

        frames.append("Frame\t\t");
        pinfalls.append(game.getPlayer() + "\nPinfalls\t");
        score.append("Score\t\t");

        game.getFrames().forEach(it -> {
            frames.append(it.getFrameNumber() + "\t\t");

            if (it.getFrameNumber() < 10) {
                if (it.isStrike()) {
                    pinfalls.append("X\t\t");
                } else if (it.isSpare()) {
                    pinfalls.append(it.getFirstThrow() + "\t\\\t");
                } else {
                    pinfalls.append(it.getFirstThrow() + "\t" + it.getSecondThrow() + "\t");

                    if (it.getFrameNumber() == 10) {
                        pinfalls.append(it.getThirdThrow());
                    }
                }
            } else {
                if (it.getFirstThrow() == 10) {
                    pinfalls.append("X\t");
                } else {
                    pinfalls.append(it.getFirstThrow() + "\t");
                }

                if (it.getSecondThrow() == 10) {
                    pinfalls.append("X\t");
                } else if (it.getFirstThrow() != 10 && it.getFirstThrow() + it.getSecondThrow() == 10) {
                    pinfalls.append("\\\t");
                } else {
                    pinfalls.append(it.getSecondThrow() + "\t");
                }

                if (it.getThirdThrow() != null) {
                    if (it.getThirdThrow() == 10) {
                        pinfalls.append("X\t");
                    } else if (it.getSecondThrow() != 10 && it.getSecondThrow() + it.getThirdThrow() == 10) {
                        pinfalls.append("\\\t");
                    } else {
                        pinfalls.append(it.getThirdThrow() + "\t");
                    }
                } else {
                    pinfalls.append("-");
                }
            }

            score.append(it.getScore() + "\t\t");
        });

        if (printFrameNumbers) {
            System.out.println(frames);
        }

        System.out.println(pinfalls);
        System.out.println(score);
    }
}

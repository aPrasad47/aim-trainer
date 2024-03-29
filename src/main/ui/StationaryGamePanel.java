package ui;

/*
StationaryGamePanel class: represents the game ui when playing the stationary game
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class StationaryGamePanel extends GamePanel implements Runnable {
    private MouseHandler mouseH = new MouseHandler();
    private StationaryGameInfoBarPanel infoBar = new StationaryGameInfoBarPanel(this);
    private Thread stationaryGameThread;
    private JFrame stationaryGameWindow;

    int framesPerSecond = 60;

    // MODIFIES: this
    // EFFECTS: constructs a StationaryGamePanel with a stationaryGameWindow, and calls super(true), then starts the
    //          game thread
    public StationaryGamePanel(JFrame stationaryGameWindow) {
        super(false);
        this.stationaryGameWindow = stationaryGameWindow;
        this.isMovingGame = false;
        this.add(infoBar, BorderLayout.NORTH);
        this.addMouseListener(mouseH);

        startGameThread();
    }

    // MODIFIES: stationaryGameThread
    // EFFECTS: starts the game thread
    @Override
    public void startGameThread() {
        stationaryGameThread = new Thread(this);
        stationaryGameThread.start();
    }

    // EFFECTS: runs the game loop
    @Override
    public void run() {
        double drawInterval = 1000000000 / framesPerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        initializeRound();
        generateRandomStationaryTarget();

        while (stationaryGameThread != null && lives != 0) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                performUpdates();
                repaint();
                delta--;
            }
        }

        displayGameOverScreen();
    }

    // MODIFIES: infoBar
    // EFFECTS: performs all updates of the game state per each tick
    public void performUpdates() {
        update();
        updateAccuracy();
        infoBar.updateAccuracyLabel(accuracy);
        infoBar.updateScoreLabel(score);
    }

    // MODIFIES: stationaryGameWindow
    // EFFECTS: displays a game over screen by constructing a new GameOverScreen
    @Override
    public void displayGameOverScreen() {
        stationaryGameWindow.dispose();
        new GameOverScreen(score, accuracy, this);
    }

    // MODIFIES: mouseH
    // EFFECTS: updates the game state per each tick of the game
    public void update() {
        List<Target> copiedTargets = new ArrayList<>(targets.getTargetsArray());

        handleMouseClick(copiedTargets);

        for (Target target : copiedTargets) {
            if (!target.isHit()) {
                if (targetShouldIncrease(target)) {
                    target.incrementTargetSize();
                    if (target.getTargetSize() >= gameWindowConstants.getMaxTargetRadius()) {
                        target.setShouldIncreaseSize(false);
                    }
                } else if (targetShouldDecrease(target)) {
                    target.decrementTargetSize();
                    if (target.getTargetSize() <= gameWindowConstants.getMinTargetRadius()) {
                        targets.removeTargetAtIndex(targets.getIndex(target));
                        generateRandomStationaryTarget();
                    }
                }
            }
        }

        mouseH.setIsMouseClicked(false);
    }

    // EFFECTS: returns true if the target should increase, false otherwise
    public boolean targetShouldIncrease(Target target) {
        return target.getShouldIncreaseSize() && target.getTargetSize() < gameWindowConstants.getMaxTargetRadius();
    }

    // EFFECTS: returns true if the target should decrease, false otherwise
    public boolean targetShouldDecrease(Target target) {
        return !target.getShouldIncreaseSize() && target.getTargetSize() > gameWindowConstants.getMinTargetRadius();
    }

    // MODIFIES: infoBar
    // EFFECTS: handles mouse click
    public void handleMouseClick(List<Target> targets) {
        if (mouseH.getIsMouseClicked()) {
            for (Target target : targets) {
                if (mouseClickHitsTarget(target)) {
                    handleHit(target);
                    break;
                } else {
                    decrementLives();
                    infoBar.updateLivesLabel(lives);
                    handleMiss(target);
                }
            }
        }
    }

    // EFFECTS: returns true if the mouse click hits a target, false otherwise
    public boolean mouseClickHitsTarget(Target target) {
        int targetRadius = target.getTargetSize();
        int targetCenterX = target.getPosition().getX();
        int targetCenterY = target.getPosition().getY();

        int clickX = mouseH.getMouseClickX();
        int clickY = mouseH.getMouseClickY();

        int distanceSquared = (clickX - targetCenterX) * (clickX - targetCenterX)
                + (targetCenterY - clickY) * (targetCenterY - clickY);

        int maxDistanceSquared = targetRadius * targetRadius;

        return distanceSquared <= maxDistanceSquared;
    }

    // MODIFIES: targets
    // EFFECTS: generates a random stationary target
    public void generateRandomStationaryTarget() {
        int gameWindowWidth = gameWindowConstants.getGameWindowWidth();
        int gameWindowHeight = gameWindowConstants.getGameWindowHeight();
        int maxTargetSize = gameWindowConstants.getMaxLives();

        Target target = new Target(generateRandomBoundedPosition(gameWindowWidth, gameWindowHeight), false);
        targets.addTarget(target);

        handleTargetSizeChange(target, maxTargetSize);
    }
}

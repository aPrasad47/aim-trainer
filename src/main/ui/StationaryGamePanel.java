package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class StationaryGamePanel extends GamePanel implements Runnable {
    private MouseHandler mouseH = new MouseHandler();
    private StationaryGameInfoBarPanel infoBar = new StationaryGameInfoBarPanel();
    private Thread stationaryGameThread;
    private JFrame stationaryGameWindow;

    int framesPerSecond = 60;

    public StationaryGamePanel(JFrame stationaryGameWindow) {
        super(false);
        this.stationaryGameWindow = stationaryGameWindow;
        this.isMovingGame = false;
        this.add(infoBar, BorderLayout.NORTH);
        this.addMouseListener(mouseH);

        startGameThread();
    }

    @Override
    public void startGameThread() {
        stationaryGameThread = new Thread(this);
        stationaryGameThread.start();
    }

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

    public void performUpdates() {
        update();
        updateAccuracy();
        infoBar.updateAccuracyLabel(accuracy);
        infoBar.updateScoreLabel(score);
    }

    @Override
    public void displayGameOverScreen() {
        stationaryGameWindow.dispose();
        new GameOverScreen(score, accuracy, this);
    }

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

    public boolean targetShouldIncrease(Target target) {
        return target.getShouldIncreaseSize() && target.getTargetSize() < gameWindowConstants.getMaxTargetRadius();
    }

    public boolean targetShouldDecrease(Target target) {
        return !target.getShouldIncreaseSize() && target.getTargetSize() > gameWindowConstants.getMinTargetRadius();
    }

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

    public void generateRandomStationaryTarget() {
        int gameWindowWidth = gameWindowConstants.getGameWindowWidth();
        int gameWindowHeight = gameWindowConstants.getGameWindowHeight();
        int maxTargetSize = gameWindowConstants.getMaxLives();

        Target target = new Target(generateRandomBoundedPosition(gameWindowWidth, gameWindowHeight), false);
        targets.addTarget(target);

        handleTargetSizeChange(target, maxTargetSize);
    }
}

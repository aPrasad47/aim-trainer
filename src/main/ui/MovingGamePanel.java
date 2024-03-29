package ui;

/*
MovingGamePanel class: represents the game ui when playing the moving game
 */

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovingGamePanel extends GamePanel implements Runnable {
    private MouseHandler mouseH = new MouseHandler();
    private MovingGameInfoBarPanel infoBar = new MovingGameInfoBarPanel(this);
    private Thread movingGameThread;
    private JFrame movingGameWindow;

    private int framesPerSecond = 60;

    // MODIFIES: this
    // EFFECTS: constructs a MovingGamePanel with a movingGameWindow, and calls super(true), then starts the game thread
    public MovingGamePanel(JFrame movingGameWindow) {
        super(true);
        this.movingGameWindow = movingGameWindow;
        this.isMovingGame = true;
        this.add(infoBar, BorderLayout.NORTH);
        this.addMouseListener(mouseH);

        startGameThread();
    }

    // MODIFIES: movingGameThread
    // EFFECTS: starts the game thread
    @Override
    public void startGameThread() {
        movingGameThread = new Thread(this);
        movingGameThread.start();
    }

    // EFFECTS: runs the game loop
    @Override
    public void run() {
        double drawInterval = 1000000000 / framesPerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        initializeRound();
        generateRandomMovingTarget();

        while (movingGameThread != null && lives != 0) {
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

    // MODIFIES: movingGameWindow
    // EFFECTS: displays a game over screen by constructing a new GameOverScreen
    @Override
    public void displayGameOverScreen() {
        movingGameWindow.dispose();
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
                        generateRandomMovingTarget();
                    }
                }
            }
        }

        paintMovingTargetsAfterUpdate(copiedTargets);
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

    // EFFECTS: paints moving targets after update
    public void paintMovingTargetsAfterUpdate(List<Target> targets) {
        for (Target target : targets) {
            updateMovingTargetPosition(target);
            if (targetHitsBounds(target)) {
                handleBoundaryHit(target);
                repaint();
            }
        }
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

    // EFFECTS: updates a moving target's position
    public void updateMovingTargetPosition(Target target) {
        int deltaX = target.getSpeedX();
        int deltaY = target.getSpeedY();

        int newX = target.getPosition().getX() + deltaX;
        int newY = target.getPosition().getY() + deltaY;

        target.setPosition(new Position(newX, newY));
    }

    // EFFECTS: returns true if a target collides with the screen's bounds, false otherwise
    public boolean targetHitsBounds(Target target) {
        int targetPositionX = target.getPosition().getX();
        int targetPositionY = target.getPosition().getY();
        int targetRadius = target.getTargetSize();

        boolean isLeftBoundHitX = targetPositionX - targetRadius <= 0;
        boolean isRightBoundHitX = targetPositionX + targetRadius >= gameWindowConstants.getGameWindowWidth();
        boolean isTopBoundHitY = targetPositionY - targetRadius <= 50;
        boolean isBottomBoundHitY = targetPositionY + targetRadius >= (gameWindowConstants.getGameWindowHeight() - 1);

        return isLeftBoundHitX || isRightBoundHitX || isTopBoundHitY || isBottomBoundHitY;
    }

    // EFFECTS: handles a target that collides with a screen boundary
    public void handleBoundaryHit(Target target) {
        int targetPositionX = target.getPosition().getX();
        int targetPositionY = target.getPosition().getY();
        int targetRadius = target.getTargetSize();
        int windowWidth = gameWindowConstants.getGameWindowWidth();
        int windowHeight = gameWindowConstants.getGameWindowHeight();

        int speedX = target.getSpeedX();
        int speedY = target.getSpeedY();

        if (targetPositionX - targetRadius <= 0 || targetPositionX + targetRadius >= windowWidth) {
            target.setSpeedX(-1 * speedX);
            updateMovingTargetPosition(target);
        } else if (targetPositionY - targetRadius <= 50 || targetPositionY + targetRadius >= (windowHeight - 1)) {
            target.setSpeedY(-1 * speedY);
            updateMovingTargetPosition(target);
        }
    }

    // MODIFIES: targets
    // EFFECTS: generates a random moving target
    public void generateRandomMovingTarget() {
        int gameWindowWidth = gameWindowConstants.getGameWindowWidth();
        int gameWindowHeight = gameWindowConstants.getGameWindowHeight();
        int maxTargetSize = gameWindowConstants.getMaxTargetRadius();

        Target target = new Target(generateRandomBoundedPosition(gameWindowWidth, gameWindowHeight), true);
        targets.addTarget(target);

        handleTargetSizeChange(target, maxTargetSize);
    }
}

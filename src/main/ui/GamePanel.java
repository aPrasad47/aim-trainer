package ui;

/*
GamePanel class: represents an abstract game panel class with all the attributes of a game
 */

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class GamePanel extends JPanel {
    protected Targets targets;
    protected HitTargets hitTargets;
    protected NonHitTargets nonHitTargets;
    protected int score;
    protected int hitAttempts;
    protected int successfulHits;
    protected double accuracy;
    protected boolean isMovingGame;
    protected String name;

    protected GameWindowConstants gameWindowConstants = new GameWindowConstants();

    protected int lives = gameWindowConstants.getMaxLives();

    // MODIFIES: this
    // EFFECTS: constructs a GamePanel with isMovingGame to denote the type of game, and initializes layout, size, and
    //          double buffered
    public GamePanel(boolean isMovingGame) {
        this.isMovingGame = isMovingGame;

        this.setLayout(new BorderLayout());
        this.setSize(gameWindowConstants.getGameWindowWidth(), gameWindowConstants.getGameWindowHeight());
        this.setDoubleBuffered(true);
    }

    // for parsing in JsonReader
    // MODIFIES: this
    // EFFECTS: constructs a GamePanel with isMovingGame to denote the type of game, and initializes layout, size, and
    //          double buffered
    public GamePanel(Targets targets, HitTargets hitTargets, NonHitTargets nonHitTargets, int score, int hitAttempts,
                     int successfulHits, double accuracy, boolean isMovingGame, String name) {
        this.targets = targets;
        this.hitTargets = hitTargets;
        this.nonHitTargets = nonHitTargets;
        this.score = score;
        this.hitAttempts = hitAttempts;
        this.successfulHits = successfulHits;
        this.accuracy = accuracy;
        this.isMovingGame = isMovingGame;
        this.name = name;

        this.setLayout(new BorderLayout());
        this.setSize(gameWindowConstants.getGameWindowWidth(), gameWindowConstants.getGameWindowHeight());
        this.setDoubleBuffered(true);
    }

    public abstract void startGameThread();

    // EFFECTS: initializes round
    public void initializeRound() {
        targets = new Targets();
        hitTargets = new HitTargets();
        nonHitTargets = new NonHitTargets();
        score = 0;
        hitAttempts = 0;
        successfulHits = 0;
        accuracy = 100.0;
    }

    // EFFECTS: generates and returns a random bounded position, with x bounded by maxX, and y bounded by maxY
    public Position generateRandomBoundedPosition(int maxX, int maxY) {
        int targetRadius = gameWindowConstants.getMaxTargetRadius();
        int minX = targetRadius;
        int minY = targetRadius + 50;
        int maxXVisible = maxX - targetRadius;
        int maxYVisible = maxY - targetRadius;

        int x = ThreadLocalRandom.current().nextInt(minX, maxXVisible + 1);
        int y = ThreadLocalRandom.current().nextInt(minY, maxYVisible + 1);

        return new Position(x, y);
    }

    public abstract void displayGameOverScreen();

    // MODIFIES: hitTargets, targets
    // EFFECTS: handles a mouse click that hits a target
    public void handleHit(Target target) {
        target.targetHit();
        hitTargets.addTarget(target);
        score++;
        hitAttempts++;
        successfulHits++;
        targets.removeTargetAtIndex(targets.getIndex(target));
        targets.addTarget(new Target(generateRandomBoundedPosition(gameWindowConstants.getGameWindowWidth(),
                gameWindowConstants.getGameWindowHeight()), false));
    }

    // MODIFIES: nonHitTargets, targets
    // EFFECTS: handles a mouse click that hits a target
    public void handleMiss(Target target) {
        target.decrementLifeSpan();
        if (target.getLifeSpan() == 0) {
            nonHitTargets.addTarget(target);
            targets.removeTargetAtIndex(targets.getIndex(target));
        }
        hitAttempts++;
    }

    // EFFECTS: updates accuracy
    public void updateAccuracy() {
        if (hitAttempts == 0) {
            accuracy = 100.0;
        } else {
            accuracy = (double) successfulHits / hitAttempts * 100.0;
        }
    }

    // EFFECTS: paints all non-hit targets on to from
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (Target target : targets.getTargetsArray()) {
            int targetX = target.getPosition().getX();
            int targetY = target.getPosition().getY();
            int radius = target.getTargetSize();

            if (!target.isHit()) {
                g2d.setColor(gameWindowConstants.getTargetColor());
                g2d.fillOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);

                g2d.setColor(gameWindowConstants.getFrameColor());
                g2d.drawOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);
            }
        }
    }

    // EFFECTS: handles the target size change as the game is played
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleTargetSizeChange(Target target, int maxTargetSize) {
        Thread sizeChangeThread = new Thread(() -> {
            int delay = 10000;
            int increment = 2;
            int currentDelay = delay;
            int sizeIncreaseCounter = 0;
            while (!target.isHit() && target.getTargetSize() < maxTargetSize) {
                try {
                    Thread.sleep(currentDelay);
                    sizeIncreaseCounter++;
                    if (sizeIncreaseCounter >= increment) {
                        target.incrementTargetSize();
                        repaint();
                        sizeIncreaseCounter = 0;
                    }
                    currentDelay = delay / (target.getTargetSize() / increment);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!target.isHit()) {
                target.decrementLifeSpan();
            }
        });
        sizeChangeThread.start();
    }

    public void decrementLives() {
        lives--;
    }

    public Targets getTargets() {
        return targets;
    }

    public HitTargets getHitTargets() {
        return hitTargets;
    }

    public NonHitTargets getNonHitTargets() {
        return nonHitTargets;
    }

    public int getScore() {
        return score;
    }

    public int getHitAttempts() {
        return hitAttempts;
    }

    public int getSuccessfulHits() {
        return successfulHits;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean getIsMovingGame() {
        return isMovingGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

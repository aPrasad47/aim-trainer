package model;

/*
Class Target: represents a target with a position,
              isHit, isMoving, the number of attempts to hit,
              and a lifespan, in the game space
 */

import org.json.JSONObject;
import ui.GameWindowConstants;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Target {
    public static final int TARGET_LIFESPAN = 3; // turns

    private Position position;
    private boolean hit;
    private final boolean moving;
    private int numAttemptsToHit;
    private int lifeSpan;
    private int targetSize;
    private boolean shouldIncreaseSize;
    private int speedX;
    private int speedY;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // EFFECTS: constructs a target object with a position, a hit
    //          attribute, a moving attribute, the number of attempts
    //          to hit the target, and a lifespan of the target
    public Target(Position position, boolean moving) {
        Random random = new Random();
        this.position = position;
        hit = false;
        this.moving = moving;
        numAttemptsToHit = 1;
        lifeSpan = TARGET_LIFESPAN;
        targetSize = gameWindowConstants.getMinTargetRadius();
        shouldIncreaseSize = true;
        speedX = random.nextInt(11) - 5;
        speedY = random.nextInt(11) - 5;
    }

    // for HitTargets tests and NonHitTargets tests
    public Target(boolean hit, boolean moving, int numAttemptsToHit) {
        position = new Position(10, 15);
        this.hit = hit;
        this.moving = moving;
        this.numAttemptsToHit = numAttemptsToHit;
        lifeSpan = TARGET_LIFESPAN;
    }

    // for Targets tests
    public Target() {
        position = new Position(10, 15);
        hit = false;
        moving = false;
        numAttemptsToHit = 1;
        lifeSpan = TARGET_LIFESPAN;
    }

    // REQUIRES: moving = true && hit = false
    // MODIFIES: this
    // EFFECTS: moves target to a different position randomly at each turn
    public void move(int maxX, int maxY) {
        setPosition(generateRandomBoundedPosition(maxX, maxY));
    }

    // EFFECTS: returns true if target has collided with (in same position of)
    //          a given target
    public boolean hasCollidedWithTarget(Target target) {
        return position.getX() == target.getPosition().getX()
                && position.getY() == target.getPosition().getY();
    }

    // EFFECTS: returns true if target position is within bounds
    public boolean hasValidPosition(int maxX, int maxY) {
        return position.getX() <= maxX - 1
                && position.getX() >= 0
                && position.getY() <= maxY - 1
                && position.getY() >= 0;
    }

    // REQUIRES: hit = false
    // MODIFIES: this
    // EFFECTS: when target is hit, sets hit to true
    public void targetHit() {
        hit = true;
    }

    // MODIFIES: this
    // EFFECTS: increments numAttemptsToHit
    public void incrementNumAttemptsToHit() {
        numAttemptsToHit++;
    }

    // MODIFIES: this
    // EFFECTS: decrements lifeSpan
    public void decrementLifeSpan() {
        lifeSpan--;
    }

    // MODIFIES: this
    // EFFECTS: sets this.position to position
    public void setPosition(Position position) {
        this.position = position;
    }

    // EFFECTS: returns a randomly generated position that is in the bounds of
    //          the game space
    // Source: SnakeGame, w. laterna library
    public Position generateRandomBoundedPosition(int maxX, int maxY) {
        return new Position(
                ThreadLocalRandom.current().nextInt(maxX),
                ThreadLocalRandom.current().nextInt(maxY)
        );
    }

    public JSONObject targetToJson() {
        JSONObject json = new JSONObject();
        json.put("positionX", position.getX());
        json.put("positionY", position.getY());
        json.put("hit", hit);
        json.put("moving", moving);
        json.put("attemptsToHit", numAttemptsToHit);
        json.put("lifespan", lifeSpan);
        return json;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getNumAttemptsToHit() {
        return numAttemptsToHit;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setNumAttemptsToHit(int numAttemptsToHit) {
        this.numAttemptsToHit = numAttemptsToHit;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public int getTargetSize() {
        return targetSize;
    }

    public void incrementTargetSize() {
        targetSize++;
    }

    public void decrementTargetSize() {
        targetSize--;
    }

    public boolean getShouldIncreaseSize() {
        return shouldIncreaseSize;
    }

    public void setShouldIncreaseSize(boolean shouldIncreaseSize) {
        this.shouldIncreaseSize = shouldIncreaseSize;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}

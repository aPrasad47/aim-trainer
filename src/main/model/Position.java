package model;

/*
Class Position: represents a position in the game space
*/

// Source: SnakeGame w. laterna library

public class Position {
    private int posX;
    private int posY;

    // EFFECTS: construct a Position object with posX and posY
    public Position(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }
}
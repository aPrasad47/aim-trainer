package ui;


import java.awt.*;
import java.util.Random;

public class GameWindowConstants {
    private static Random random = new Random();

    private static int GAME_WINDOW_WIDTH = 750;
    private static int GAME_WINDOW_HEIGHT = 500;
    private static int MAX_TARGET_RADIUS = 75;
    private static int MIN_TARGET_RADIUS = 2;
    private static int MAX_LIVES = 3;
    private static Color TARGET_COLOR = Color.RED;
    private static Color FRAME_COLOR = Color.WHITE;
    private static int MAX_TARGET_SPEED_DX = 5;
    private static int MIN_TARGET_SPEED_DX = -5;
    private static int MAX_TARGET_SPEED_DY = 5;
    private static int MIN_TARGET_SPEED_DY = -5;
    private static int TARGET_SPEED_DX = random.nextInt(MAX_TARGET_SPEED_DX - MIN_TARGET_SPEED_DX + 1)
            - MAX_TARGET_SPEED_DX;
    private static int TARGET_SPEED_DY = random.nextInt(MAX_TARGET_SPEED_DY - MIN_TARGET_SPEED_DY + 1)
            - MAX_TARGET_SPEED_DY;

    public static int getGameWindowWidth() {
        return GAME_WINDOW_WIDTH;
    }

    public static void setGameWindowWidth(int gameWindowWidth) {
        GAME_WINDOW_WIDTH = gameWindowWidth;
    }

    public static int getGameWindowHeight() {
        return GAME_WINDOW_HEIGHT;
    }

    public static void setGameWindowHeight(int gameWindowHeight) {
        GAME_WINDOW_HEIGHT = gameWindowHeight;
    }

    public static int getMaxTargetRadius() {
        return MAX_TARGET_RADIUS;
    }

    public static void setMaxTargetRadius(int maxTargetRadius) {
        MAX_TARGET_RADIUS = maxTargetRadius;
    }

    public static int getMinTargetRadius() {
        return MIN_TARGET_RADIUS;
    }

    public static void setMinTargetRadius(int minTargetRadius) {
        MIN_TARGET_RADIUS = minTargetRadius;
    }

    public static int getMaxLives() {
        return MAX_LIVES;
    }

    public static void setMaxLives(int maxLives) {
        MAX_LIVES = maxLives;
    }

    public static Color getTargetColor() {
        return TARGET_COLOR;
    }

    public static void setTargetColor(Color targetColor) {
        TARGET_COLOR = targetColor;
    }

    public static Color getFrameColor() {
        return FRAME_COLOR;
    }

    public static void setFrameColor(Color frameColor) {
        FRAME_COLOR = frameColor;
    }

    public static int getTargetSpeedDx() {
        return TARGET_SPEED_DX;
    }

    public static void setTargetSpeedDx(int minTargetSpeedDx, int maxTargetSpeedDx) {
        MIN_TARGET_SPEED_DX = minTargetSpeedDx;
        MAX_TARGET_SPEED_DX = maxTargetSpeedDx;
        TARGET_SPEED_DX = random.nextInt(MAX_TARGET_SPEED_DX - MIN_TARGET_SPEED_DX + 1) - MAX_TARGET_SPEED_DX;
    }

    public static int getTargetSpeedDy() {
        return TARGET_SPEED_DY;
    }

    public static void setTargetSpeedDy(int minTargetSpeedDy, int maxTargetSpeedDy) {
        MIN_TARGET_SPEED_DY = minTargetSpeedDy;
        MAX_TARGET_SPEED_DY = maxTargetSpeedDy;
        TARGET_SPEED_DY = random.nextInt(MAX_TARGET_SPEED_DY - MIN_TARGET_SPEED_DY + 1) - MAX_TARGET_SPEED_DY;
    }

    public static int getMaxTargetSpeedDx() {
        return MAX_TARGET_SPEED_DX;
    }

    public static void setMaxTargetSpeedDx(int maxTargetSpeedDx) {
        MAX_TARGET_SPEED_DX = maxTargetSpeedDx;
    }

    public static int getMinTargetSpeedDx() {
        return MIN_TARGET_SPEED_DX;
    }

    public static void setMinTargetSpeedDx(int minTargetSpeedDx) {
        MIN_TARGET_SPEED_DX = minTargetSpeedDx;
    }

    public static int getMaxTargetSpeedDy() {
        return MAX_TARGET_SPEED_DY;
    }

    public static void setMaxTargetSpeedDy(int maxTargetSpeedDy) {
        MAX_TARGET_SPEED_DY = maxTargetSpeedDy;
    }

    public static int getMinTargetSpeedDy() {
        return MIN_TARGET_SPEED_DY;
    }

    public static void setMinTargetSpeedDy(int minTargetSpeedDy) {
        MIN_TARGET_SPEED_DY = minTargetSpeedDy;
    }
}

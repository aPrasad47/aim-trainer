package ui;

/*
DisplayHitTargetsPanel class: represents a panel that displays all the hit targets
 */

import model.Target;

import javax.swing.*;
import java.awt.*;

public class DisplayHitTargetsPanel extends JPanel {
    private GamePanel gamePanel;
    private int maxTargetSize;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // EFFECTS: constructs a DisplayHitTargetsPanel, with a GamePanel and a maxTargetSize
    public DisplayHitTargetsPanel(GamePanel gamePanel, int maxTargetSize) {
        this.gamePanel = gamePanel;
        this.maxTargetSize = maxTargetSize;
        this.setLayout(new BorderLayout());
        this.setSize(gameWindowConstants.getGameWindowWidth(), gameWindowConstants.getGameWindowHeight());
        this.setDoubleBuffered(true);
    }

    // EFFECTS: paints targets on to frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (maxTargetSize == 0) {
            displayAllHitTargets(g2d);
        } else {
            displayFilteredHitTargets(g2d);
        }
    }

    // EFFECTS: paints (displays) all hit targets on to frame
    public void displayAllHitTargets(Graphics2D g2d) {
        for (Target hitTarget : gamePanel.getHitTargets().getTargetsArray()) {
            int targetX = hitTarget.getPosition().getX();
            int targetY = hitTarget.getPosition().getY();
            int radius = hitTarget.getTargetSize();

            drawTarget(g2d, targetX, targetY, radius);
        }
    }

    // EFFECTS: paints (displays) all hit targets on to frame, filtered by radius <= maxTargetSize
    public void displayFilteredHitTargets(Graphics2D g2d) {
        for (Target hitTarget : gamePanel.getHitTargets().getTargetsArray()) {
            int targetX = hitTarget.getPosition().getX();
            int targetY = hitTarget.getPosition().getY();
            int radius = hitTarget.getTargetSize();

            if (radius <= maxTargetSize) {
                drawTarget(g2d, targetX, targetY, radius);
            }
        }
    }

    // EFFECTS: draws target given an x position, y position, and radius, and (x,y) is the center of the target
    public void drawTarget(Graphics2D g2d, int targetX, int targetY, int radius) {
        g2d.setColor(GameWindowConstants.getTargetColor());
        g2d.fillOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);
        g2d.setColor(GameWindowConstants.getFrameColor());
        g2d.drawOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);
    }
}

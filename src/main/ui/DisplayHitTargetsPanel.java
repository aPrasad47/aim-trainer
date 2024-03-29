package ui;

import model.Target;

import javax.swing.*;
import java.awt.*;

public class DisplayHitTargetsPanel extends JPanel {
    private GamePanel gamePanel;
    private int maxTargetSize;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    public DisplayHitTargetsPanel(GamePanel gamePanel, int maxTargetSize) {
        this.gamePanel = gamePanel;
        this.maxTargetSize = maxTargetSize;
        this.setLayout(new BorderLayout());
        this.setSize(gameWindowConstants.getGameWindowWidth(), gameWindowConstants.getGameWindowHeight());
        this.setDoubleBuffered(true);
    }

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

    public void displayAllHitTargets(Graphics2D g2d) {
        for (Target hitTarget : gamePanel.getHitTargets().getTargetsArray()) {
            int targetX = hitTarget.getPosition().getX();
            int targetY = hitTarget.getPosition().getY();
            int radius = hitTarget.getTargetSize();

            drawTarget(g2d, targetX, targetY, radius);
        }
    }

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

    public void drawTarget(Graphics2D g2d, int targetX, int targetY, int radius) {
        g2d.setColor(GameWindowConstants.getTargetColor());
        g2d.fillOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);
        g2d.setColor(GameWindowConstants.getFrameColor());
        g2d.drawOval(targetX - radius, targetY - radius, 2 * radius, 2 * radius);
    }
}

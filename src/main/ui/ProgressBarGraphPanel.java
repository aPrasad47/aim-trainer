package ui;

/*
ProgressBarGraphPanel class: represents the bar graph displayed when a user wants to see their progress over the saved
                             sessions
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProgressBarGraphPanel extends JPanel {
    private List<GamePanel> sessions;

    // MODIFIES: this
    // EFFECTS: constructs a ProgressBarGraphPanel, with a list of sessions
    public ProgressBarGraphPanel(List<GamePanel> sessions) {
        this.sessions = sessions;
        this.setSize(InitializationConstants.WINDOW_WIDTH, InitializationConstants.WINDOW_HEIGHT + 70);
    }

    // EFFECTS: paints the bar graph
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int barWidth = InitializationConstants.WINDOW_WIDTH / sessions.size();

        for (int i = 0; i < sessions.size(); i++) {
            GamePanel session = sessions.get(i);
            int calculatedValue = (int) ((session.getScore() + session.getAccuracy()) / 2) * 5;
            int barHeight = calculatedValue;
            int x = i * barWidth;
            int y = InitializationConstants.WINDOW_HEIGHT - barHeight;

            if (session.getIsMovingGame() == true) {
                g2d.setColor(Color.RED);

            } else {
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x, y, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);

            g2d.drawString(String.valueOf(calculatedValue), x + barWidth / 2 - 12, y - 5);
        }
    }
}

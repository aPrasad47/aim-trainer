package ui;

/*
Statistics class: represents the statistics of the aim trainer of the saved session
 */

import model.EventLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statistics implements ActionListener {
    private JFrame statisticsWindow;

    private JButton viewProgressButton;
    private JButton viewPreviousSessionButton;
    private JButton returnToMainMenuButton;

    // MODIFIES: statisticsWindow
    // EFFECTS: constructs a Statistics, and initializes the statistics window, the statistics window buttons, and adds
    //          the statistics window buttons to the statistics window
    public Statistics() {
        initializeStatisticsWindow();
        initializeStatisticsButtons();
        addStatisticsButtonsToStatisticsWindow();

        statisticsWindow.setVisible(true);
    }

    // MODIFIES: statisticsWindow
    // EFFECTS: initializes the statistics window
    public void initializeStatisticsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        statisticsWindow = new JFrame("Aim Trainer - Statistics");
        statisticsWindow.setSize(windowWidth, windowHeight);
        statisticsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        statisticsWindow.setLocationRelativeTo(null);
        statisticsWindow.setLayout(null);
        statisticsWindow.setResizable(false);
        statisticsWindow.addWindowListener(new CloseWindowHandler(EventLog.getInstance()));
    }

    // MODIFIES: viewProgressButton, viewPreviousSessionButton, returnToMainMenuButton
    public void initializeStatisticsButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        viewProgressButton = new JButton("View Progress (10 Sessions)");
        viewPreviousSessionButton = new JButton("View Previous Session Stats");
        returnToMainMenuButton = new JButton("Return to Main Menu");

        viewProgressButton.setBounds((windowWidth - buttonWidth) / 2, 50, buttonWidth, buttonHeight);
        viewProgressButton.addActionListener(this);

        viewPreviousSessionButton.setBounds((windowWidth - buttonWidth) / 2, 120, buttonWidth, buttonHeight);
        viewPreviousSessionButton.addActionListener(this);

        returnToMainMenuButton.setBounds((windowWidth - buttonWidth) / 2, 190, buttonWidth, buttonHeight);
        returnToMainMenuButton.addActionListener(this);
    }

    // MODIFIES: statisticsWindow
    // EFFECTS: adds statistics window buttons to statistics window
    public void addStatisticsButtonsToStatisticsWindow() {
        statisticsWindow.add(viewProgressButton);
        statisticsWindow.add(viewPreviousSessionButton);
        statisticsWindow.add(returnToMainMenuButton);
    }

    // MODIFIES: statisticsWindow
    // EFFECTS: if viewProgressButton is pressed, disposes statisticsWindow, and constructs a new
    //          ViewProgress,
    //          if viewPreviousSessionButton is pressed, disposes statisticsWindow, and constructs a new
    //          ViewPreviousStats,
    //          if returnToMainMenuButton is pressed, disposes statisticsWindow, and constructs a new
    //          MainMenu,
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProgressButton) {
            statisticsWindow.dispose();
            JOptionPane.showMessageDialog(statisticsWindow,
                    "For your information, red bars correspond to moving games, "
                            + "blue bars correspond to stationary games, " + "and the value is the average "
                            + "of score and accuracy of session.");
            new ViewProgress();
        } else if (e.getSource() == viewPreviousSessionButton) {
            statisticsWindow.dispose();
            new ViewPreviousStats();
        } else if (e.getSource() == returnToMainMenuButton) {
            statisticsWindow.dispose();
            new MainMenu();
        }
    }
}

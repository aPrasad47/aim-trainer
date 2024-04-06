package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CloseWindowHandler implements WindowListener {
    private EventLog eventLog;

    public CloseWindowHandler(EventLog eventLog) {
        this.eventLog = eventLog;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : eventLog) {
            System.out.println(event.getDescription());
        }

        JFrame frame = (JFrame) e.getWindow();
        frame.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

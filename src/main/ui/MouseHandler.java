package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private boolean isMouseClicked = false;
    private int mouseClickX;
    private int mouseClickY;
    private boolean isMouseInsideFrame = false;
    private AimTrainerGUI aimTrainerGUI;

    @Override
    public void mouseClicked(MouseEvent e) {
        isMouseClicked = true;
        mouseClickX = e.getX();
        mouseClickY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseInsideFrame = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseInsideFrame = false;
    }

    public boolean getIsMouseClicked() {
        return isMouseClicked;
    }

    public int getMouseClickX() {
        return mouseClickX;
    }

    public int getMouseClickY() {
        return mouseClickY;
    }

    public boolean getIsMouseInsideFrame() {
        return isMouseInsideFrame;
    }

    public void setIsMouseClicked(boolean isMouseClicked) {
        this.isMouseClicked = isMouseClicked;
    }
}

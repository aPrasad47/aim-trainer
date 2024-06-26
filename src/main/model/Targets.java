package model;

/*
Class Targets: represents an arraylist of targets
 */

import java.awt.*;
import java.util.ArrayList;

public class Targets {
    protected ArrayList<Target> targetsArray;

    public Targets() {
        targetsArray = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds target to Targets
    public void addTarget(Target target) {
        String positionX = String.valueOf(target.getPosition().getX());
        String positionY = String.valueOf(target.getPosition().getX());

        String isHit;
        String typeOfTargetsList;

        String position = "(" + positionX + ", " + positionY + ")";

        if (target.isHit()) {
            isHit = "hit";
            typeOfTargetsList = "hit targets array";

            EventLog.getInstance().logEvent(new Event("Added "
                    + isHit + " target at position " + position + " to " + typeOfTargetsList));
        }

        targetsArray.add(target);
    }

    public ArrayList<Target> filterTargets(int maxTargetSize) {
        ArrayList<Target> filteredTargets = new ArrayList<>();

        EventLog.getInstance().logEvent(new Event("Filtered all hit targets without a radius greater than "
                + maxTargetSize));

        for (Target target : targetsArray) {
            int radius = target.getTargetSize();

            if (radius <= maxTargetSize) {
                filteredTargets.add(target);
            }
        }

        return filteredTargets;
    }

    // REQUIRES: targetsArray.size() > 0 && 0 <= index <= targets.size() - 1
    // MODIFIES: this
    // EFFECTS: removes target at index of targets
    public void removeTargetAtIndex(int index) {
        targetsArray.remove(index);
    }

    // EFFECTS: returns whether targets is empty
    public boolean isEmpty() {
        return targetsArray.isEmpty();
    }

    // EFFECTS: returns whether target is contained in targets
    public boolean contains(Target target) {
        return targetsArray.contains(target);
    }

    // EFFECTS: returns the size of targets
    public int size() {
        return targetsArray.size();
    }

    // REQUIRES: targetsArray.contains(target)
    // EFFECTS: returns the index of the target in targetsArray
    public int getIndex(Target target) {
        return targetsArray.indexOf(target);
    }

    public ArrayList<Target> getTargetsArray() {
        return targetsArray;
    }

}

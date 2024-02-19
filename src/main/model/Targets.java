package model;

/*
Class Targets: represents an arraylist of targets
 */

import java.util.ArrayList;

public class Targets {
    protected ArrayList<Target> targetsArray;

    public Targets() {
        targetsArray = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds target to Targets
    public void addTarget(Target target) {
        targetsArray.add(target);
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

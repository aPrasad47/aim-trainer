package model;

/*
Class NonHitTargets: represents an arraylist of non hit targets
 */

public class NonHitTargets extends Targets {

    // EFFECTS: constructs a NonHitTargets object
    public NonHitTargets() {
        super();
    }

    // REQUIRES: nonHitTargets.size() > 0
    // EFFECTS: returns the total score that was not achieved
    public int totalScoreNotAchieved() {
        int totalScoreNotAchieved = 0;
        for (int i = 0; i < targetsArray.size(); i++) {
            totalScoreNotAchieved++;
        }
        return totalScoreNotAchieved;
    }
}

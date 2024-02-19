package model;

/*
Class HitTargets: represents an arraylist of hit targets
 */

public class HitTargets extends Targets {

    // EFFECTS: constructs a HitTargets object
    public HitTargets() {
        super();
    }

    // REQUIRES: hitTargets.size() > 0
    // EFFECTS: returns the average number of attempts to hit
    //          a target throughout the game
    public double averageNumAttemptsToHitTarget() {
        double totalNumAttemptsToHitTargets = 0.0;
        for (Target t : targetsArray) {
            totalNumAttemptsToHitTargets += t.getNumAttemptsToHit();
        }
        return totalNumAttemptsToHitTargets / targetsArray.size();
    }

    // REQUIRES: hitTargets.size() > 0
    // EFFECTS: returns the total score
    public int totalScore() {
        int totalScore = 0;
        for (int i = 0; i < targetsArray.size(); i++) {
            totalScore++;
        }
        return totalScore;
    }
}

package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.AimTrainerConsole;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads AimTrainerConsole from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads AimTrainerConsole from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AimTrainerConsole read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAimTrainerConsole(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AimTrainerConsole from JSON object and returns it
    private AimTrainerConsole parseAimTrainerConsole(JSONObject jsonObject) {
        Targets targets = addTargets(jsonObject);
        HitTargets hitTargets = addHitTargets(jsonObject);
        NonHitTargets nonHitTargets = addNonHitTargets(jsonObject);
        int score = addScore(jsonObject);
        int hitAttempts = addHitAttempts(jsonObject);
        int successfulHits = addSuccessfulHits(jsonObject);
        double accuracy = addAccuracy(jsonObject);
        String name = addName(jsonObject);
        boolean isMovingGame = addIsMovingGame(jsonObject);
        return new AimTrainerConsole(targets, hitTargets, nonHitTargets,
                score, hitAttempts, successfulHits, accuracy, name, isMovingGame);
    }

    // MODIFIES: this
    // EFFECTS: parses targets from JSON object and adds it to AimTrainerConsole
    private Targets addTargets(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("targets");
        Targets targets = new Targets();
        for (Object json : jsonArray) {
            JSONObject toBeConvertedToTarget = (JSONObject) json;
            Target target = convertToTarget(toBeConvertedToTarget);
            targets.addTarget(target);
        }
        return targets;
    }

    // MODIFIES: this
    // EFFECTS: parses is-moving-game from JSON object and adds it to AimTrainerConsole
    private boolean addIsMovingGame(JSONObject jsonObject) {
        boolean isMovingGame = jsonObject.getBoolean("is-moving-game");
        return isMovingGame;
    }

    // MODIFIES: this
    // EFFECTS: parses hit-attempts from JSON object and adds it to AimTrainerConsole
    private int addHitAttempts(JSONObject jsonObject) {
        int hitAttempts = jsonObject.getInt("hit-attempts");
        return hitAttempts;
    }

    // MODIFIES: this
    // EFFECTS: parses successful-hits from JSON object and adds it to AimTrainerConsole
    private int addSuccessfulHits(JSONObject jsonObject) {
        int successfulHits = jsonObject.getInt("successful-hits");
        return successfulHits;
    }

    // MODIFIES: this
    // EFFECTS: parses score from JSON object and adds it to AimTrainerConsole
    private int addScore(JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        return score;
    }

    // MODIFIES: this
    // EFFECTS: parses accuracy from JSON object and adds it to AimTrainerConsole
    private double addAccuracy(JSONObject jsonObject) {
        double accuracy = jsonObject.getDouble("accuracy");
        return accuracy;
    }

    // MODIFIES: this
    // EFFECTS: parses hit-targets from JSON object and adds it to AimTrainerConsole
    private HitTargets addHitTargets(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("hit-targets");
        HitTargets hitTargets = new HitTargets();
        for (Object json : jsonArray) {
            JSONObject toBeConvertedToHitTarget = (JSONObject) json;
            Target hitTarget = convertToHitTarget(toBeConvertedToHitTarget);
            hitTargets.addTarget(hitTarget);
        }
        return hitTargets;
    }

    // MODIFIES: this
    // EFFECTS: parses name from JSON object and adds it to AimTrainerConsole
    private String addName(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        return name;
    }

    // MODIFIES: this
    // EFFECTS: parses non-hit-targets from JSON object and adds it to AimTrainerConsole
    private NonHitTargets addNonHitTargets(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("non-hit-targets");
        NonHitTargets nonHitTargets = new NonHitTargets();
        for (Object json : jsonArray) {
            JSONObject toBeConvertedToNonHitTarget = (JSONObject) json;
            Target nonHitTarget = convertToNonHitTarget(toBeConvertedToNonHitTarget);
            nonHitTargets.addTarget(nonHitTarget);
        }
        return nonHitTargets;
    }

    // EFFECTS: parses target from JSON object and adds it to AimTrainerConsole
    private Target convertToTarget(JSONObject jsonObject) {
        int positionX = jsonObject.getInt("positionX");
        int positionY = jsonObject.getInt("positionY");
        boolean hit = jsonObject.getBoolean("hit");
        boolean moving = jsonObject.getBoolean("moving");
        int numAttemptsToHit = jsonObject.getInt("attemptsToHit");
        int lifespan = jsonObject.getInt("lifespan");
        Target returnTarget = new Target(new Position(positionX, positionY), moving);
        returnTarget.setNumAttemptsToHit(numAttemptsToHit);
        returnTarget.setLifeSpan(lifespan);
        return returnTarget;
    }

    // EFFECTS: parses hit target from JSON object and adds it to AimTrainerConsole
    private Target convertToHitTarget(JSONObject jsonObject) {
        int positionX = jsonObject.getInt("positionX");
        int positionY = jsonObject.getInt("positionY");
        boolean hit = jsonObject.getBoolean("hit");
        boolean moving = jsonObject.getBoolean("moving");
        int numAttemptsToHit = jsonObject.getInt("attemptsToHit");
        int lifespan = jsonObject.getInt("lifespan");
        Target returnHitTarget = new Target(new Position(positionX, positionY), moving);
        returnHitTarget.setNumAttemptsToHit(numAttemptsToHit);
        returnHitTarget.targetHit();
        returnHitTarget.setLifeSpan(lifespan);
        return returnHitTarget;
    }

    // EFFECTS: parses non hit target from JSON object and adds it to AimTrainerConsole
    private Target convertToNonHitTarget(JSONObject jsonObject) {
        int positionX = jsonObject.getInt("positionX");
        int positionY = jsonObject.getInt("positionY");
        boolean hit = jsonObject.getBoolean("hit");
        boolean moving = jsonObject.getBoolean("moving");
        int numAttemptsToHit = jsonObject.getInt("attemptsToHit");
        int lifespan = jsonObject.getInt("lifespan");
        Target returnNonHitTarget = new Target(new Position(positionX, positionY), moving);
        returnNonHitTarget.setNumAttemptsToHit(numAttemptsToHit);
        returnNonHitTarget.setLifeSpan(lifespan);
        return returnNonHitTarget;
    }

}

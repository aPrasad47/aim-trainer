package ui;

/*
Class AimTrainerConsole: represents the aim trainer in the console
 */

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class AimTrainerConsole implements Writable {
    private static final int MAX_X = 2;
    private static final int MAX_Y = 2;
    private static final String JSON_STORE = "./data/aimTrainerConsole.json";
    private Scanner input;
    private Targets targets;
    private HitTargets hitTargets;
    private NonHitTargets nonHitTargets;
    private int score;
    private int hitAttempts;
    private int successfulHits;
    private double accuracy;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String name;
    private boolean isMovingGame;

    public AimTrainerConsole() {

    }

    public AimTrainerConsole(Targets targets, HitTargets hitTargets, NonHitTargets nonHitTargets, int score,
                            int hitAttempts, int successfulHits, double accuracy, String name, boolean isMovingGame) {
        this.targets = targets;
        this.hitTargets = hitTargets;
        this.nonHitTargets = nonHitTargets;
        this.score = score;
        this.hitAttempts = hitAttempts;
        this.successfulHits = successfulHits;
        this.accuracy = accuracy;
        this.name = name;
        this.isMovingGame = isMovingGame;
    }

    // MODIFIES: this
    // EFFECTS: runs the aim trainer application and processes the user's input
    public void runAimTrainer() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        String command;
        initializeRound();

        displayMenu();
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("q")) {
            System.out.println("\nGoodbye!");
        } else {
            processGameMode(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command to select game mode
    private void processGameMode(String command) {
        if (command.equals("m")) {
            playMovingGame(this);
            isMovingGame = true;
        } else if (command.equals("s")) {
            playStationaryGame(this);
            isMovingGame = false;
        } else if (command.equals("sq")) {
            saveSession();
            quitGame();
        } else if (command.equals("lr")) {
            resumeSessionFromSave();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the game round
    private void initializeRound() {
        targets = new Targets();
        hitTargets = new HitTargets();
        nonHitTargets = new NonHitTargets();
        score = 0;
        hitAttempts = 0;
        successfulHits = 0;
        accuracy = 100.0;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to the Aim Trainer!");
        System.out.println("\nSelect your game mode:");
        System.out.println("\tm -> moving targets");
        System.out.println("\ts -> stationary targets");
        System.out.println("\tsq -> save aim training session and quit");
        System.out.println("\tlr -> load and resume previous aim training session");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: saves the aim training session to file and quits
    private void saveSession() {
        try {
            String sessionName = nameSession(this);
            jsonWriter.open();
            //jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved " + sessionName + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads and resumes the previous aim training session
    public void resumeSessionFromSave() { //load the session, then
        try {
            AimTrainerConsole atc = jsonReader.readAimTrainerConsole();
            System.out.println("Loaded " + atc.getName() + " from " + JSON_STORE);
            if (isMovingGame) {
                playMovingGame(atc);
            } else {
                playStationaryGame(atc);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: names session and returns name
    private String nameSession(AimTrainerConsole atc) {
        System.out.println("What would you like to name this session?");
        String command = input.next();
        atc.setName(command);
        return command;
    }

    // MODIFIES: this
    // EFFECTS: plays the aim trainer with moving targets
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void playMovingGame(AimTrainerConsole atc) {
        generateRandomMovingTargets();
        boolean quit = false;
        while (!atc.getTargets().isEmpty() && !quit) {
            atc.updateAccuracy();
            System.out.println("\n Score: " + atc.getScore() + ", Accuracy: " + atc.getAccuracy());
            System.out.println("\nSelect a position (x, y) to shoot (0 <= x <= " + (MAX_X - 1)
                    + ", 0 <= y <= " + (MAX_Y - 1) + "), press s to save, press q to quit, "
                    + "or press sq to save and quit:");
            System.out.println("x:");
            if (input.hasNextInt()) {
                int coordX = input.nextInt();
                System.out.println("y:");
                if (input.hasNextInt()) {
                    int coordY = input.nextInt();
                    if (validPosition(coordX, coordY)) {
                        Position targetPosition = new Position(coordX, coordY);
                        atc.shootMoving(targetPosition);
                    } else {
                        System.out.println("\nPosition not valid...");
                    }
                } else {
                    String quitCommand = input.next().toLowerCase();
                    if (quitCommand.equals("q")) {
                        quit = true;
                    } else if (quitCommand.equals("s")) {
                        saveSession();
                    } else if (quitCommand.equals("sq")) {
                        saveSession();
                        quit = true;
                    } else {
                        System.out.println("\nInvalid input. Please enter integer coordinates, 's' to save, 'q' to "
                                + "quit, or 'sq' to save and quit");
                    }
                }
            } else {
                String quitCommand = input.next().toLowerCase();
                if (quitCommand.equals("q")) {
                    quit = true;
                } else if (quitCommand.equals("s")) {
                    saveSession();
                } else if (quitCommand.equals("sq")) {
                    saveSession();
                    quit = true;
                } else {
                    System.out.println("\nInvalid input. Please enter integer coordinates, 's' to save, 'q' to "
                            + "quit, or 'sq' to save and quit");
                }
            }
            input.nextLine();
        }
        atc.quitGame();
    }

    // MODIFIES: this
    // EFFECTS: plays the aim trainer with stationary targets
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void playStationaryGame(AimTrainerConsole atc) {
        generateRandomStationaryTargets();
        boolean quit = false;
        while (!atc.getTargets().isEmpty() && !quit) {
            atc.updateAccuracy();
            System.out.println("\n Score: " + atc.getScore() + ", Accuracy: " + atc.getAccuracy());
            System.out.println("\nSelect a position (x, y) to shoot (0 <= x <= " + (MAX_X - 1)
                    + ", 0 <= y <= " + (MAX_Y - 1) + "), press s to save, press q to quit, "
                    + "or press sq to save and quit:");
            System.out.println("x:");
            if (input.hasNextInt()) {
                int coordX = input.nextInt();
                System.out.println("y:");
                if (input.hasNextInt()) {
                    int coordY = input.nextInt();
                    if (validPosition(coordX, coordY)) {
                        Position targetPosition = new Position(coordX, coordY);
                        atc.shootStationary(targetPosition);
                    } else {
                        System.out.println("\nPosition not valid...");
                    }
                } else {
                    String quitCommand = input.next().toLowerCase();
                    if (quitCommand.equals("q")) {
                        quit = true;
                    } else if (quitCommand.equals("s")) {
                        saveSession();
                    } else if (quitCommand.equals("sq")) {
                        saveSession();
                        quit = true;
                    } else {
                        System.out.println("\nInvalid input. Please enter integer coordinates, 's' to save, 'q' to "
                                + "quit, or 'sq' to save and quit");
                    }
                }
            } else {
                String quitCommand = input.next().toLowerCase();
                if (quitCommand.equals("q")) {
                    quit = true;
                } else if (quitCommand.equals("s")) {
                    saveSession();
                } else if (quitCommand.equals("sq")) {
                    saveSession();
                    quit = true;
                } else {
                    System.out.println("\nInvalid input. Please enter integer coordinates, 's' to save, 'q' to "
                            + "quit, or 'sq' to save and quit");
                }
            }
            input.nextLine();
        }
        atc.quitGame();
    }



    // EFFECTS: presents the end-of-game statistics
    private void quitGame() {
        System.out.println("\nQuitting the game...");
        System.out.println("\nGame over!");
        System.out.println("\nYour score was " + finalScore() + ".");
        System.out.println("\nYour accuracy was " + getAccuracy() + ".");
    }

    // MODIFIES: this
    // EFFECTS: attempts to shoot the moving target
    private void shootMoving(Position position) {
        if (shootTarget(position)) {
            System.out.println("Hit!");
            handleHit(position);
            moveTargets();
        } else {
            System.out.println("Miss!");
            handleMiss();
            moveTargets();
        }
    }

    // MODIFIES: this
    // EFFECTS: attempts to shoot the stationary target
    private void shootStationary(Position position) {
        if (shootTarget(position)) {
            System.out.println("Hit!");
            handleHit(position);
        } else {
            System.out.println("Miss!");
            handleMiss();
        }
    }

    // EFFECTS: returns a randomly generated position that is in the bounds of
    //          the game space
    // Source: SnakeGame, w. laterna library
    private Position generateRandomBoundedPosition(int maxX, int maxY) {
        return new Position(
                ThreadLocalRandom.current().nextInt(maxX),
                ThreadLocalRandom.current().nextInt(maxY)
        );
    }

    // EFFECTS: returns if the position with x and y as coordinates
    //          is a valid position
    private boolean validPosition(int x, int y) {
        return x >= 0 && x <= MAX_X - 1 && y >= 0 && y <= MAX_Y - 1;
    }

    // EFFECTS: returns true if there is target that has been hit
    private boolean shootTarget(Position position) {
        for (Target t : targets.getTargetsArray()) {
            if (t.getPosition().getX() == position.getX()
                    && t.getPosition().getY() == position.getY()) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: handles the hit of the target
    private void handleHit(Position position) {
        List<Target> copiedTargets = new ArrayList<>(targets.getTargetsArray());
        // ensure that the modifications to the original array does not interfere with the iteration
        for (Target t : copiedTargets) {
            if (t.getPosition().getX() == position.getX()
                    && t.getPosition().getY() == position.getY()) {
                t.targetHit();
                hitTargets.addTarget(t);
                incrementScore();
                incrementHitAttempts();
                incrementSuccessfulHits();
                targets.removeTargetAtIndex(targets.getIndex(t));
                targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), true));
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the miss
    private void handleMiss() {
        List<Target> copiedTargets = new ArrayList<>(targets.getTargetsArray());
        // ensure that the modifications to the original array doesn't interfere with the iteration
        for (Target missedTarget : copiedTargets) {
            missedTarget.decrementLifeSpan();
            if (missedTarget.getLifeSpan() == 0) {
                nonHitTargets.addTarget(missedTarget);
                targets.removeTargetAtIndex(targets.getIndex(missedTarget));
            }
        }
        incrementHitAttempts();
        updateAccuracy();
    }

    // MODIFIES: this
    // EFFECTS: moves the targets
    private void moveTargets() {
        for (Target t : targets.getTargetsArray()) {
            t.move(MAX_X, MAX_Y);
        }
    }

    // EFFECTS: generates three moving targets at a random position in the
    //          bounds of the game
    private void generateRandomMovingTargets() {
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), true));
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), true));
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), true));
    }

    // EFFECTS: generates three stationary targets at a random position in the
    //          bounds of the game
    private void generateRandomStationaryTargets() {
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), false));
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), false));
        targets.addTarget(new Target(generateRandomBoundedPosition(MAX_X, MAX_Y), false));
    }

    // EFFECTS: returns the total final score at the end of the game
    private String finalScore() {
        return String.valueOf(hitTargets.totalScore());
    }

    // MODIFIES: this
    // EFFECTS: updates the accuracy after each shot
    private void updateAccuracy() {
        if (hitAttempts == 0) {
            accuracy = 100.0;
        } else {
            accuracy = (double) successfulHits / hitAttempts * 100.0;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("targets", targetsToJson());
        json.put("hit-targets", hitTargetsToJson());
        json.put("non-hit-targets", nonHitTargetsToJson());
        json.put("score", score);
        json.put("hit-attempts", hitAttempts);
        json.put("successful-hits", successfulHits);
        json.put("accuracy", accuracy);
        json.put("name", name);
        json.put("is-moving-game", isMovingGame);
        return json;
    }

    private JSONArray targetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target t : targets.getTargetsArray()) {
            jsonArray.put(t.targetToJson());
        }
        return jsonArray;
    }

    private JSONArray hitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : hitTargets.getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }

    private JSONArray nonHitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : nonHitTargets.getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }

    public void incrementHitAttempts() {
        hitAttempts++;
    }

    public void incrementSuccessfulHits() {
        successfulHits++;
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public Targets getTargets() {
        return targets;
    }

    public HitTargets getHitTargets() {
        return hitTargets;
    }

    public NonHitTargets getNonHitTargets() {
        return nonHitTargets;
    }

    public int getHitAttempts() {
        return hitAttempts;
    }

    public int getSuccessfulHits() {
        return successfulHits;
    }

    public boolean getIsMovingGame() {
        return isMovingGame;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setHitAttempts(int hitAttempts) {
        this.hitAttempts = hitAttempts;
    }

    public void setSuccessfulHits(int successfulHits) {
        this.successfulHits = successfulHits;
    }

    public void setTargets(Targets targets) {
        this.targets = targets;
    }

    public void setHitTargets(HitTargets hitTargets) {
        this.hitTargets = hitTargets;
    }

    public void setNonHitTargets(NonHitTargets nonHitTargets) {
        this.nonHitTargets = nonHitTargets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

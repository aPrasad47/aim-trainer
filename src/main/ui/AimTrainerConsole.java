package ui;

/*
Class AimTrainerConsole: represents the aim trainer in the console
 */

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class AimTrainerConsole {
    private static final int MAX_X = 51;
    private static final int MAX_Y = 51;
    private Scanner input;
    private Targets targets;
    private HitTargets hitTargets;
    private NonHitTargets nonHitTargets;
    private int score;
    private int hitAttempts;
    private int successfulHits;
    private double accuracy;

    // EFFECTS: runs the aim trainer application
    public AimTrainerConsole() {
        runAimTrainer();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input
    private void runAimTrainer() {
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
            playMovingGame();
        } else if (command.equals("s")) {
            playStationaryGame();
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
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: plays the aim trainer with moving targets
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void playMovingGame() {
        generateRandomMovingTargets();
        boolean quit = false;
        while (!targets.isEmpty() && !quit) {
            updateAccuracy();
            System.out.println("\n Score: " + getScore() + ", Accuracy: " + getAccuracy());
            System.out.println("\nSelect a position (x, y) to shoot (0 <= x <= " + (MAX_X - 1)
                    + ", 0 <= y <= " + (MAX_Y - 1) + "), or press q to quit:");
            System.out.println("x:");
            if (input.hasNextInt()) {
                int coordX = input.nextInt();
                System.out.println("y:");
                if (input.hasNextInt()) {
                    int coordY = input.nextInt();
                    if (validPosition(coordX, coordY)) {
                        Position targetPosition = new Position(coordX, coordY);
                        shootMoving(targetPosition);
                    } else {
                        System.out.println("\nPosition not valid...");
                    }
                } else {
                    String quitCommand = input.next().toLowerCase();
                    if (quitCommand.equals("q")) {
                        quit = true;
                    } else {
                        System.out.println("\nInvalid input. Please enter integer coordinates or 'q' to quit.");
                    }
                }
            } else {
                String quitCommand = input.next().toLowerCase();
                if (quitCommand.equals("q")) {
                    quit = true;
                } else {
                    System.out.println("\nInvalid input. Please enter integer coordinates or 'q' to quit.");
                }
            }
            input.nextLine();
        }
        quitGame();
    }

    // MODIFIES: this
    // EFFECTS: plays the aim trainer with stationary targets
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void playStationaryGame() {
        generateRandomStationaryTargets();
        boolean quit = false;
        while (!targets.isEmpty() && !quit) {
            updateAccuracy();
            System.out.println("\n Score: " + getScore() + ", Accuracy: " + getAccuracy());
            System.out.println("\nSelect a position (x, y) to shoot (0 <= x <= " + (MAX_X - 1)
                    + ", 0 <= y <= " + (MAX_Y - 1) + "), or press q to quit:");
            System.out.println("x:");
            if (input.hasNextInt()) {
                int coordX = input.nextInt();
                System.out.println("y:");
                if (input.hasNextInt()) {
                    int coordY = input.nextInt();
                    if (validPosition(coordX, coordY)) {
                        Position targetPosition = new Position(coordX, coordY);
                        shootStationary(targetPosition);
                    } else {
                        System.out.println("\nPosition not valid...");
                    }
                } else {
                    String quitCommand = input.next().toLowerCase();
                    if (quitCommand.equals("q")) {
                        quit = true;
                    } else {
                        System.out.println("\nInvalid input. Please enter integer coordinates or 'q' to quit.");
                    }
                }
            } else {
                String quitCommand = input.next().toLowerCase();
                if (quitCommand.equals("q")) {
                    quit = true;
                } else {
                    System.out.println("\nInvalid input. Please enter integer coordinates or 'q' to quit.");
                }
            }
            input.nextLine();
        }
        quitGame();
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

    private void incrementHitAttempts() {
        hitAttempts++;
    }

    private void incrementSuccessfulHits() {
        successfulHits++;
    }

    private void incrementScore() {
        score++;
    }

    private int getScore() {
        return score;
    }

    private double getAccuracy() {
        return accuracy;
    }


}

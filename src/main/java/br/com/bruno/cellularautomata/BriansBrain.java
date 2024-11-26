package br.com.bruno.cellularautomata;

import java.awt.Color;
import java.util.Random;

public class BriansBrain implements CellularAutomata<Color[][]> {

    public enum State {
        ON, DYING, OFF;

        static State stateOf(boolean bool) {
            return bool ? ON : OFF;
        }

        boolean toBoolean() {
            return this != OFF;
        }
    }

    private final int height;
    private final int width;
    private State[][] universe;

    private BriansBrain(int height, int width, Random r) {
        this.height = height;
        this.width = width;
        this.universe = new State[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.universe[i][j] = State.stateOf(r.nextBoolean());
            }
        }
    }

    public BriansBrain(int height, int width, long seed) {
        this(height, width, new Random(seed));
    }

    public BriansBrain(int height, int width) {
        this(height, width, new Random());
    }

    @Override
    public Color[][] getUniverse() {
        Color[][] universe = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe[i][j] = switch (this.universe[i][j]) {
                    case ON -> Color.WHITE;
                    case DYING -> Color.BLUE;
                    case OFF -> Color.BLACK;
                };
            }
        }
        return universe;
    }

    @Override
    public void nextGeneration() {
        State[][] universe = new State[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe[i][j] = nextValueOfPosition(i, j);
            }
        }

        this.universe = universe;
    }

    private State nextValueOfPosition(int i, int j) {
//        Mutation
//        if (Math.random() < 0.0001)
//            return State.ON;

//        Brian's Brain Rules:
//        A cell may be in three states: on, dying, off
//        Each cell has eight neighbors (Moore)

//        A cell turn on if it was off but has exactly 2 neighbors that are on
        if (valueOf(i, j) == State.OFF && numberOfOnNeighbors(i, j) == 2)
            return State.ON;

//        All cells that are on go into the dying state
        if (valueOf(i, j) == State.ON)
            return State.DYING;

//        All cells in the dying state turn off
//        and cell in the off state remains off
        return State.OFF;
    }

    private byte numberOfOnNeighbors(int i, int j) {
        byte count = 0;
        State[] neighbors = {
            valueOf(i - 1, j - 1), valueOf(i - 1, j), valueOf(i - 1, j + 1),
            valueOf(i, j - 1),     /* center cell */  valueOf(i, j + 1),
            valueOf(i + 1, j - 1), valueOf(i + 1, j), valueOf(i + 1, j + 1)
        };

        for (State s : neighbors)
            if (s == State.ON) count++;
        return count;
    }

    private State valueOf(int i, int j) {
        if (i < 0)
            i = height - 1;
        else if (i >= height)
            i = 0;

        if (j < 0)
            j = width - 1;
        else if (j >= width)
            j = 0;

        return this.universe[i][j];
    }
}

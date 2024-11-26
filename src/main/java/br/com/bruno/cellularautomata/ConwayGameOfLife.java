package br.com.bruno.cellularautomata;

import java.awt.Color;
import java.util.Random;

public class ConwayGameOfLife implements CellularAutomata<Color[][]> {

    private final int height;
    private final int width;
    private boolean[][] universe;

    private ConwayGameOfLife(int height, int width, Random r) {
        this.height = height;
        this.width = width;
        this.universe = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.universe[i][j] = r.nextBoolean();
            }
        }
    }

    public ConwayGameOfLife(int height, int width, long seed) {
        this(height, width, new Random(seed));
    }

    public ConwayGameOfLife(int height, int width) {
        this(height, width, new Random());
    }

    @Override
    public Color[][] getUniverse() {
        Color[][] universe = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe[i][j] = this.universe[i][j] ? Color.BLACK : Color.WHITE;
            }
        }
        return universe;
    }

    @Override
    public void nextGeneration() {
        boolean[][] universe = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe[i][j] = nextValueOfPosition(i, j);
            }
        }

        this.universe = universe;
    }

    public boolean nextValueOfPosition(int i, int j) {
        if (Math.random() < 0.0005)
            return true;

        byte numberOfNeighbors = numberOfNeighbors(i, j);
//        Conway's Game of Life
        if (valueOf(i, j)) {

//            Any live cell with fewer than two live neighbours dies, as if by underpopulation
            if (numberOfNeighbors < 2)
                return false;

//            Any live cell with two or three live neighbours lives on to the next generation
//            Any live cell with more than three live neighbours dies, as if by overpopulation
            return numberOfNeighbors <= 3;
        }

//        Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction
        return numberOfNeighbors == 3;
    }

    private byte numberOfNeighbors(int i, int j) {
        byte count = 0;
        boolean[] neighbors = {
                valueOf(i - 1, j - 1), valueOf(i - 1, j), valueOf(i - 1, j + 1),
                valueOf(i, j - 1),     /* center cell */  valueOf(i, j + 1),
                valueOf(i + 1, j - 1), valueOf(i + 1, j), valueOf(i + 1, j + 1)
        };

        for (boolean n : neighbors)
            if (n) count++;

        return count;
    }

    private boolean valueOf(int i, int j) {
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

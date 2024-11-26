package br.com.bruno.cellularautomata;

import java.awt.Color;
import java.util.Random;

public class BelousovZhabotinsky implements CellularAutomata<Color[][]> {
    private final int height;
    private final int width;
    private final short g;
    private short[][] universe;

    private BelousovZhabotinsky(int height, int width, Random r, short g) {
        this.height = height;
        this.width = width;
        this.g = g;
        this.universe = new short[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
               if(r.nextInt(100) < 5) {
                   this.universe[i][j] = (short) (r.nextInt(Short.MAX_VALUE / 2) + Short.MAX_VALUE / 2);
               } else {
                   this.universe[i][j] = (short) (r.nextInt(Short.MAX_VALUE));
               }
            }
        }
    }

    public BelousovZhabotinsky(int height, int width, short g, long seed) {
        this(height, width, new Random(seed), g);
    }

    public BelousovZhabotinsky(int height, int width, short g) {
        this(height, width, new Random(), g);
    }

    @Override
    public Color[][] getUniverse() {
        Color[][] universe = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = (int) (this.universe[i][j] / (float) Short.MAX_VALUE * 255);

                if (color < 80) {
                    universe[i][j] = new Color((255 - color), (255 - color), (255 - color));
                } else if (color < 160) {
                    int xolor = (int) (color / (float) 160 * 255);
                    universe[i][j] = new Color(color, xolor, xolor);
                } else {
                    universe[i][j] = new Color(color, color, color);
                }
            }
        }
        return universe;
    }

    @Override
    public void nextGeneration() {
        short[][] universe = new short[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe[i][j] = nextValueOfPosition(i, j);
            }
        }

        this.universe = universe;
    }

    private short nextValueOfPosition(int i, int j) {
//        Belousov-Zhabotinsky Rules:
//        A cell exists in n states (e.g. n == 100)

//        If a cell is infected (at n) it becomes healthy
        if (this.universe[i][j] == Short.MAX_VALUE)
            return 0;

//        If a cell is between 0 and n it becomes the average of the states of its neighbors plus G
        if (this.universe[i][j] > 0){
            int x = averageOfNeighbors(i, j, false) + this.g;
            return x > Short.MAX_VALUE ? 0 : (short) x;
        }

//        If a cell is healthy, it becomes the average os the number of infected/ill neighbors
//        if (this.universe[i][j] == 0)
        return averageOfNeighbors(i, j, true);
    }

    private short averageOfNeighbors(int i, int j, boolean onlyInfected) {
        int valueSum = 0, denominator = 0;
        short[] neighbors = {
                valueOf(i - 1, j - 1), valueOf(i - 1, j), valueOf(i - 1, j + 1),
                valueOf(i, j - 1),     /* center cell */  valueOf(i, j + 1),
                valueOf(i + 1, j - 1), valueOf(i + 1, j), valueOf(i + 1, j + 1)
        };

        for (short value : neighbors){
            if (!onlyInfected || value != 0){
                valueSum += value;
                denominator++;
            }
        }
        return denominator == 0 ? 0 : (short) (valueSum / denominator);
    }

    private short valueOf(int i, int j) {
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

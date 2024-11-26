package br.com.bruno.cellularautomata;

import java.util.Random;

public class OneDimensionalCellularAutomata implements CellularAutomata<boolean[]> {

    private boolean[] universe;

    public OneDimensionalCellularAutomata(boolean[] universe) {
        this.universe = universe;
    }

    private OneDimensionalCellularAutomata(int universeLength, Random r) {
        this.universe = new boolean[universeLength];
        for (int i = 0; i < universeLength; i++) {
            this.universe[i] = r.nextBoolean();
        }
    }

    public OneDimensionalCellularAutomata(int universeLength, long seed) {
        this(universeLength, new Random(seed));
    }

    public OneDimensionalCellularAutomata(int universeLength) {
        this(universeLength, new Random());
    }

    @Override
    public boolean[] getUniverse() {
        return this.universe;
    }

    @Override
    public void nextGeneration() {
        boolean[] nextUniverse = new boolean[this.universe.length];

        for(int i = 0; i < this.universe.length; i++) {
            nextUniverse[i] = nextValueOfIndex(i);
        }

        this.universe = nextUniverse;
    }

    private boolean nextValueOfIndex(int index) {
        boolean left = this.valueOfIndex(index - 1);
        boolean cell = this.valueOfIndex(index);
        boolean right = this.valueOfIndex(index + 1);

        // If cell and neighbors are 1, then 0
        if (cell && right && left)
            return false;

        // If cell and neighbors are 0, then 0
        if (!cell && !right && !left)
            return false;

        // If cell and right neighbor are 0 but left, then 0
        if (!cell && !right && left)
            return false;

        // Otherwise 1
        return true;
    }

    private boolean valueOfIndex(int index) {
        int length = this.universe.length;
        if (index < 0)
            return this.universe[length - 1];
        else if (index >= length)
            return this.universe[0];
        else
            return this.universe[index];
    }

}

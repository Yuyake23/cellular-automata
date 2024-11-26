package br.com.bruno.cellularautomata;

import java.util.function.Consumer;

public interface CellularAutomata<E> {
    E getUniverse();
    void nextGeneration();

}

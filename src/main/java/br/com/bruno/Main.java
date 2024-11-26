package br.com.bruno;

import br.com.bruno.cellularautomata.BelousovZhabotinsky;
import br.com.bruno.cellularautomata.BriansBrain;
import br.com.bruno.cellularautomata.CellularAutomata;
import br.com.bruno.cellularautomata.ConwayGameOfLife;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        +++++++ One-Dimensional Cellular Automata +++++++
//        int length = 300;
//        int gens = 10;
//
//        CellularAutomata<boolean[]> automata = new OneDimensionalCellularAutomata(length, 1);
//
//        String separator = new StringBuilder(length).append('\n').repeat('-', length).toString();
//        Runnable printer = () -> {
//            for (boolean value : automata.getUniverse()) {
//                System.out.print(value ? '#' : ' ');
//            }
//            System.out.println(separator);
//        };
//
//        printer.run();
//        for (int i = 0; i < gens; i++) {
//            automata.nextGeneration();
//            printer.run();
//        }

//        +++++++ Bi-Dimensional Cellular Automata +++++++
        int monitorHeight = 1080;
        int monitorWidth = 1920; // 1920
        double scale = 4;

        int height = (int) (monitorHeight / scale);
        int width = (int) (monitorWidth / scale);

//        BriansBrain automata = new BriansBrain(height, width);
//        BelousovZhabotinsky automata = new BelousovZhabotinsky(height, width, (short) 220); // 180
        ConwayGameOfLife automata = new ConwayGameOfLife(height, width);


        new BiDimensionalCellularAutomataFrame(monitorHeight, monitorWidth, automata, scale);
    }

}
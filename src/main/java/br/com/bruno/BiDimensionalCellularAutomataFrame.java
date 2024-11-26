package br.com.bruno;

import br.com.bruno.cellularautomata.CellularAutomata;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BiDimensionalCellularAutomataFrame extends JFrame {

    public final int height;
    public final int width;
    public final double scale;

    public BiDimensionalCellularAutomataFrame(int height, int width, CellularAutomata<Color[][]> automata, double scale) {
        this.height = height;
        this.width = width;
        this.scale = scale;

        add(new BiDimensionalCellularAutomataPanel(automata));
        setTitle("Cellular Automata");
        setSize(this.width, this.height);
        setExtendedState(MAXIMIZED_BOTH);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false);
        setVisible(true);
    }

    class BiDimensionalCellularAutomataPanel extends JPanel implements ActionListener {

        private final CellularAutomata<Color[][]> automata;

        public BiDimensionalCellularAutomataPanel(CellularAutomata<Color[][]> automata) {
            this.automata = automata;
            this.setFocusable(true);
            this.setDoubleBuffered(true);
            this.setBounds(0, 0, width, height);

            Timer timer = new Timer(1000 / 120, BiDimensionalCellularAutomataPanel.this);
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.automata.nextGeneration();
            this.repaint();
        }

        @Override
        public void paint(Graphics g) {
            Color fillColor = new Color(255, 255, 255, 10);
            g.setColor(fillColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            Graphics2D g2d = (Graphics2D) g;

            g2d.setBackground(Color.BLACK);

            Color[][] universe = this.automata.getUniverse();
            int height = universe.length;
            int width = universe[0].length;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (fillColor.getRed() == universe[i][j].getRed()
                            && fillColor.getGreen() == universe[i][j].getGreen()
                            && fillColor.getBlue() == universe[i][j].getBlue())
                        continue;

                    g2d.setColor(universe[i][j]);
//                    g2d.drawRect((int) (j * scale), (int) (i * scale), (int) Math.ceil(scale), (int) Math.ceil(scale));
                    g2d.fillRect((int) (j * scale), (int) (i * scale), (int) Math.ceil(scale), (int) Math.ceil(scale));
                }
            }

        }
    }
}

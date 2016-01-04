//SynthTiles by Pradyumna Shome - December 15th 2014 - 1730 hours
package SynthTiles;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;

public class SynthTiles implements MouseListener {

    static JFrame frame;
    static JPanel panel;
    static JButton[][] tiles;
    static JButton a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, start;
    static SynthTiles pt;
    static Color lime;
    static Random random;
    static int r, req;
    static long bTime, endTime;

    public static void main(String[] args) {
        lime = new Color(0, 255, 0);
        random = new Random();
        frame = new JFrame("Synth!");
        panel = new JPanel(new GridLayout(4, 4));
        frame.setContentPane(panel);
        frame.setSize(480, 640);
        panel.setBackground(Color.orange);
        frame.setDefaultCloseOperation(3);
        pt = new SynthTiles();
        a = new JButton("");
        b = new JButton("");
        c = new JButton("");
        d = new JButton("");
        e = new JButton("");
        f = new JButton("");
        g = new JButton("");
        h = new JButton("");
        i = new JButton("");
        j = new JButton("");
        k = new JButton("");
        l = new JButton("");
        m = new JButton("");
        n = new JButton("");
        o = new JButton("");
        p = new JButton("");
        start = new JButton("START");
        pt = new SynthTiles();
        tiles = new JButton[4][4];
        tiles[0][0] = a;
        tiles[0][1] = b;
        tiles[0][2] = c;
        tiles[0][3] = d;

        tiles[1][0] = e;
        tiles[1][1] = f;
        tiles[1][2] = g;
        tiles[1][3] = h;

        tiles[2][0] = i;
        tiles[2][1] = j;
        tiles[2][2] = k;
        tiles[2][3] = l;

        tiles[3][0] = m;
        tiles[3][1] = n;
        tiles[3][2] = o;
        tiles[3][3] = p;

        start.setBackground(Color.yellow);
        panel.add(start);
        start.addMouseListener(pt);
        frame.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < 4; i++) {
            if (tiles[3][i].getBackground() == lime) {
                req = i;//this is the index of the button on the 4th row which is lime color. Through a loop, this is stored by checking the bg colors
                // of all the buttons on the 4th row.
            }
        }
        if (e.getSource() == tiles[3][req]) {
            frame.setTitle("Time Per SynthTile is: " + Math.abs(((double) (System.currentTimeMillis() - bTime)) / 1000) + " seconds");
            bTime = System.currentTimeMillis();
            tiles[3][req].setBackground(Color.blue);
            for (int i = 3; 1 <= i; i--) {
                for (int j = 0; j < 4; j++) {
                    if (tiles[i - 1][j].getBackground() == lime) {
                        tiles[i][j].setBackground(lime);
                        tiles[i - 1][j].setBackground(Color.blue);
                    }
                }
            }
            r = random.nextInt(4);
            tiles[0][r].setBackground(lime);
        } else if (e.getSource() == SynthTiles.start) {
            System.out.println("STARTED--Time starts from the moment you click START");
            System.out.println("The time on the title is not the time required to click and release one tile but the time difference between a click on one tile "
                    + "\n and a click on the next(above) tile. That means that if you click one tile and wait for 3 seconds before you click on the next tile"
                    + "the time taken to do the tile will be 3 seconds.");
            setTilesAtBeginning();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tiles[i][j].setVisible(true);
                }
            }
            start.setVisible(false);
            start.setEnabled(false);
            panel.remove(start);
        } else {
            tiles[3][r].setBackground(Color.red);
            tiles[3][r].setText("Game Over!");
            JOptionPane.showConfirmDialog(panel, "Game's Over , bro!!! You can't play any longer.");
            frame.setEnabled(false);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void setTilesAtBeginning() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                frame.getContentPane().add(tiles[i][j]);
                tiles[i][j].addMouseListener(pt);
                tiles[i][j].setBackground(Color.blue);
                tiles[i][j].setVisible(false);
            }
        }
        for (int i = 0; i < 4; i++) {
            r = random.nextInt(4);
            tiles[i][r].setBackground(lime);
        }
        bTime = 0;//Set beginning time=0 before the firest click. After that, bTIme is set after each click.
    }
}

package test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PointsExample extends JFrame {

    public PointsExample() {
        initUI();
    }

    public final void initUI() {

        DrawPanel dpnl = new DrawPanel();
        add(dpnl);

        setSize(250, 200);
        setTitle("Points");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PointsExample ex = new PointsExample();
                ex.setVisible(true);
            }
        });
    }
}
package it.unibo.cloudnine.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class View {

    private final JFrame frame = new JFrame();
    private final JTabbedPane pane = new JTabbedPane();

    public View() {
        frame.pack();
        frame.setSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane.addTab("Dio porco", new JPanel());
        pane.addTab("Dio porco", new JPanel());
        pane.addTab("Dio porco", new JPanel());

        frame.add(pane);

        frame.setVisible(true);
    }
}

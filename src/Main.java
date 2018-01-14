import gui.MainFrame;
import model.Cause;
import model.Graph;
import model.Symptom;

import java.awt.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph("resources/projekt2.xdsl");
        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame(graph);
            ex.setVisible(true);
        });
    }



}

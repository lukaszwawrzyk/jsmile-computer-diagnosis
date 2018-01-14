package gui;

import model.Cause;
import model.Graph;
import model.Symptom;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class MainFrame extends JFrame {

    public MainFrame(Graph graph) {
        setTitle("Computer problems diagnosis");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        QuestionsPanel questions = new QuestionsPanel(graph.getSymptoms());
        add(questions);

        ResultsPanel results = new ResultsPanel(graph);
        add(results);

        pack();
    }

    private static class QuestionsPanel extends JPanel {
        public QuestionsPanel(List<Symptom> symptoms) {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            symptoms.forEach(symptom -> add(new QuestionPanel(symptom)));
        }
    }

    private static class QuestionPanel extends JPanel {
        public QuestionPanel(Symptom symptom) {
            setLayout(new FlowLayout());
            add(new JLabel(symptom.getQuestion()));
            ButtonGroup group = new ButtonGroup();
            symptom.getAnswers().forEach(answer -> {
                JRadioButton option = new JRadioButton(answer);
                option.addActionListener(e -> symptom.setAnswer(answer));
                add(option);
                group.add(option);
            });
        }
    }

    private class ResultsPanel extends JPanel {
        public ResultsPanel(Graph graph) {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            JButton evaluateButton = new JButton("Evaluate");
            add(evaluateButton);
            JTextArea resultsText = new JTextArea(30, 30);
            JScrollPane scrollPane = new JScrollPane(resultsText);
            add(scrollPane);

            evaluateButton.addActionListener(e -> {
                List<Cause> causes = graph.evaluateCauses();
                String summary = causes.stream()
                        .map(cause -> String.format("%s %.3f", cause.getName(), cause.getPositiveProbability()))
                        .collect(joining("\r\n"));
                resultsText.setText(summary);
            });
        }
    }
}

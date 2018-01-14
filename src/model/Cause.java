package model;

import smile.Network;

import java.util.Arrays;
import java.util.List;

public class Cause {

    private static final List<String> POSITIVE_OUTCOMES = Arrays.asList("Tak", "tak", "Wystepuje", "Wykryto");
    private static final List<String> NEGATIVE_OUTCOMES = Arrays.asList("Nie", "nie", "NieWystepuje", "Nie_wykryto", "Brak");

    private final Network net;
    private final String node;
    private final int positiveOutcomeIndex;

    public Cause(Network net, String node) {
        this.net = net;
        this.node = node;
        this.positiveOutcomeIndex = resolvePositiveOutcomeIndex();
    }

    public String getName() {
        return net.getNodeName(node);
    }

    public String getPositiveAnswer() {
        return getAnswers().get(positiveOutcomeIndex);
    }

    private List<String> getAnswers() {
        return Arrays.asList(net.getOutcomeIds(node));
    }

    public double getPositiveProbability() {
        return net.getNodeValue(node)[positiveOutcomeIndex];
    }

    private int resolvePositiveOutcomeIndex() {
        List<String> answers = getAnswers();
        if (answers.size() != 2) throw new AssertionError("Expected 2 answers, found " + answers);
        if (POSITIVE_OUTCOMES.contains(answers.get(0))) {
            if (!NEGATIVE_OUTCOMES.contains(answers.get(1))) throw new AssertionError("Unexpected negative outcome " + answers.get(1));
            return 0;
        }
        if (POSITIVE_OUTCOMES.contains(answers.get(1))) {
            if (!NEGATIVE_OUTCOMES.contains(answers.get(0))) throw new AssertionError("Unexpected negative outcome " + answers.get(0));
            return 1;
        }
        throw new AssertionError("Unexpected set of outcomes " + answers);
    }

    @Override
    public String toString() {
        return getName() + " " + getAnswers();
    }
}

package model;

import smile.Network;

import java.util.Arrays;
import java.util.List;

public class Symptom {

    private final Network net;
    private final String node;

    public Symptom(Network net, String node) {
        this.net = net;
        this.node = node;
    }

    public String getQuestion() {
        return net.getNodeName(node);
    }

    public List<String> getAnswers() {
        return Arrays.asList(net.getOutcomeIds(node));
    }

    public void setAnswer(String answer) {
        net.setEvidence(node, answer);
    }

    @Override
    public String toString() {
        return getQuestion() + " " + getAnswers();
    }
}

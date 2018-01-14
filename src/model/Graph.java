package model;

import smile.Network;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Graph {

    private final Network network;
    private List<Symptom> symptoms;
    private List<Cause> causes;

    public Graph(String fileName) {
        network = initializeNetwork(fileName);
        List<String> allNodes = Arrays.asList(network.getAllNodeIds());
        symptoms = allNodes
                .stream()
                .filter(node -> network.getChildren(node).length == 0)
                .map(node -> new Symptom(network, node))
                .collect(toList());
        causes = allNodes
                .stream()
                .filter(node -> network.getChildren(node).length > 0)
                .map(node -> new Cause(network, node))
                .collect(toList());

    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public List<Cause> evaluateCauses() {
        network.updateBeliefs();
        return causes.stream()
            .sorted(Comparator.comparingDouble(Cause::getPositiveProbability).reversed())
            .collect(toList());
    }

    private Network initializeNetwork(String fileName) {
        System.setProperty("jsmile.native.library", new File("lib/jsmile.dll").getAbsolutePath());
        new smile.License(
                "SMILE LICENSE 434e2eac 1bb0bdbf 31d23440 " +
                        "THIS IS AN ACADEMIC LICENSE AND CAN BE USED  " +
                        "SOLELY FOR ACADEMIC RESEARCH AND TEACHING, " +
                        "AS DEFINED IN THE BAYESFUSION ACADEMIC  " +
                        "SOFTWARE LICENSING AGREEMENT. " +
                        "Serial #: 60ztefmpu0n40zgp5yguq8g6n " +
                        "Issued for: \u0141ukasz Wawrzyk (lukasz.wawrzyk@gmail.com) " +
                        "Academic institution: AGH University of Science and Technology " +
                        "Valid until: 2018-06-20 " +
                        "Issued by BayesFusion activation server",
                new byte[] {
                        -32,-111,76,-39,-122,28,86,66,70,-93,-32,-107,123,127,30,-38,
                        -128,-24,104,-112,14,-86,122,83,-16,-73,80,-91,-46,47,125,10,
                        79,-123,-81,75,74,-23,-128,88,-45,10,-33,95,-19,-86,18,87,
                        -110,-53,48,-36,81,-112,-56,-111,-82,-29,-69,-119,28,-26,77,-23
                }
        );
        Network network = new Network();
        network.readFile(fileName);
        return network;
    }
}

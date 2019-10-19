package EDAPlacementAppPg;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import EDAPlacementAppPg.Model.Circuit;
import EDAPlacementAppPg.Model.MyGraph;
import javafx.util.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "EDAPlacementAppPg.Model.controller")
@EnableWebMvc
public class EDAPlacementApp {
    public static MyGraph<String> graph;

    public static void main(String[] args) throws Exception {
        graph = parseFromFile(Paths.get(Paths.get("").toAbsolutePath() + "/src/input.txt"));
        SpringApplication.run(EDAPlacementApp.class, args);
    }

    public static int[][][] circuit(){
        return BuildCircuit(graph);
    }

    public static MyGraph<String> parseFromFile(Path path) throws Exception {
        Scanner sc = new Scanner(path);
        List<String> vert = new ArrayList<String>();
        List<String> ops = new ArrayList<String>();
        String line;
        HashMap<String, String> vertInp = new HashMap<String, String>();
        while (sc.hasNextLine()) {
            line = sc.nextLine().toLowerCase();
            String[] el = line.split(" ");
            switch (el[0]) {
                case "in":
                case "out":
                    vert.add(el[1]);
                    vertInp.put(el[1], el[1]);
                    break;
                default:
                    String el0 = el[0] + "|" + UUID.randomUUID().toString();
                    vert.add(el0);
                    String result = line.replace(el[0], el0);
                    ops.add(result);
                    break;
            }
        }
        sc.close();

        MyGraph<String> graph = new MyGraph<String>(vert.toArray(new String[vert.size()]), true);
        for (String op : ops) {
            String[] el = op.split(" ");
            switch (el[0].split("\\|")[0]) {
                case "and":
                case "or":
                case "nand":
                case "nor":
                case "xor":
                case "xnor":
                    graph.insertVertex(el[0]);
                    vertInp.put(el[3], el[0]);
                    graph.insertEdge(vertInp.get(el[1]), el[0], 0);
                    graph.insertEdge(vertInp.get(el[2]), el[0], 0);
                    break;
                case "not":
                    graph.insertVertex(el[0]);
                    vertInp.put(el[0], el[1]);
                    graph.insertEdge(vertInp.get(el[1]), el[0], 0);
                    break;
            }
        }
        return graph;
    }

    public static int[][][] BuildCircuit(MyGraph<String> graph) {
        Circuit circuit = new Circuit();
        List<String> vertices = graph.AllVertices();
        ArrayList<Circuit.Element> allElements = new ArrayList<Circuit.Element>();
        LinkedList<Circuit.Element> inputs = new LinkedList<Circuit.Element>();
        LinkedList<Circuit.Element> outputs = new LinkedList<Circuit.Element>();
        LinkedList<Circuit.Element> elements = new LinkedList<Circuit.Element>();
        LinkedList<Pair<Circuit.Element,Circuit.Element>> wires = new LinkedList<Pair<Circuit.Element,Circuit.Element>>();

        for (String vertex:vertices ) {

            Circuit.Element element = circuit.createNew(vertex);
            allElements.add(element);
            HashSet<String> Inputs = graph.getAllInputs(vertex);
            HashSet<String> Outputs = graph.getAllOutputs(vertex);
            if(Inputs.isEmpty())
            {
                element.elementType = Circuit.PointType.Output;
                inputs.add(element);
            }
            else if(Outputs.isEmpty())
            {
                element.elementType = Circuit.PointType.Input;
                outputs.add(element);
            }
            else
            {
                element.elementType = ParseElement(vertex);
                elements.add(element);
            }
        }
        for (String vertex:vertices ) {
            HashSet<String> outs = graph.getAllOutputs(vertex);
            Circuit.Element element = FindeElement(allElements,vertex);
            for (String output : outs) {
                Circuit.Element element2 = FindeElement(allElements,output);
                wires.add(new Pair<>(element,element2));
            }
        }

        circuit.Build(elements,inputs,outputs,wires);
        int[][][] result = circuit.ToArray();

        return result;
    }

    private static Circuit.PointType ParseElement(String element)
    {
        String type = element.substring(0,element.indexOf('|'));
        switch(type)
        {
            case "and": return  Circuit.PointType.And;
            case "or": return  Circuit.PointType.Or;
            case "not": return  Circuit.PointType.Not;
        }
        return  Circuit.PointType.None;
    }

    private static Circuit.Element FindeElement(  ArrayList<Circuit.Element> allElements, String str)
    {
        for(int i = 0; i < allElements.size();i++)
        {
            if(allElements.get(i).name.equals(str))return allElements.get(i);
        }
        return  null;
    }
}

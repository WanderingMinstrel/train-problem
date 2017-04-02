package tests;

import com.graph.TownGraph;
import com.run.TownGraphGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TrainsTest {
    TownGraph townGraph;

    @BeforeEach
    void setUp() {
        FileReader input = null;
        BufferedReader bufferedInput = null;

        try {
            input = new FileReader("src/com/resource/input_graph.txt");
            bufferedInput = new BufferedReader(input);

            townGraph = TownGraphGenerator.createTownGraph(bufferedInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calcDistanceTest() {
        String result = townGraph.calculateDistanceBetweenElements(new String[]{"A","B","C"});
        Assertions.assertEquals("9", result);

        result = townGraph.calculateDistanceBetweenElements(new String[]{"A","D"});
        Assertions.assertEquals("5", result);

        result = townGraph.calculateDistanceBetweenElements(new String[]{"A", "D", "C"});
        Assertions.assertEquals("13", result);

        result = townGraph.calculateDistanceBetweenElements(new String[]{"A", "E", "B", "C", "D"});
        Assertions.assertEquals("22", result);

        result = townGraph.calculateDistanceBetweenElements(new String[]{"A", "E", "D"});
        Assertions.assertEquals("NO SUCH ROUTE", result);

    }

    @Test
    void calcNumTripsTest() {
        //less than or equal to 3 stops
        int result = townGraph.calculateNumTripsByNumStops(false, "C", "C", 3);
        Assertions.assertEquals(2, result);

        // equal to 4 stops
        result = townGraph.calculateNumTripsByNumStops(true,"A", "C", 4);
        Assertions.assertEquals(3, result);

        result = townGraph.calculateNumTripsByNumStops(false,"C", "C", 6);
        Assertions.assertEquals(10, result);

        result = townGraph.calculateNumTripsByNumStops(false,"C", "C", 7);
        Assertions.assertEquals(15, result);

        result = townGraph.calculateNumTripsByNumStops(false,"C", "C", 10);
        Assertions.assertEquals(51, result);


    }

    @Test
    void retrievePathsTest() {
        String[] onePath = {"C", "D", "C"};
        String[] twoPath = {"C", "E", "B", "C"};
        String[] threePath = {"C", "D", "E", "B", "C"};
        String[] fourPath = {"C", "D", "C", "D", "C"};
        List<List<String>> result = townGraph.getPaths(false,"C", "C", 3, new ArrayList<>());
        Assertions.assertArrayEquals(onePath, result.get(0).toArray());
        Assertions.assertArrayEquals(twoPath, result.get(1).toArray());

        System.out.println(result);

        result = townGraph.getPaths(false,"C", "C", 4, new ArrayList<>());
        Assertions.assertArrayEquals(onePath, result.get(0).toArray());
        Assertions.assertArrayEquals(fourPath, result.get(1).toArray());
        Assertions.assertArrayEquals(threePath, result.get(2).toArray());
        Assertions.assertArrayEquals(twoPath, result.get(3).toArray());
        System.out.println(result);

        result = townGraph.getPaths(false,"C", "C", 6, new ArrayList<>());
        Assertions.assertEquals(10, result.size());

        result = townGraph.getPaths(false, "C", "C", 7, new ArrayList<>());
        System.out.println("Second result:");
        for(List<String> one:result) {
            System.out.println(one);
        }
        System.out.println("Size: " + result.size());
    }

    @Test
    void getShortestPathTest() {
        int result = townGraph.getShortestPath("A", "C");
        Assertions.assertEquals(9, result);

        result = townGraph.getShortestPath("B", "B");
        Assertions.assertEquals(9, result);

        result = townGraph.getShortestPath("A", "B");
        Assertions.assertEquals(5, result);

        result = townGraph.getShortestPath("D", "C");
        Assertions.assertEquals(8, result);

        result = townGraph.getShortestPath("C", "C");
        Assertions.assertEquals(9, result);
    }


}
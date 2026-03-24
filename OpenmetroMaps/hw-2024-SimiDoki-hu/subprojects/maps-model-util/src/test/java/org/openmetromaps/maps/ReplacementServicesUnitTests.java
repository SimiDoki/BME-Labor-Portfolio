package org.openmetromaps.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.openmetromaps.maps.model.Coordinate;
import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;
import org.openmetromaps.maps.model.Stop;

import com.google.common.collect.Lists;

public class ReplacementServicesUnitTests {
    ModelData model;

    Station stationA;
    Station stationB;
    Station stationC;

    Line line1;

    /*
     * 1 X----X----X
     *   A    B    C
     */
    /*@Before
    public void createMap() {
        // Arrange

        List<Stop> stationAStops = new ArrayList<>();
        stationA = new Station(0, "A", new Coordinate(47.4891, 19.0614), stationAStops);

        List<Stop> stationBStops = new ArrayList<>();
        stationB = new Station(1, "B", new Coordinate(47.4891, 19.0714), stationBStops);

        List<Stop> stationCStops = new ArrayList<>();
        stationC = new Station(2, "C", new Coordinate(47.4891, 19.0814), stationCStops);

        List<Stop> line1Stops = new ArrayList<>();
        line1 = new Line(3, "1", "#009EE3", false, line1Stops);

        Stop line1AStop = new Stop(stationA, line1);
        stationAStops.add(line1AStop);
        line1Stops.add(line1AStop);

        Stop line1BStop = new Stop(stationB, line1);
        stationBStops.add(line1BStop);
        line1Stops.add(line1BStop);

        Stop line1CStop = new Stop(stationC, line1);
        stationCStops.add(line1CStop);
        line1Stops.add(line1CStop);

        model = new ModelData(new ArrayList<>(List.of(line1)), new ArrayList<>(List.of(stationA, stationB, stationC)));
    }*/

    /*
     * Starting from:
     *
     * 1 X----X----X
     *   A    B    C
     *
     * CLOSE B:
     *
     * 1 X---------X
     *   A         C
     */
    @Test
    public void checkCloseStation() {
        List<Stop> stationAStops = new ArrayList<>();
        stationA = new Station(0, "A", new Coordinate(47.4891, 19.0614), stationAStops);

        List<Stop> stationBStops = new ArrayList<>();
        stationB = new Station(1, "B", new Coordinate(47.4891, 19.0714), stationBStops);

        List<Stop> stationCStops = new ArrayList<>();
        stationC = new Station(2, "C", new Coordinate(47.4891, 19.0814), stationCStops);

        List<Stop> line1Stops = new ArrayList<>();
        line1 = new Line(3, "1", "#009EE3", false, line1Stops);

        Stop line1AStop = new Stop(stationA, line1);
        stationAStops.add(line1AStop);
        line1Stops.add(line1AStop);

        Stop line1BStop = new Stop(stationB, line1);
        stationBStops.add(line1BStop);
        line1Stops.add(line1BStop);

        Stop line1CStop = new Stop(stationC, line1);
        stationCStops.add(line1CStop);
        line1Stops.add(line1CStop);

        model = new ModelData(new ArrayList<>(List.of(line1)), new ArrayList<>(List.of(stationA, stationB, stationC)));
        // Act
        ReplacementServices.closeStation(model, stationB, List.of(line1));

        // Assert
        assertModel(model)
            .hasLines(1)
            .hasExactStations("A", "C")
            .hasLineWithExactStations("1", "A", "C");
    }

    
    /*
     * Starting from:
     *
     * 1 X----X----X
     *   A    B    C
     *
     * ALTERNATIVE A-C:
     *
     * P-1  ---------
     *     |         |
     * 1   X----X----X
     *     A    B    C
     */
    @Test
    public void checkAlternativeService() {
        List<Stop> stationAStops = new ArrayList<>();
        stationA = new Station(0, "A", new Coordinate(47.4891, 19.0614), stationAStops);

        List<Stop> stationBStops = new ArrayList<>();
        stationB = new Station(1, "B", new Coordinate(47.4891, 19.0714), stationBStops);

        List<Stop> stationCStops = new ArrayList<>();
        stationC = new Station(2, "C", new Coordinate(47.4891, 19.0814), stationCStops);

        List<Stop> line1Stops = new ArrayList<>();
        line1 = new Line(3, "1", "#009EE3", false, line1Stops);

        Stop line1AStop = new Stop(stationA, line1);
        stationAStops.add(line1AStop);
        line1Stops.add(line1AStop);

        Stop line1BStop = new Stop(stationB, line1);
        stationBStops.add(line1BStop);
        line1Stops.add(line1BStop);

        Stop line1CStop = new Stop(stationC, line1);
        stationCStops.add(line1CStop);
        line1Stops.add(line1CStop);

        model = new ModelData(new ArrayList<>(List.of(line1)), new ArrayList<>(List.of(stationA, stationB, stationC)));
        // Act
        ReplacementServices.createAlternativeService(model, stationA, stationC);

        // Assert
        assertModel(model)
            .hasLines(2)
            .hasExactStations("A", "B", "C")
            .hasLineWithExactStations("1", "A", "B", "C")
            .hasLineWithExactStations("P-1", "A", "C");
    }

    /*
     * Starting from:
     *
     * 1 X----X----X
     *   A    B    C
     *
     * REPLACE B-C, 1:
     *
     * 1   X----X
     *     A    B
     *
     * P1       X----X
     *          B    C
     */
    @Test
    public void checkReplacement() {
        List<Stop> stationAStops = new ArrayList<>();
        stationA = new Station(0, "A", new Coordinate(47.4891, 19.0614), stationAStops);

        List<Stop> stationBStops = new ArrayList<>();
        stationB = new Station(1, "B", new Coordinate(47.4891, 19.0714), stationBStops);

        List<Stop> stationCStops = new ArrayList<>();
        stationC = new Station(2, "C", new Coordinate(47.4891, 19.0814), stationCStops);

        List<Stop> line1Stops = new ArrayList<>();
        line1 = new Line(3, "1", "#009EE3", false, line1Stops);

        Stop line1AStop = new Stop(stationA, line1);
        stationAStops.add(line1AStop);
        line1Stops.add(line1AStop);

        Stop line1BStop = new Stop(stationB, line1);
        stationBStops.add(line1BStop);
        line1Stops.add(line1BStop);

        Stop line1CStop = new Stop(stationC, line1);
        stationCStops.add(line1CStop);
        line1Stops.add(line1CStop);

        model = new ModelData(new ArrayList<>(List.of(line1)), new ArrayList<>(List.of(stationA, stationB, stationC)));
        // Act
        ReplacementServices.createReplacementService(model, List.of(stationB, stationC), List.of(line1));

        // Assert
        assertModel(model)
            .hasLines(2)
            .hasExactStations("A", "B", "C")
            .hasLineWithExactStations("1", "A", "B")
            .hasLineWithExactStations("P1", "B", "C")
            .hasLine("P1", (line) -> {
                Assert.assertEquals("#009EE3", line.getColor());
                return null;
            });
    }
    @Test
    public void testReplacementServiceBetweenTwoStationsWithMultipleLines() {
    // Initial setup: Lines and Stations
    List<Stop> l1Stops = new ArrayList<>();
    Line line1 = new Line(1, "L1", "#FF0000", false, l1Stops);  // Red line
    List<Stop> l2Stops = new ArrayList<>();
    Line line2 = new Line(2, "L2", "#00FF00", false, l2Stops);  // Green line

    // Stations
    Station stationA = new Station(1, "Station A", new Coordinate(0, 0), new ArrayList<>());
    Station stationB = new Station(2, "Station B", new Coordinate(1, 0), new ArrayList<>());
    Station stationC = new Station(3, "Station C", new Coordinate(2, 0), new ArrayList<>());
    Station stationD = new Station(4, "Station D", new Coordinate(3, 0), new ArrayList<>());
    Station stationE = new Station(5, "Station E", new Coordinate(4, 0), new ArrayList<>());
    
    Stop Al1 = new Stop(stationA, line1);
    Stop Bl1 = new Stop(stationB, line1);
    Stop Cl1 = new Stop(stationC, line1);
    Stop Dl1 = new Stop(stationD, line1);
    Stop Bl2 = new Stop(stationB, line2);
    Stop Cl2 = new Stop(stationC, line2);
    Stop Dl2 = new Stop(stationD, line2);
    Stop El2 = new Stop(stationE, line2);
    

    // Line L1 stops (Red line)
    l1Stops.add(Al1);
    l1Stops.add(Bl1);
    l1Stops.add(Cl1);
    l1Stops.add(Dl1);
    
    // Line L2 stops (Green line)
    l2Stops.add(Bl2);
    l2Stops.add(Cl2);
    l2Stops.add(Dl2);
    l2Stops.add(El2);

    // Adding stops to stations
    stationA.getStops().add(Al1);
    stationB.getStops().add(Bl1);
    stationB.getStops().add(Bl2);
    stationC.getStops().add(Cl1);
    stationC.getStops().add(Cl2);
    stationD.getStops().add(Dl1);
    stationD.getStops().add(Dl2);
    stationE.getStops().add(El2);

    // Initializing the model
    ModelData model = new ModelData(new ArrayList<>( List.of(line1, line2)),new ArrayList<>( List.of(stationA, stationB, stationC, stationD, stationE)));

    // Creating replacement service between Station C and Station D
    ReplacementServices.createReplacementService(model,new ArrayList<>( List.of(stationC, stationD)), new ArrayList<>(List.of(line1, line2)));

    // Assertions for expected result
    assertModel(model)
        .hasExactLines("L1", "L2-1", "L2-2", "P-1")
        .hasExactStations("Station A", "Station B", "Station C", "Station D", "Station E")
        .hasLineWithExactStations("L1", "Station A", "Station B", "Station C")
        .hasLineWithExactStations("L2-1", "Station B", "Station C")
        .hasLineWithExactStations("L2-2", "Station D", "Station E")
        .hasLineWithExactStations("P-1", "Station C", "Station D")
        .hasStationWithExactLines("Station A", "L1")
        .hasStationWithExactLines("Station B", "L1", "L2-1")
        .hasStationWithExactLines("Station C", "L1", "L2-1", "P-1")
        .hasStationWithExactLines("Station D", "P-1", "L2-2")
        .hasStationWithExactLines("Station E", "L2-2")
        .hasLine("P-1", line -> {
            Assert.assertEquals("#009EE3", line.getColor()); // Light blue for replacement
            return null;
        });
}
@Test
    public void testReplacementServiceBetweenTwoStationsWithMultipleLines2() {
    // Initial setup: Lines and Stations
    List<Stop> l1Stops = new ArrayList<>();
    Line line1 = new Line(1, "L1", "#FF0000", false, l1Stops);  // Red line
    List<Stop> l2Stops = new ArrayList<>();
    Line line2 = new Line(2, "L2", "#00FF00", false, l2Stops);  // Green line

    // Stations
    Station stationA = new Station(1, "Station A", new Coordinate(0, 0), new ArrayList<>());
    Station stationB = new Station(2, "Station B", new Coordinate(1, 0), new ArrayList<>());
    Station stationC = new Station(3, "Station C", new Coordinate(2, 0), new ArrayList<>());
    Station stationD = new Station(4, "Station D", new Coordinate(3, 0), new ArrayList<>());
    Station stationE = new Station(5, "Station E", new Coordinate(4, 0), new ArrayList<>());
    
    Stop Al1 = new Stop(stationA, line1);
    Stop Bl1 = new Stop(stationB, line1);
    Stop Cl1 = new Stop(stationC, line1);
    Stop Dl1 = new Stop(stationD, line1);
    Stop El1 = new Stop(stationE, line1);
    Stop Al2 = new Stop(stationA, line2);
    Stop Bl2 = new Stop(stationB, line2);
    Stop Cl2 = new Stop(stationC, line2);
    Stop Dl2 = new Stop(stationD, line2);
    

    // Line L1 stops (Red line)
    l1Stops.add(Al1);
    l1Stops.add(Bl1);
    l1Stops.add(Cl1);
    l1Stops.add(Dl1);
    l1Stops.add(El1);
    
    // Line L2 stops (Green line)
    l2Stops.add(Al2);
    l2Stops.add(Bl2);
    l2Stops.add(Cl2);
    l2Stops.add(Dl2);

    // Adding stops to stations
    stationA.getStops().add(Al1);
    stationA.getStops().add(Al2);
    stationB.getStops().add(Bl1);
    stationB.getStops().add(Bl2);
    stationC.getStops().add(Cl1);
    stationC.getStops().add(Cl2);
    stationD.getStops().add(Dl1);
    stationD.getStops().add(Dl2);
    stationE.getStops().add(El1);
    
    // Initializing the model
    ModelData model = new ModelData(new ArrayList<>( List.of(line1, line2)),new ArrayList<>( List.of(stationA, stationB, stationC, stationD, stationE)));

    // Creating replacement service between Station C and Station D
    ReplacementServices.createReplacementService(model,new ArrayList<>( List.of(stationB, stationC)), new ArrayList<>(List.of(line1, line2)));

    // Assertions for expected result
    assertModel(model)
        .hasExactLines("L1-1","L1-2", "L2-1", "L2-2", "P-1")
        .hasExactStations("Station A", "Station B", "Station C", "Station D", "Station E")
        .hasLineWithExactStations("L1-1", "Station A", "Station B")
        .hasLineWithExactStations("L1-2", "Station C", "Station D","Station E")
        .hasLineWithExactStations("L2-1", "Station A", "Station B")
        .hasLineWithExactStations("L2-2", "Station C", "Station D")
        .hasLineWithExactStations("P-1", "Station B", "Station C")
        .hasStationWithExactLines("Station A", "L1-1","L2-1")
        .hasStationWithExactLines("Station B", "L1-1", "L2-1","P-1")
        .hasStationWithExactLines("Station C", "L1-2", "L2-2", "P-1")
        .hasStationWithExactLines("Station D", "L1-2", "L2-2")
        .hasStationWithExactLines("Station E", "L1-2")
        .hasLine("P-1", line -> {
            Assert.assertEquals("#009EE3", line.getColor()); // Light blue for replacement
            return null;
        });
}

    private static ModelAsserter assertModel(ModelData data) {
        return new ModelAsserter(data);
    }

    private static class ModelAsserter {
        private final ModelData model;

        private ModelAsserter(ModelData model) {
            this.model = model;
        }

        public ModelAsserter hasStations(int num) {
            Assert.assertEquals("There are more or fewer stations lines than expected", num, model.stations.size());
            return this;
        }

        public ModelAsserter hasExactStations(String... stationNames) {
            assertStationListEquals(getStationsFromNames(stationNames), model.stations);
            return this;
        }

        public ModelAsserter hasStations(String... stationNames) {
            for(Station station : getStationsFromNames(stationNames)) {
                Assert.assertTrue("The station was not present: " + station.getName() + ")", model.stations.contains(station));
            }
            return this;
        }

        public ModelAsserter hasStation(String stationName) {
            hasStations(stationName);
            return this;
        }

        public ModelAsserter hasStation(String stationName, Function<Station, Void> assertStation) {
            hasStations(stationName);
            assertStation.apply(getStationFromName(stationName));
            return this;
        }

        public ModelAsserter hasStationWithExactLines(String stationName, String... lineNames) {
            hasStations(stationName);

            Station station = getStationFromName(stationName);
            List<Line> lines = getLinesFromNames(lineNames);

            assertLineListEquals(lines, station.getStops().stream().map(Stop::getLine).toList());

            return this;
        }

        public ModelAsserter hasStationWithLines(String stationName, String... lineNames) {
            hasStations(stationName);

            Station station = getStationFromName(stationName);
            List<Line> lines = getLinesFromNames(lineNames);
            List<Line> stationLines = station.getStops().stream().map(Stop::getLine).toList();

            for(Line line : lines) {
                Assert.assertTrue(
                    "Line (" + line.getName() + ") does not stop at Station (" + station.getName() + ")",
                    stationLines.contains(line)
                );
            }

            return this;
        }

        public ModelAsserter hasLines(int num) {
            Assert.assertEquals("There are more or fewer lines than expected", num, model.lines.size());
            return this;
        }

        public ModelAsserter hasExactLines(String... lineNames) {
            assertLineListEquals(getLinesFromNames(lineNames), model.lines);
            return this;
        }

        public ModelAsserter hasLines(String... lineNames) {
            for(Line line : getLinesFromNames(lineNames)) {
                Assert.assertTrue("The line was not present (" + line + ")", model.lines.contains(line));
            }
            return this;
        }

        public ModelAsserter hasLine(String lineName) {
            hasLines(lineName);
            return this;
        }

        public ModelAsserter hasLine(String lineName, Function<Line, Void> assertLine) {
            hasLines(lineName);
            assertLine.apply(getLineFromName(lineName));
            return this;
        }

        public ModelAsserter hasLineWithExactStations(String lineName, String... stationNames) {
            hasLines(lineName);

            Line line = getLineFromName(lineName);
            List<Station> stations = getStationsFromNames(stationNames);
            List<Station> lineStations = line.getStops().stream().map(Stop::getStation).toList();

            String expectedStationNames = stations.stream().map(Station::getName).collect(Collectors.joining(", "));
            String actualStationNames = lineStations.stream().map(Station::getName).collect(Collectors.joining(", "));

            Assert.assertTrue(
                "Stations differ on line (" +
                    line.getName() +
                    "). Expected: " +
                    expectedStationNames +
                    ". Actual: "
                    + actualStationNames,
                stations.equals(lineStations) || stations.equals(Lists.reverse(lineStations))
            );

            return this;
        }

        private List<Station> getStationsFromNames(String... stationNames) {
            return getStationsFromNames(List.of(stationNames));
        }

        private List<Station> getStationsFromNames(List<String> stationNames) {
            return stationNames.stream()
                .map(this::getStationFromName)
                .toList();
        }

        private Station getStationFromName(String stationName) {
            return model.stations.stream()
                .filter(s -> s.getName().equals(stationName))
                .findFirst()
                .orElseThrow(() -> {
                    Assert.fail("Station with name is not in the model: " + stationName);
                    return new RuntimeException();
                });
        }

        private void assertStationListEquals(List<Station> expected, List<Station> actual) {
            String expectedStations = expected.stream().map(Station::getName).collect(Collectors.joining(", "));
            String actualStations = actual.stream().map(Station::getName).collect(Collectors.joining(", "));

            Assert.assertEquals("There are more or fewer stations than expected", expected.size(), actual.size());
            Assert.assertTrue(
                "There are stations that were not expected but are present. Expected: " +
                    expectedStations +
                    ". Actual: " +
                    actualStations,
                expected.containsAll(actual)
            );
            Assert.assertTrue(
                "There are stations that were expected but are not present. Expected: " +
                    expectedStations +
                    ". Actual: " +
                    actualStations,
                actual.containsAll(expected)
            );
        }

        private List<Line> getLinesFromNames(String... lineNames) {
            return getLinesFromNames(List.of(lineNames));
        }

        private List<Line> getLinesFromNames(List<String> lineNames) {
            return lineNames.stream()
                    .map(this::getLineFromName)
                    .toList();
        }

        private Line getLineFromName(String lineName) {
            return model.lines.stream()
                .filter(l -> l.getName().equals(lineName))
                .findFirst()
                .orElseThrow(() -> {
                    Assert.fail("Line with name is not in the model: " + lineName);
                    return new RuntimeException();
                });
        }

        private void assertLineListEquals(List<Line> expected, List<Line> actual) {
            String expectedLines = expected.stream().map(Line::getName).collect(Collectors.joining(", "));
            String actualLines = actual.stream().map(Line::getName).collect(Collectors.joining(", "));

            Assert.assertEquals("There are more or fewer lines than expected", expected.size(), actual.size());
            Assert.assertTrue(
                "There are lines that were not expected but are present. Expected: " +
                    expectedLines +
                    ". Actual: " +
                    actualLines,
                expected.containsAll(actual)
            );
            Assert.assertTrue(
                "There are lines that were expected but are not present. Expected: " +
                    expectedLines +
                    ". Actual: " +
                    actualLines,
                actual.containsAll(expected)
            );
        }
    }
}

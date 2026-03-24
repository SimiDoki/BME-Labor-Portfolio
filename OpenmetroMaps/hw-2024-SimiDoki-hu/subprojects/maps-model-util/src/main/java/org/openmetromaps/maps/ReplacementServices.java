package org.openmetromaps.maps;

import java.util.ArrayList;
import java.util.List;

import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;
import org.openmetromaps.maps.model.Stop;

public class ReplacementServices {
    private ReplacementServices() {}

    public static int calculateReplacementCost(ModelData model, Line line) {
        throw new RuntimeException("Not implemented");
    }
    public static void closeStation(ModelData model, Station station/*Mk*/, List<Line> lines) {
        for(Line line:lines){
            if (line.getStops().size() <= 2) {
                return;
            }
        }

        for (Line line : lines) {
            List<Stop> stops = line.getStops();
            // Megkersem kijelölt megállót (Mk)
            Stop stopToRemove = findStopOnLine(stops, station);
            if (stopToRemove == null) continue;

            // A kijelölt megálló eltávolítás vonalról és stationból
            stopToRemove.getStation().getStops().remove(stopToRemove);
            stops.remove(stopToRemove);           
        }

        // Ha az állomáshoz nem tartoznak megállók, eltávolítjuk a modellből
        if (station.getStops().isEmpty()) {
            model.stations.remove(station);
        }
    }

    private static Stop findStopOnLine(List<Stop> stops, Station station) {
        for (Stop stop : stops) {
            if (stop.getStation().equals(station)) {
                return stop;
            }
        }
        return null;
    }

    public static void createReplacementService(ModelData model, List<Station> stations, List<Line> lines) {
        for(Line line : lines){
            if (!areStationsConsecutiveOnLine(line, stations)) {
                return; // Ha nem szomszédosak, megszakítjuk a műveletet
            }
        }
        // Iterálunk minden kijelölt vonalon
        for (Line line : lines) {
            // Ellenőrizzük, hogy a kijelölt állomások szomszédosak-e az adott vonalon
            if (!areStationsConsecutiveOnLine(line, stations)) {
                return; // Ha nem szomszédosak, megszakítjuk a műveletet
            }
    
            // Létrehozunk három listát: kezdő szakasz, pótlóvonalbeli szakasz és végszakasz
            List<Stop> startStops = new ArrayList<>();
            List<Stop> replacementStops = new ArrayList<>();
            List<Stop> endStops = new ArrayList<>();
    
            // A vonal megállóit szétválogatjuk a megfelelő listákba
            for (Stop stop : line.getStops()) {
                boolean found = false;
    
                // Ha a megálló szerepel a kijelölt állomások között, a pótlóvonalhoz adjuk
                for (Station station : stations) {
                    if (stop.getStation().equals(station)) {
                        replacementStops.add(stop);
                        found = true;
                        break;
                    }
                }
    
                // Ha nem találtuk meg a kijelölt állomások között
                if (!found) {
                    // A pótló megállók listája még üres -> kezdő szakaszhoz adjuk
                    if (replacementStops.isEmpty()) {
                        startStops.add(stop);
                    } else { 
                        // Már van pótló szakasz -> végszakaszhoz adjuk
                        endStops.add(stop);
                    }
                }
            }
    
            // Az utolsó megállókat az endStops-hoz hozzáadjuk, ha szükséges
            if (!endStops.isEmpty()) {
                List<Stop> temp = new ArrayList<>();
                temp.add(replacementStops.get(replacementStops.size() - 1));
                temp.addAll(endStops);
                endStops = temp; // Az új végszakaszt frissítjük
            }
    
            // Az első megállókat a startStops-hoz hozzáadjuk, ha szükséges
            if (!startStops.isEmpty()) {
                startStops.add(replacementStops.get(0));
            }
    
            // Létrehozzuk az új vonalakat, ha szükséges
            Line replacementLine = null;
            Line startLine;
            Line endLine;
             
            // Ha a pótló megállók nem fedik le az eredeti vonalat
            if (replacementStops.size() != line.getStops().size()) {
                Line[] segments = createStartAndEndSegments(model, line, stations, startStops, endStops);
                startLine = segments[0];
                endLine = segments[1];
                
                // Ha vannak pótló megállók, létrehozzuk a pótlóvonalat
                if (!replacementStops.isEmpty()) {
                    replacementLine = new Line(model.lines.size() + 1, generateReplacementLineName(model, line, lines), "#009EE3", false, replacementStops);
                    model.lines.add(replacementLine);
                }

                
                // Megállók hozzárendelése a megfelelő vonalakhoz
                assignStopsToLines2(line, replacementLine, startLine, endLine, startStops, replacementStops, endStops);
    
                // Frissítjük a pótlóvonal első és utolsó megállóit
                if(replacementLine!=null){
                    updateBoundaryStops(replacementLine, replacementStops, startStops, endStops);
                }
                
                // Az eredeti vonalat eltávolítjuk
                model.lines.remove(line);
    
                // Ütközések kezelése
                if(replacementLine!=null){
                    removeConflictingReplacementLines(model, line, replacementStops, replacementLine, lines);
                }
                
            }

        }
    }

    private static void assignStopsToLines1(Line originalLine, Line replacementLine, Line startSegment, Line endSegment,List<Stop> startStops, List<Stop> replacementStops, List<Stop> endStops) {
        // Iterálunk az eredeti vonal összes megállóján
        for (Stop stop : originalLine.getStops()) {

            if(replacementStops.contains(stop)){
                stop.setLine(replacementLine);
            }
            if(startStops.contains(stop)){
                stop.setLine(startSegment);
            }
            if(endStops.contains(stop)){
                stop.setLine(endSegment);
            }
        }
    }

    private static void assignStopsToLines2(Line originalLine, Line replacementLine, Line startSegment, Line endSegment,List<Stop> startStops, List<Stop> replacementStops, List<Stop> endStops) {
        // Iterálunk az eredeti vonal összes megállóján
        for (Stop stop : originalLine.getStops()) {

            // Ha a megálló a pótló megállók között van, hozzárendeljük a pótlóvonalhoz
            for (Stop pStop : replacementStops) {
                if (pStop.equals(stop)) {
                    stop.setLine(replacementLine);
                    break;
                }
            }

            // Ha a megálló a kezdő megállók között van, hozzárendeljük a kezdő szakaszhoz
            for (Stop eStop : startStops) {
                if (eStop.equals(stop)) {
                    stop.setLine(startSegment);
                    break;
                }
            }

            // Ha a megálló a vég megállók között van, hozzárendeljük a végszakaszhoz
            for (Stop vStop : endStops) {
                if (vStop.equals(stop)) {
                    stop.setLine(endSegment);
                    break;
                }
            }
        }
    }
    
    private static void updateBoundaryStops(Line replacementLine, List<Stop> replacementStops,List<Stop> startStops, List<Stop> endStops) {
        // Az első megálló frissítése a pótlóvonalon, ha létezik kezdő szakasz
        if (!startStops.isEmpty()) {
            Stop firstStop = new Stop(replacementStops.get(0).getStation(), replacementLine);
            replacementLine.getStops().set(0, firstStop); // Az első megálló beállítása
            replacementStops.get(0).getStation().getStops().add(firstStop); // Az állomás megállóihoz hozzáadjuk
        }

        // Az utolsó megálló frissítése a pótlóvonalon, ha létezik végszakasz
        if (!endStops.isEmpty()) {
            Stop secondStop = new Stop(replacementStops.get(replacementStops.size() - 1).getStation(), replacementLine);
            replacementLine.getStops().remove(replacementLine.getStops().size() - 1); // Az utolsó megállót töröljük
            replacementLine.getStops().add(secondStop); // Az új utolsó megállót hozzáadjuk
            replacementStops.get(replacementStops.size() - 1).getStation().getStops().add(secondStop); // Az állomáshoz hozzáadjuk
        }
    }


    
    private static void removeConflictingReplacementLines(ModelData model, Line originalLine, List<Stop> replacementStops,Line replacementLine, List<Line> originalLines) {
        // Megkeresi az originalLine indexét az originalLines listában
        int currentIndex = originalLines.indexOf(originalLine);

        // Végigmegy az originalLines azon vonalain, amelyek az originalLine előtt vannak
        for (int i = 0; i < currentIndex; i++) {
            // Iterálunk minden replacementStop megállón, hogy eltávolítsuk az ütköző vonalakhoz tartozó megállókat
            for (Stop replacementStop : replacementStops) {
                Station station = replacementStop.getStation();
                Stop stopToRemove = null;

                // Megkeressük az adott állomás megállói között az ütköző megállót, amely a replacementLine-hoz tartozik
                for (Stop stationStop : station.getStops()) {
                    if (stationStop.getLine().getName().equals(replacementLine.getName())) {
                        stopToRemove = stationStop;
                        break;
                    }
                }

                // Ha találtunk ütköző megállót, eltávolítjuk azt az állomás megállói közül
                if (stopToRemove != null) {
                    station.getStops().remove(stopToRemove);
                }
            }

            // Eltávolítjuk az ütköző pótlóvonalat a modell vonalai közül
            model.lines.remove(replacementLine);
        }                   
    }

    private static Line[] createStartAndEndSegments(ModelData model, Line line, List<Station> stations, List<Stop> startStops, List<Stop> endStops) {
        Line elotteLine=null;
        Line utanaLine=null; 
        // Ha van kezdő és végszakasz, mindkettőt létrehozzuk
        if (!startStops.isEmpty() && !endStops.isEmpty()) {
            if (stations.get(0).getStops().contains(startStops.get(startStops.size() - 1))) {
                // A kezdő szakasz megelőzi a pótlóvonalat
                elotteLine = new Line(line.getId(), line.getName() + "-1", line.getColor(), false, startStops);
                utanaLine = new Line(line.getId(), line.getName() + "-2", line.getColor(), false, endStops);
            } else if (stations.get(0).getStops().contains(endStops.get(0))) {
                // A végszakasz követi a pótlóvonalat
                elotteLine = new Line(line.getId(), line.getName() + "-2", line.getColor(), false, startStops);
                utanaLine = new Line(line.getId(), line.getName() + "-1", line.getColor(), false, endStops);
            }
            // Mindkét szakaszt hozzáadjuk a modellhez
            model.lines.add(elotteLine);
            model.lines.add(utanaLine);
        } else if (!startStops.isEmpty()) {
            // Ha csak kezdő szakasz van
            elotteLine = new Line(line.getId(), line.getName(), line.getColor(), false, startStops);
            model.lines.add(elotteLine);
        } else if (!endStops.isEmpty()) {
            // Ha csak végszakasz van
            utanaLine = new Line(line.getId(), line.getName(), line.getColor(), false, endStops);
            model.lines.add(utanaLine);
        }
        return new Line[]{elotteLine, utanaLine};
    }

  
    private static String generateReplacementLineName(ModelData model, Line line, List<Line> lines) {
        if (lines.size() == 1) {
            // Ha csak egy vonal van kiválasztva, a név a "P" + eredeti vonalnév lesz
            return "P" + line.getName();
        } else {
            // Több vonal esetén a név egyedi sorszámot kap
            int replacementLineCount = calculateReplacementLineCount(model);
            return "P-" + replacementLineCount;
        }
    }

    private static boolean areStationsConsecutiveOnLine(Line line, List<Station> stations) {
        List<Stop> stops = line.getStops();
        List<Integer> selectedIndexes = new ArrayList<>();
        //Kigyujtöm a lane-en lévő kijelölt stationokat
        for (int i = 0; i < stops.size(); i++) {
            if (stations.contains(stops.get(i).getStation())) {
                selectedIndexes.add(i);
            }
        }
        // Ellenőrzöm hogy a kijelölt állomások indexei egy összefüggő intervallumot alkotnak-e.
        // Ha bármely két egymást követő index nem szomszédos, akkor nem összefüggőek.
        for (int i = 1; i < selectedIndexes.size(); i++) {
            if (selectedIndexes.get(i) != selectedIndexes.get(i - 1) + 1) {
                return false;
            }
        }

        return true;
    }


    public static void createAlternativeService(ModelData model, Station stationA, Station stationB) {
        int replacementLineCount = calculateReplacementLineCount(model);
        String replacementLineName  = "P-" + replacementLineCount;

        //Létrehozom a két kapott station A és B illeszkedő stopokat
        Stop stopA = new Stop(stationA, null);
        Stop stopB = new Stop(stationB, null);
        List<Stop> stops = List.of(stopA, stopB);
        //Létrehozom az új stoppok között az új linet
        Line ujLine = new Line(model.lines.size() + 1,replacementLineName ,"#009EE3",false,stops);

        //Stoppokhoz beállítom a hozzájuk tartozó line-t
        stopA.setLine(ujLine);
        stopB.setLine(ujLine);
        
        //Modelhez hozzáadom az új line-t
        model.lines.add(ujLine);
        
        //A station-okhoz hozzáadaom az új stoppokat
        stationA.getStops().add(stopA);
        stationB.getStops().add(stopB);
    }

    //megszámolja a modelben lévő pótlóvonalak számát
    private static int calculateReplacementLineCount(ModelData model) {
        int count = 0;
        for (Line line : model.lines) {
            if (line.getName().startsWith("P")) {
                count++;
            }
        }
        return count + 1;
    }
}

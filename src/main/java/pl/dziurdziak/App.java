package pl.dziurdziak;

import com.google.common.base.Joiner;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomClass;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;
import pl.dziurdziak.rock.engine.MushroomRockEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class App {
    private final static Joiner ON_COMMA = Joiner.on(",");

    public static void main(String[] args) throws FileNotFoundException {
        double goodness = Double.parseDouble(args[0]);
        String filePath = args[1];
        int numberOfClusters = Integer.parseInt(args[2]);
        int numberOfPoints = Integer.parseInt(args[3]);

        System.out.println("Calculation started");
        long timeStart = System.currentTimeMillis();
        MushroomRockEngine mushroomRockEngine = new MushroomRockEngine(goodness, filePath);
        Collection<Cluster<MushroomPoint>> clusters = mushroomRockEngine.calculateClusters(numberOfClusters, numberOfPoints);
        long endTime = System.currentTimeMillis();
        System.out.println("Calculation ended");

        File output = new File("output_" + new SimpleDateFormat("YYYYMMdd-HHmmss.SSS").format(new Date()) + ".txt");
        try (PrintWriter printWriter = new PrintWriter(output)) {
            printWriter.println("Goodness: " + goodness);
            printWriter.println("Number of clusters: " + numberOfClusters);
            printWriter.println("Number of points: " + numberOfPoints);
            printWriter.println("-------------------------------------");
            printWriter.println("Calculation time: " + (endTime - timeStart) + " ms");
            printWriter.println("Calculated clusters: " + clusters.size());
            int wrong = 0;
            for (Cluster<MushroomPoint> cluster : clusters) {
                wrong += writeClusterInfo(printWriter, cluster);
            }
            printWriter.println("-------------------------------------");
            printWriter.println("Wrong " + wrong + " of " + numberOfPoints);
            printWriter.print("Accuracy: " + (100.0 - (double) wrong / numberOfPoints) + "%");
            printWriter.flush();
        }
    }

    private static int writeClusterInfo(PrintWriter printWriter, Cluster<MushroomPoint> cluster) {
        int edible = cluster.getPoints().stream().filter(point -> MushroomClass.EDIBLE.equals(point.getClazz()))
                .collect(Collectors.toList()).size();
        int poisonous = cluster.size() - edible;
        int wrongType = Math.min(edible, poisonous);
        printWriter.println(ON_COMMA.join(cluster.size(), edible >= poisonous ? "e" : "p", edible, poisonous, wrongType));
        return wrongType;
    }
}

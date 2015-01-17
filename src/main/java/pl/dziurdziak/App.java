package pl.dziurdziak;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomClass;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;
import pl.dziurdziak.rock.dao.impl.supermarket.SupermarketPoint;
import pl.dziurdziak.rock.engine.MushroomRockEngine;
import pl.dziurdziak.rock.engine.SupermarketRockEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private final static Joiner ON_COMMA = Joiner.on(",");

    public static void main(String[] args) throws IOException {
        String type = args[0];
        double goodness = Double.parseDouble(args[1]);
        String filePath = args[2];
        int numberOfClusters = Integer.parseInt(args[3]);
        int numberOfPoints = Integer.parseInt(args[4]);

        if ("m".equals(type)) {
            processMushroom(goodness, filePath, numberOfClusters, numberOfPoints);
        } else {
            double maxNeighboursDistance = Double.parseDouble(args[5]);
            processSupermarkets(goodness, filePath, numberOfClusters, numberOfPoints, maxNeighboursDistance);
        }
    }

    private static void processMushroom(double goodness, String filePath, int numberOfClusters, int numberOfPoints) throws FileNotFoundException {
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

    private static void processSupermarkets(double goodness, String filePath, int numberOfClusters, int numberOfPoints,
                                            double maxNeighboursDistance) throws IOException {
        System.out.println("Calculation started");
        long timeStart = System.currentTimeMillis();
        SupermarketRockEngine rockEngine = new SupermarketRockEngine(goodness, filePath, maxNeighboursDistance);
        Collection<Cluster<SupermarketPoint>> clusters = rockEngine.calculateClusters(numberOfClusters, numberOfPoints);
        long endTime = System.currentTimeMillis();
        System.out.println("Calculation ended");

        String fileName = "output_" + new SimpleDateFormat("YYYYMMdd-HHmmss.SSS").format(new Date());
        File output = new File(fileName + ".txt");
        try (PrintWriter printWriter = new PrintWriter(output)) {
            printWriter.println("Goodness: " + goodness);
            printWriter.println("Number of clusters: " + numberOfClusters);
            printWriter.println("Number of points: " + numberOfPoints);
            printWriter.println("-------------------------------------");
            printWriter.println("Calculation time: " + (endTime - timeStart) + " ms");
            printWriter.println("Calculated clusters: " + clusters.size());

            for (Cluster<SupermarketPoint> cluster : clusters) {
                writeSupermarketClusterInfo(printWriter, cluster);
            }
            printWriter.println("-------------------------------------");
            printWriter.flush();
        }

        createChart(fileName + ".png", clusters);
    }

    @VisibleForTesting
    public static void createChart(String fileName, Collection<Cluster<SupermarketPoint>> clusters) throws IOException {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        int i = 1;
        for (Cluster<SupermarketPoint> cluster : clusters) {
            XYSeries series = new XYSeries("Cluster_"  + i++);
            cluster.getPoints().stream().forEach(point -> series.add(point.getLongitude(), point.getLatitude()));
            xySeriesCollection.addSeries(series);
        }

        JFreeChart jFreeChart = ChartFactory.createScatterPlot(null, "Longitude", "Latitude", xySeriesCollection,
                PlotOrientation.VERTICAL, true, false, false);
        ChartUtilities.saveChartAsPNG(new File(fileName), jFreeChart, 800, 600);
    }

    private static void writeSupermarketClusterInfo(PrintWriter printWriter, Cluster<SupermarketPoint> cluster) {
        printWriter.println(cluster.size());
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

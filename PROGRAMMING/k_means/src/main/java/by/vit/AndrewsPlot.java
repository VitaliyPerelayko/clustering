package by.vit;

import by.vit.binomination.BiVector;
import by.vit.binomination.Mapper;
import by.vit.readwritefile.ParsingOfFile;
import by.vit.records.ListsOfRecords;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.vit.kmeans.FileMapper.*;

public class AndrewsPlot extends Application {
    private static final String PATH = "E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\access_logs\\access_log";
    private static final byte[] weightsOfAttributes = new byte[]{3, 1, 1, 2, 1, 2};
    private static final double INTERVAL = 0.1;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        File file = new File(PATH);

        stage.setTitle("Andrews Plot");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("t");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Andrews Plot");


        //defining a series
        List<XYChart.Series> seriesList = setManySeries(filter(parseFile(file, weightsOfAttributes)), INTERVAL);

        seriesList.forEach(series -> lineChart.getData().add(series));

        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private List<BiVector> parseFile(File file, final byte[] weightsOfAttributes) {
        ListsOfRecords listsOfRecords = new ListsOfRecords();
        ParsingOfFile.parse(file, listsOfRecords, createFileWithBadRecords(), createFileForOneIP(), createFileLocalHostIP());
        return Mapper.mapListOfRecords(listsOfRecords, weightsOfAttributes);
    }

    private List<XYChart.Series> setManySeries(List<byte[]> biVectors, final double interval) {
        final List<XYChart.Series> seriesList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            byte[] vector = biVectors.get(i);
            XYChart.Series series = new XYChart.Series();
            setData(series, interval, vector);
            seriesList.add(series);
        }
        return seriesList;
    }

    private void setData(XYChart.Series series, double interval, byte[] xVector) {
        double t = -Math.PI;
        while (t < Math.PI) {
            series.getData().add(new XYChart.Data(t, andrewF(t, xVector)));
            t += interval;
        }
    }

    private double andrewF(double t, byte[] xVector) {
        double result = xVector[0] / Math.sqrt(2D);
        for (int i = 1; i < xVector.length; i++) {
            int k = (i + 1) / 2;
            if (i % 2 == 1) {
                result += xVector[i] * Math.sin(t * k);
            } else {
                result += xVector[i] * Math.cos(t * k);
            }
        }
        return result*100;
    }

    private List<byte[]> filter(List<BiVector> biVectors) {
        return biVectors.stream().map(BiVector::getVector).distinct().collect(Collectors.toList());
    }
}
package by.vit.kmodes;

import by.vit.distance.Distance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class KModes {

    private static final Random random = new Random();

    public static Map<Centroid, List<Record>> fit(List<Record> records,
                                                  int k,
                                                  Distance distance,
                                                  int maxIterations) {
        // omitted
        return null;
    }

    private static List<Centroid> randomCentroids(List<Record> records, int k) {
        List<Centroid> centroidList = new ArrayList<>(k);


        return centroidList;
    }

    private static List<String> getRequestTypes(List<Record> records){
        return  null;
    }
}

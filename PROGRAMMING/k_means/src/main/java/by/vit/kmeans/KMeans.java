package by.vit.kmeans;

import by.vit.binomination.BiVector;
import by.vit.binomination.Mapper;
import by.vit.distance.Distance;
import by.vit.records.RecordId;

import java.util.*;
import java.util.stream.Collectors;

import static by.vit.records.AttributesLength.*;

public class KMeans {

    private static final Random random = new Random();

    public static Map<Centroid, List<RecordId>> fit(List<BiVector> records,
                                                    //int lengthOfIPList,
                                                    int k,
                                                    Distance distance,
                                                    int maxIterations,
                                                    byte[] weightsOfAttributes) {

        List<Centroid> centroids = placeCentroids(k,
                //lengthOfIPList,
                weightsOfAttributes);
        Map<Centroid, List<BiVector>> clusters = new HashMap<>();
        Map<Centroid, List<BiVector>> lastState = new HashMap<>();

        // iterate for a pre-defined number of times
        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (BiVector record : records) {
                Centroid centroid = nearestCentroid(record, centroids, distance);
                assignToCluster(clusters, record, centroid);
            }

            // if the assignments do not change, then the algorithm terminates
            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids
            centroids = relocateCentroids(clusters, centroids);
            clusters = new HashMap<>();
            System.out.println(i);
        }
        return map(lastState);
    }

    private static List<Centroid> placeCentroids(int k,
                                                 //int lengthOfIpList,
                                                 byte[] weightsOfAttributes) {
        List<Centroid> centroidList = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            centroidList.add(randomCentroid(//lengthOfIpList,
                    weightsOfAttributes));
        }
        return centroidList;
    }

    private static Centroid randomCentroid(//int lengthOfIpList,
                                           byte[] weightsOfAttributes) {
//        int length = lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH + TIME_OF_DAY;
//        double[] vector = new double[length];
//        vector[random.nextInt(lengthOfIpList)] = random.nextDouble() * weightsOfAttributes[0];
//        vector[random.nextInt(lengthOfIpList + TYPE_OF_REQUEST)] = random.nextDouble() * weightsOfAttributes[1];
//        vector[random.nextInt(lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE)] =
//                random.nextDouble() * weightsOfAttributes[2];
//        vector[random.nextInt(lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK)] =
//                random.nextDouble() * weightsOfAttributes[3];
//        vector[random.nextInt(lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH)] =
//                random.nextDouble() * weightsOfAttributes[4];
//        vector[random.nextInt(
//                lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH + TIME_OF_DAY)] =
//                random.nextDouble() * weightsOfAttributes[5];

        int length = TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH + TIME_OF_DAY;
        double[] vector = new double[length];
        //vector[random.nextInt(lengthOfIpList)] = random.nextDouble() * weightsOfAttributes[0];
        vector[random.nextInt(TYPE_OF_REQUEST)] = random.nextDouble() * weightsOfAttributes[1];
        vector[random.nextInt(TYPE_OF_REQUEST + CODE_OF_RESPONSE)] =
                random.nextDouble() * weightsOfAttributes[2];
        vector[random.nextInt(TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK)] =
                random.nextDouble() * weightsOfAttributes[3];
        vector[random.nextInt(TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH)] =
                random.nextDouble() * weightsOfAttributes[4];
        vector[random.nextInt(TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH + TIME_OF_DAY)] =
                random.nextDouble() * weightsOfAttributes[5];
        return new Centroid(vector);
    }

    private static void assignToCluster(Map<Centroid, List<BiVector>> clusters,
                                        BiVector biVector,
                                        Centroid centroid) {
        List<BiVector> recordIdList = clusters.get(centroid);
        if (recordIdList == null) {
            recordIdList = new ArrayList<>();
        }
        recordIdList.add(biVector);
        clusters.put(centroid, recordIdList);
    }

    private static Centroid nearestCentroid(BiVector vector, List<Centroid> centroids, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;
        for (Centroid centroid : centroids) {
            double currentDistance = distance.calcDistance(centroid.getVector(), vector.getVector());
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }
        return nearest;
    }

    private static List<Centroid> relocateCentroids(Map<Centroid, List<BiVector>> clusters, List<Centroid> centroids) {
        final List<Centroid> newCentroids = new ArrayList<>();
        centroids.forEach(centroid -> {
            List<BiVector> biVectors = clusters.get(centroid);
            if (biVectors == null || biVectors.isEmpty()) {
                newCentroids.add(centroid);
            } else {
                newCentroids.add(average(biVectors, centroid.getVector().length));
            }
        });
        return newCentroids;
    }

    private static Centroid average(List<BiVector> records, final int length) {
        final double[] average = new double[length];
        final int amount = records.size();
        records.forEach(vector -> {
            byte[] v = vector.getVector();
            for (int i = 0; i < length; i++) {
                average[i] += ((double) v[i]) / amount;
            }
        });
        return new Centroid(average);
    }

    private static Map<Centroid, List<RecordId>> map(Map<Centroid, List<BiVector>> lastState) {
        final Map<Centroid, List<RecordId>> val = new HashMap<>();
        lastState.forEach((centroid, biVectors) ->
                val.put(centroid, biVectors.stream().map(Mapper::mapBiVectorToRecordId).collect(Collectors.toList())));
        return val;
    }
}

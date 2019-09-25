package by.vit.readwritefile;

import by.vit.kmeans.Centroid;
import by.vit.records.RecordId;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WriteToFile {

    public static void createFilesAndWriteToThem(Map<Centroid, List<RecordId>> clusters,
                                                 Map<Centroid, File> mapCentroidFile,
                                                 File[] filesWithData) {
        for (File file : filesWithData) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                List<String> records = br.lines().collect(Collectors.toList());
                for (int i = 0; i < records.size(); i++) {
                    RecordId recordId = new RecordId(file.getName(), i);
                    Centroid centroid = searchCluster(clusters, recordId);
                    if (centroid == null){
                        continue;
                    }
                    File fileToWrite = mapCentroidFile.get(centroid);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite, true))) {
                        bw.write(records.get(i));
                        bw.newLine();
                        bw.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Centroid searchCluster(Map<Centroid, List<RecordId>> clusters, RecordId record) {
        if (clusters.isEmpty()) {
            return null;
        }
        Centroid returnedCentroid = null;
        List<Centroid> emptyClusters = new ArrayList<>();
        Set<Centroid> centroids = clusters.keySet();
        for (Centroid centroid : centroids) {
            List<RecordId> cluster = clusters.get(centroid);
            if (cluster.isEmpty()) {
                emptyClusters.add(centroid);
                continue;
            }
            if (isBelongToCluster(clusters.get(centroid), record)) {
                returnedCentroid = centroid;
                break;
            }
        }
        emptyClusters.forEach(clusters::remove);
        return returnedCentroid;
    }

    private static boolean isBelongToCluster(List<RecordId> cluster, RecordId recordId) {
        if (cluster.contains(recordId)) {
            cluster.remove(recordId);
            return true;
        } else {
            return false;
        }
    }
}

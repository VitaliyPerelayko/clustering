package by.vit;

import by.vit.binomination.BiVector;
import by.vit.distance.Distance;
import by.vit.distance.DistanceBetweenVectors;
import by.vit.kmeans.Centroid;
import by.vit.records.ListsOfRecords;
import by.vit.records.RecordId;

import java.io.File;
import java.util.List;
import java.util.Map;

import static by.vit.binomination.Mapper.mapListOfRecords;
import static by.vit.kmeans.FileMapper.*;
import static by.vit.kmeans.KMeans.fit;
import static by.vit.readwritefile.ParsingOfFile.parse;
import static by.vit.readwritefile.WriteToFile.createFilesAndWriteToThem;

public class AdditionalAnalyzing {
    private static final int k = 20;
    private static final int maxIterations = 100;
    private static final byte[] weightsOfAttributes = new byte[]{0, 1, 1, 2, 1, 2};
    private static final String pathToDirectoryWithNewFiles = "E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\new_clusters\\";
    private static final String pathToDirectoryWithFilesToParse = "E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\good_records";



    public static void main(String[] args) {

        File[] filesToParse = getFiles(pathToDirectoryWithFilesToParse);

        // create implementation of Distance
        Distance distance = new DistanceBetweenVectors();
        ListsOfRecords listsOfRecords = new ListsOfRecords();
        // parse file to list of records
        for (File f : filesToParse) {
            parse(f, listsOfRecords);
        }
        // get Length of IPList
        //int lengthOfIPList = listsOfRecords.getSetOfIP().size();

        // map list of records to List of BiVectors
        List<BiVector> records = mapListOfRecords(listsOfRecords, weightsOfAttributes);
        // k-means clustering
        Map<Centroid, List<RecordId>> clusters = fit(records, //lengthOfIPList,
                k, distance, maxIterations,
                weightsOfAttributes);
        // create cluster files
        Map<Centroid, File> mapCentroidFile = createClusterFiles(clusters.keySet(), pathToDirectoryWithNewFiles);
        // write to clusters files
        createFilesAndWriteToThem(clusters, mapCentroidFile, filesToParse);
    }
}

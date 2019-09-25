package by.vit.kmeans;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileMapper {

    private static final String[] arrayOfIP = {"63.143.42.242", "63.143.42.243", "24.6.216.145", "54.243.185.88",
            "35.165.247.151", "173.199.80.131", "23.92.127.10", "134.90.149.139", "162.219.176.251", "43.245.160.230",
            "194.99.104.27", "185.206.224.249", "31.28.24.135", "52.41.174.241", "196.52.34.4", "177.154.139.194",
            "66.70.182.117", "5.45.79.16"};


    public static Map<Centroid, File> createClusterFiles(Set<Centroid> clusters, String pathToDirectoryWithNewFiles) {
        final Map<Centroid, File> mapCentroidFile = new HashMap<>();
        int i = 0;
        for (Centroid centroid : clusters) {
            File file = new File(pathToDirectoryWithNewFiles + "cluster" + i++);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapCentroidFile.put(centroid, file);
        }
        return mapCentroidFile;
    }

    public static File[] getFiles(String path) {
        File directory = new File(path);
        return directory.listFiles();
    }

    public static Map<String, File> createFileForOneIP() {
        Map<String, File> ipFileMap = new HashMap<>();
        for (String ip : arrayOfIP) {
            String name = "E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\clusters\\oneIP" + ip;
            File fileToWrite = new File(name);
            try {
                fileToWrite.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ipFileMap.put(ip, fileToWrite);
        }
        return ipFileMap;
    }

    public static File createFileLocalHostIP() {
        File fileToWrite = new File("E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\clusters\\localHostIP.txt");
        try {
            fileToWrite.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileToWrite;
    }

    public static File createFileWithBadRecords() {
        File fileToWrite = new File("E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\clusters\\bad_records.txt");
        try {
            fileToWrite.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileToWrite;
    }
}


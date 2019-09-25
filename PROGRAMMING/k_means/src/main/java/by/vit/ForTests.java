package by.vit;

import by.vit.records.ListsOfRecords;

import java.io.File;

import static by.vit.readwritefile.ParsingOfFile.parse;

public class ForTests {
    public static void main(String[] args) {
        String path = "E:\\Work\\vitaluga\\PROGRAMMING\\Test_Task\\access_logs\\access_log";

        File file = new File(path);
        ListsOfRecords listsOfRecords = new ListsOfRecords();

        //parse(file, listsOfRecords,);

//        System.out.println(listsOfRecords.getSetOfIP());
//
//        List<String> listOfID = Arrays.asList("3","5","63.143.42.242","7");
//        RecordAttributes ra = listsOfRecords.getGoodRecords().get("access_log").get(36);
//        BiVector biVector = mapRecordAttributesToBiVector("file",
//                ra,
//                listOfID, new byte[]{1, 1, 1, 1, 1, 1});
//        System.out.println(ra);
//        System.out.println(Arrays.toString(biVector.getVector()));


    }
}

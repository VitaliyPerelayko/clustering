package by.vit.records;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListsOfRecords {

    final private Map<String, List<RecordAttributes>> goodRecords = new HashMap<>();
    //final private Set<String> setOfIP = new HashSet<>();

    public void putGoodRecord(String fileName, List<RecordAttributes> goodRecords) {
        this.goodRecords.put(fileName, goodRecords);
    }

    public Map<String, List<RecordAttributes>> getGoodRecords() {
        return goodRecords;
    }

//    public Set<String> getSetOfIP() {
//        calcListOfIp();
//        return setOfIP;
//    }

//    private void calcListOfIp() {
//        goodRecords.forEach((s, recordAttributes) ->
//                recordAttributes.forEach(record ->
//                        this.setOfIP.add(record.getIp())));
//    }
}
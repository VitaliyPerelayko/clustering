package by.vit.binomination;

import by.vit.records.ListsOfRecords;
import by.vit.records.RecordAttributes;
import by.vit.records.RecordId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.vit.records.AttributesLength.*;

public class Mapper {

    public static List<BiVector> mapListOfRecords(ListsOfRecords listsOfRecords, final byte[] weightsOfAttributes) {
        //final List<String> listOfIP = new ArrayList<>(listsOfRecords.getSetOfIP());
        final List<BiVector> biVectorList = new ArrayList<>();
        final Map<String, List<RecordAttributes>> goodRecords = listsOfRecords.getGoodRecords();
        final Set<String> files = goodRecords.keySet();
        files.forEach(s ->
                goodRecords.get(s).forEach(recordAttributes ->
                        biVectorList.add(
                                mapRecordAttributesToBiVector(s, recordAttributes, //listOfIP,
                                        weightsOfAttributes)
                        ))
        );
        return biVectorList;
    }

    public static BiVector mapRecordAttributesToBiVector(String nameOfFile, RecordAttributes attributes,
                                                         //List<String> ipList,
                                                         byte[] weightsOfAttributes) {
        // length = ipList.size() + 9 (types of request) + 5 (codes of response) + 7 (day of week) +
        // + 12 (month) + 4 (time of day)
        //int lengthOfIpList = ipList.size();
        int length = TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH + TIME_OF_DAY;
        byte[] vector = new byte[length];
        //vector[ipList.indexOf(attributes.getIp())] = weightsOfAttributes[0];
//        vector[lengthOfIpList + attributes.getTypeOfRequest().ordinal()] = weightsOfAttributes[1];
//        vector[lengthOfIpList + TYPE_OF_REQUEST + attributes.getCodeOfResponse() - 1] = weightsOfAttributes[2];
//        vector[lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + attributes.getDayOfWeek().ordinal()] =
//                weightsOfAttributes[3];
//        vector[lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + attributes.getMonth().ordinal()] =
//                weightsOfAttributes[4];
//        vector[lengthOfIpList + TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH +
//                attributes.getTimeOfDay().ordinal()] = weightsOfAttributes[5];

        vector[attributes.getTypeOfRequest().ordinal()] = weightsOfAttributes[1];
        vector[TYPE_OF_REQUEST + attributes.getCodeOfResponse() - 1] = weightsOfAttributes[2];
        vector[TYPE_OF_REQUEST + CODE_OF_RESPONSE + attributes.getDayOfWeek().ordinal()] =
                weightsOfAttributes[3];
        vector[TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + attributes.getMonth().ordinal()] =
                weightsOfAttributes[4];
        vector[TYPE_OF_REQUEST + CODE_OF_RESPONSE + DAY_OF_WEEK + MONTH +
                attributes.getTimeOfDay().ordinal()] = weightsOfAttributes[5];


        return new BiVector(nameOfFile, attributes.getNumOfRecord(), vector);
    }

    public static RecordId mapBiVectorToRecordId(BiVector vector) {
        return new RecordId(vector.getNameOfFile(), vector.getNumOfRecord());
    }

    // TODO: maybe it will be useful
    public RecordAttributes mapBiVectorToRecordAttributes(BiVector biVector, List<String> ipList) {
        return null;
    }

}

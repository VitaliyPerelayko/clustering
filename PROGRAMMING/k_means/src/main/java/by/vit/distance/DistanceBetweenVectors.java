package by.vit.distance;

import by.vit.kmodes.Record;

import java.math.BigDecimal;
import java.util.Objects;

public class DistanceBetweenVectors implements Distance {

    //TODO: try to play with weight of attributes
    @Override
    public BigDecimal calcDistance(Record vector1, Record vector2) {
        BigDecimal distance = BigDecimal.ZERO;
        // 1 ip
//        if (!Objects.equals(vector1.getIp(), vector2.getIp())) {
//            distance = distance.add(BigDecimal.valueOf(0.20));
//        }
        // 2 day of week
        if (!Objects.equals(vector1.getDayOfWeek(), vector2.getDayOfWeek())) {
            distance = distance.add(BigDecimal.valueOf(0.16));
        }
        // 3 code of response
        if (!Objects.equals(vector1.getCodeOfResponse(), vector2.getCodeOfResponse())) {
            distance = distance.add(BigDecimal.valueOf(0.16));
        }
        // 4 month
        if (!Objects.equals(vector1.getMonth(), vector2.getMonth())) {
            distance = distance.add(BigDecimal.valueOf(0.16));
        }
        // 5 time of day
        if (!Objects.equals(vector1.getTimeOfDay(), vector2.getTimeOfDay())) {
            distance = distance.add(BigDecimal.valueOf(0.16));
        }
        // 6 type of request
        if (!Objects.equals(vector1.getTypeOfRequest(), vector2.getTypeOfRequest())) {
            distance = distance.add(BigDecimal.valueOf(0.16));
        }
        return distance;
    }

    /**
     * calculate Euclidean distance
     *
     * @param vector1 biVector1
     * @param vector2 biVector2
     * @return double
     */
    @Override
    public double calcDistance(double[] vector1, byte[] vector2) {
        double sum = 0D;
        for (int i = 0; i < vector1.length; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(sum);
    }
}

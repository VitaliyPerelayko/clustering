package by.vit.distance;

import by.vit.binomination.BiVector;
import by.vit.kmodes.Record;

import java.math.BigDecimal;

public interface Distance {

    BigDecimal calcDistance(Record vector1, Record vector2);

    double calcDistance(double[] vector1, byte[] vector2);
}

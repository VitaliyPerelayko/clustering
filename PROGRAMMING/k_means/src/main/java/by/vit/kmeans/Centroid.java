package by.vit.kmeans;

import java.util.Arrays;

public class Centroid {
    private double[] vector;

    public Centroid(double[] vector) {
        this.vector = vector;
    }

    public double[] getVector() {
        return vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Centroid)) return false;
        Centroid centroid = (Centroid) o;
        return Arrays.equals(getVector(), centroid.getVector());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getVector());
    }
}

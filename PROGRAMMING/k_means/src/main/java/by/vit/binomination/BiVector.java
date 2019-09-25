package by.vit.binomination;

import java.util.Arrays;
import java.util.Objects;

public class BiVector {

    private String nameOfFile;
    private Integer numOfRecord;

    private byte[] vector;

    public BiVector(String nameOfFile, Integer numOfRecord, byte[] vector) {
        this.nameOfFile = nameOfFile;
        this.numOfRecord = numOfRecord;
        this.vector = vector;
    }

    public byte[] getVector() {
        return vector;
    }

//    public byte get(int position){
//        return this.vector[position];
//    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public Integer getNumOfRecord() {
        return numOfRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiVector)) return false;
        BiVector biVector = (BiVector) o;
        return Objects.equals(getNameOfFile(), biVector.getNameOfFile()) &&
                Objects.equals(getNumOfRecord(), biVector.getNumOfRecord()) &&
                Arrays.equals(getVector(), biVector.getVector());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getNameOfFile(), getNumOfRecord());
        result = 31 * result + Arrays.hashCode(getVector());
        return result;
    }
}

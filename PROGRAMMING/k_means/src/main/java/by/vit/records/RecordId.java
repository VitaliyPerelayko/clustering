package by.vit.records;

import java.util.Objects;

public class RecordId {
    private String fileName;
    private Integer numOfRecord;

    public RecordId(String fileName, Integer numOfRecord) {
        this.fileName = fileName;
        this.numOfRecord = numOfRecord;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getNumOfRecord() {
        return numOfRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordId)) return false;
        RecordId recordId = (RecordId) o;
        return Objects.equals(getFileName(), recordId.getFileName()) &&
                Objects.equals(getNumOfRecord(), recordId.getNumOfRecord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(), getNumOfRecord());
    }
}

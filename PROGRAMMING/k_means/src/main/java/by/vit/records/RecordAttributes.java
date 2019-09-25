package by.vit.records;

import java.time.DayOfWeek;
import java.time.Month;

public class RecordAttributes {

    private Integer numOfRecord;
    //private String ip;
    private TypeOfRequest typeOfRequest;
    private Byte codeOfResponse;
    private DayOfWeek dayOfWeek;
    private Month month;
    private TimeOfDay timeOfDay;

//    public String getIp() {
//        return ip;
//    }

//    public void setIp(String ip) {
//        this.ip = ip;
//    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(TypeOfRequest typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public Byte getCodeOfResponse() {
        return codeOfResponse;
    }

    public void setCodeOfResponse(Byte codeOfResponse) {
        this.codeOfResponse = codeOfResponse;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Integer getNumOfRecord() {
        return numOfRecord;
    }

    public void setNumOfRecord(Integer numOfRecord) {
        this.numOfRecord = numOfRecord;
    }

    @Override
    public String toString() {
        return "InformationVector{" +
                "numOfRecord=" + numOfRecord +
                //", ip='" + ip + '\'' +
                ", typeOfRequest='" + typeOfRequest + '\'' +
                ", codeOfResponse=" + codeOfResponse +
                ", dayOfWeek=" + dayOfWeek +
                ", month=" + month +
                ", timeOfDay=" + timeOfDay +
                '}';
    }
}

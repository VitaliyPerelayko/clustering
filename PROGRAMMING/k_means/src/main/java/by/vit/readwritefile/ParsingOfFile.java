package by.vit.readwritefile;

import by.vit.records.ListsOfRecords;
import by.vit.records.RecordAttributes;
import by.vit.records.TimeOfDay;
import by.vit.records.TypeOfRequest;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParsingOfFile {

    public static void parse(File file, ListsOfRecords listsOfRecords, File badRecords, Map<String, File> ipFileMap,
                             File localHostFile) {
        final String fileName = file.getName();
        final List<RecordAttributes> goodRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> records = br.lines().collect(Collectors.toList());
            for (int i = 0; i < records.size(); i++) {
                RecordAttributes record = setInformation(getInfo(records.get(i), ipFileMap, localHostFile));
                if (record == null) {
                    writeBadRecord(records.get(i), badRecords);
                } else {
                    if (record.getCodeOfResponse() == 8) {
                        continue;
                    }
                    record.setNumOfRecord(i);
                    goodRecords.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listsOfRecords.putGoodRecord(fileName, goodRecords);
    }

    public static void parse(File file, ListsOfRecords listsOfRecords) {
        final String fileName = file.getName();
        final List<RecordAttributes> goodRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> records = br.lines().collect(Collectors.toList());
            for (int i = 0; i < records.size(); i++) {
                RecordAttributes record = setInformation(getInfo(records.get(i)));
                if (record == null) {
                    continue;
                }
                record.setNumOfRecord(i);
                goodRecords.add(record);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        listsOfRecords.putGoodRecord(fileName, goodRecords);
    }

    private static RecordAttributes setInformation(String[] info) {
        if (info == null) {
            return null;
        }
        final RecordAttributes infVector = new RecordAttributes();
        //infVector.setIp(info[0]);
//        if ("63.143.42.242".equals(info[0])||"63.143.42.243".equals(info[0])) {
//            return infVector;
//        }
        if ("next".equals(info[0])) {
            infVector.setCodeOfResponse((byte) 8);
            return infVector;
        }
        LocalDateTime dateTime = parseDateTime(info[1]);
        if (dateTime == null) return null;
        infVector.setDayOfWeek(dateTime.getDayOfWeek());
        infVector.setMonth(dateTime.getMonth());
        TimeOfDay timeOfDay = parseTime(dateTime.getHour());
        if (timeOfDay == null) return null;
        infVector.setTimeOfDay(timeOfDay);
        TypeOfRequest typeOfRequest = parseRequest(info[2]);
        if (typeOfRequest == null) return null;
        infVector.setTypeOfRequest(typeOfRequest);
        try {
            int code = Integer.parseInt(info[3]) / 100;
            infVector.setCodeOfResponse((byte) code);
        } catch (NumberFormatException e) {
            return null;
        }
        return infVector;
    }

    private static String[] getInfo(String line, Map<String, File> ipFileMap, File localHostFile) {
        try {
            String[] info = new String[4];
            // IP
            //info[0] = line.substring(0, line.indexOf(" - - ["));
            String ip = line.substring(0, line.indexOf(" - - ["));

            if (isOneOfIpAddresses(line, ipFileMap, ip, localHostFile)) {
                info[0] = "next";
                return info;
            }

            line = line.substring(ip.length() + 6);
            // date
            info[1] = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf("\"") + 1);
            // type of request
            info[2] = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf("HTTP/1.") + 10);
            // code of response
            info[3] = line.substring(0, line.indexOf(" "));
            return info;
        } catch (Exception e) {
            return null;
        }
    }

    private static String[] getInfo(String line) {
        try {
            String[] info = new String[4];
            // IP
            //info[0] = line.substring(0, line.indexOf(" - - ["));

            line = line.substring(line.indexOf(" - - [") + 6);
            // date
            info[1] = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf("\"") + 1);
            // type of request
            info[2] = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf("HTTP/1.") + 10);
            // code of response
            info[3] = line.substring(0, line.indexOf(" "));
            return info;
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDateTime parseDateTime(String text) {
        String[] dayMonthText = text.split("/");
        String[] yearHourMinutesSec = dayMonthText[2].split(":");
        Month month = parseMonth(dayMonthText[1]);
        if (month == null) return null;
        return LocalDateTime.of(Integer.parseInt(yearHourMinutesSec[0]),
                month,
                Integer.parseInt(dayMonthText[0]),
                Integer.parseInt(yearHourMinutesSec[1]),
                Integer.parseInt(yearHourMinutesSec[2]),
                Integer.parseInt(yearHourMinutesSec[3])
        );
    }

    private static Month parseMonth(final String month) {
        switch (month) {
            case "Dec":
                return Month.DECEMBER;
            case "Jan":
                return Month.JANUARY;
            case "Feb":
                return Month.FEBRUARY;
            case "Mar":
                return Month.MARCH;
            case "Apr":
                return Month.APRIL;
            case "May":
                return Month.MAY;
            case "Jun":
                return Month.JUNE;
            case "Jul":
                return Month.JULY;
            case "Aug":
                return Month.AUGUST;
            case "Sep":
                return Month.SEPTEMBER;
            case "Oct":
                return Month.OCTOBER;
            case "Nov":
                return Month.NOVEMBER;
        }
        return null;
    }

    private static TimeOfDay parseTime(int hour) {
        if (hour < 0) return null;
        if (hour <= 5) return TimeOfDay.NIGHT;
        if (hour <= 11) return TimeOfDay.MORNING;
        if (hour <= 17) return TimeOfDay.AFTERNOON;
        if (hour <= 23) return TimeOfDay.EVENING;
        return null;
    }

    private static TypeOfRequest parseRequest(String string) {
        TypeOfRequest typeOfRequest;
        try {
            typeOfRequest = TypeOfRequest.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return typeOfRequest;
    }

    private static boolean isOneOfIpAddresses(String line, Map<String, File> ipFileMap, String ip, File localHostFile) {
        Set<String> ipSet = ipFileMap.keySet();
        if (ipSet.contains(ip)) {
            writeBadRecord(line, ipFileMap.get(ip));
            return true;
        }
        if ("::1".equals(ip)) {
            writeBadRecord(line, localHostFile);
            return true;
        }
        return false;
    }

    private static void writeBadRecord(String record, File fileToWrite) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            bw.write(record);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

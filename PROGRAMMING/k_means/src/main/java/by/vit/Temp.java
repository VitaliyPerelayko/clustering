package by.vit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static by.vit.kmeans.FileMapper.getFiles;

public class Temp {
    public static void main(String[] args) {
        String line = "66.249.64.30 - - [16/Dec/2018:01:41:56 -0800] \"GET /ads.txt HTTP/1.1\" 301 244 \"-\" \"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\"";
        line = line.substring(line.indexOf(" - - [") + 6);
        System.out.println(line.substring(0, line.indexOf(" ")));
        line = line.substring(line.indexOf("\"") + 1);

        System.out.println(line);

    }
}

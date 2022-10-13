package ies.lab2.e4.showquote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ImportData {

    public static HashMap<Integer, ShowData> fromFile(String filename) {
        HashMap<Integer, ShowData> data = new HashMap<>();

        Integer counter = 0;
        HashMap<String, Integer> auxAddedShows = new HashMap<>(); // So we have a unique id for each show

        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String raw = sc.nextLine();
                String[] split = raw.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String quote = split[1].replaceAll("\"", "");
                String show = split[2].replaceAll("\"", "");

                if (!auxAddedShows.containsKey(show)) {
                    Integer id = counter++;
                    auxAddedShows.put(show, id);
                    data.put(id, new ShowData(id, show));
                }
                Integer showId = auxAddedShows.get(show);
                data.get(showId).addQuote(quote);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " was not found.");
            System.exit(1);
        }
        return data;
    }

}

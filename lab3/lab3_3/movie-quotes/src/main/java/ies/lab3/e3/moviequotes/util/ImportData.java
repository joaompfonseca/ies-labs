package ies.lab3.e3.moviequotes.util;

import ies.lab3.e3.moviequotes.model.Movie;
import ies.lab3.e3.moviequotes.model.Quote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ImportData {

    public static List<Movie> fromFile(String filename) {
        List<Movie> movies = new ArrayList<>();

        Integer counter = 0;
        HashMap<String, Integer> auxAddedMovies = new HashMap<>(); // So we know the index of each movie in movies array

        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String raw = sc.nextLine();
                String[] split = raw.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String content = split[1].replaceAll("\"", "");
                String title = split[2].replaceAll("\"", "");
                Integer year = Integer.parseInt(split[3].replaceAll("\"", ""));

                if (!auxAddedMovies.containsKey(title)) {
                    auxAddedMovies.put(title, counter++);
                    movies.add(new Movie(title, year, new ArrayList<>()));
                }
                Integer index = auxAddedMovies.get(title);
                Movie movie = movies.get(index);
                List<Quote> quotes = movies.get(index).getQuotes();
                quotes.add(new Quote(content, movie));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " was not found.");
            System.exit(1);
        }
        return movies;
    }

}

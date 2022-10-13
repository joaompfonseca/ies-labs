package ies.lab2.e4.showquote;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GreetingRestController {

    private HashMap<Integer, ShowData> showData;
    private Random rand;

    public GreetingRestController() {
        showData = ImportData.fromFile("src/main/resources/static/data.csv"); // Imports movie quotes from file
        rand = new Random();
    }

    public Integer[] getShowIds() {
        return showData.keySet().toArray(Integer[]::new);
    }

    public ShowData getShow(Integer showId) {
        return showData.get(showId);
    }

    @GetMapping("/api/quote")
    public Quote quote() {
        Integer[] showIds = getShowIds();
        Integer showId = rand.nextInt(showIds.length);

        ShowData show = getShow(showId);
        Integer quoteIdx = rand.nextInt(show.numberOfQuotes());
        String quote = show.getQuote(quoteIdx);

        return new Quote(showId, quote);
    }

    @GetMapping("/api/shows")
    public List<Show> shows() {
        List<Show> ret = new ArrayList<>();

        for (Map.Entry<Integer, ShowData> kv : showData.entrySet()) {
            Integer showId = kv.getKey();
            String showTitle = kv.getValue().getTitle();
            ret.add(new Show(showId, showTitle));
        }

        return ret;
    }

    @GetMapping("/api/quotes")
    public ResponseEntity<Quote> quotes(@RequestParam(value = "show") Integer showId) {
        ShowData show = getShow(showId);
        if (show != null) {
            Integer quoteIdx = rand.nextInt(show.numberOfQuotes());
            String quote = show.getQuote(quoteIdx);

            return new ResponseEntity<>(new Quote(showId, quote), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
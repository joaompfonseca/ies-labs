package ies.lab2.e4.showquote;

import java.util.ArrayList;
import java.util.List;

public class ShowData {

    private Integer id;
    private String title;
    private List<String> quotes;

    public ShowData(Integer id, String title) {
        this.id = id;
        this.title = title;
        this.quotes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getQuote(Integer index) {
        return quotes.get(index);
    }

    public void addQuote(String quote) {
        quotes.add(quote);
    }

    public Integer numberOfQuotes() {
        return quotes.size();
    }
}

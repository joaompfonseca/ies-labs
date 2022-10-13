package ies.lab2.e4.showquote;

public class Quote {
    private Integer showId;
    private String quote;

    public Quote(Integer showId, String quote) {
        this.showId = showId;
        this.quote = quote;
    }

    public Integer getShowId() {
        return showId;
    }

    public String getQuote() {
        return quote;
    }
}

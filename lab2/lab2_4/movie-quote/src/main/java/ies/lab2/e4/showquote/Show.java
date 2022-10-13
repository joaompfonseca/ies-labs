package ies.lab2.e4.showquote;

public class Show {
    private Integer showId;
    private String showTitle;

    public Show(Integer showId, String showTitle) {
        this.showId = showId;
        this.showTitle = showTitle;
    }

    public Integer getShowId() {
        return showId;
    }

    public String getShowTitle() {
        return showTitle;
    }
}

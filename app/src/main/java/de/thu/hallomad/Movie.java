package de.thu.hallomad;

import androidx.annotation.NonNull;

public class Movie {
    public String title;
    public String year;
    public String type;
    public String imdbId;

    public Movie(String title, String year, String imdbId, String type) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.imdbId = imdbId;
    }

    @NonNull
    @Override
    public String toString() {
        return title + "(" + type + "," + year + ")";
    }
}

package org.mikolajczak.popularmovies.model;

public class Movie {

    private int moviedb_id;
    private String poster;
    private String title;
    private String releaseDate;
    private Double voteAvg;
    private String plotSynopsis;

    public Movie() {

    }

    public Movie(String poster, String title, String releaseDate, Double voteAvg, String plotSynopsis, int moviedb_id) {
        this.moviedb_id = moviedb_id;
        this.poster = poster;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public int getMoviedbId() {
        return moviedb_id;
    }
}

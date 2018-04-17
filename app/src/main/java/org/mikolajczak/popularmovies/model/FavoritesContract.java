package org.mikolajczak.popularmovies.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoritesContract {
    public static final String AUTHORITY = "org.mikolajczak.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String FAVORITES_PATH = "favorites";


    public static final class FavoritesEntry  {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(FAVORITES_PATH).build();

        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIEDB_ID = "moviedb_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_RELEASE = "release_date";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_PLOT = "plot";
    }
}

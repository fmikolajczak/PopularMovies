package org.mikolajczak.popularmovies.model;

import android.provider.BaseColumns;

public class FavoritesContract {
    public static final class FavoritesEntry  implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIEDB_ID = "moviedb_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_RELEASE = "release";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_PLOT = "plot";
    }
}

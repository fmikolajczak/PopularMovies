package org.mikolajczak.popularmovies.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.mikolajczak.popularmovies.model.FavoritesContract.FavoritesEntry;

public class FavoritesContentProvider extends ContentProvider {
    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;

    private FavoritesDbHelper mFavoritesDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.FAVORITES_PATH,
                FAVORITES);
        uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.FAVORITES_PATH + "/#",
                FAVORITES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoritesDbHelper = new FavoritesDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
            selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mFavoritesDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case FAVORITES:
                retCursor = db.query(FavoritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(FavoritesEntry.TABLE_NAME,
                        projection,
                        "moviedb_id=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder
                        );
                break;
            default:
                throw new UnsupportedOperationException("Not implemented");
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mFavoritesDbHelper.getWritableDatabase();

        Uri returnUri;

        if ( sUriMatcher.match(uri) != FAVORITES) {
            throw new UnsupportedOperationException("Not implemented");
        }

        long id = db.insert(FavoritesEntry.TABLE_NAME, null, values);
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(FavoritesEntry.CONTENT_URI, id);
        } else {
            throw  new android.database.SQLException("Failed to insert row into " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        if (sUriMatcher.match(uri) != FAVORITES_WITH_ID ) {
            throw new UnsupportedOperationException("Not implemented");
        }
        final SQLiteDatabase db = mFavoritesDbHelper.getWritableDatabase();

        String id = uri.getPathSegments().get(1);
        int deleted = db.delete(FavoritesEntry.TABLE_NAME, "moviedb_id=?", new String[]{id});

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}

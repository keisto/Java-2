package xyz.y_not.keiser_java2_4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class SweetsProvider extends ContentProvider {
    private static final String AUTHORITY   = "xyz.y_not.keiser_java2_4.SweetsProvider";
    private static final String BASE_PATH   = "sweets";
    public  static final Uri    CONTENT_URI = Uri.parse("content://" +
            AUTHORITY + "/" + BASE_PATH );

    private static final int SWEETS    = 1;
    private static final int SWEETS_ID = 2;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Sweet";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, SWEETS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SWEETS_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == SWEETS_ID){
            selection = DBOpenHelper.SWEETS_ID + "=" + uri.getLastPathSegment();
        }
        return database.query(DBOpenHelper.TABLE_SWEETS, DBOpenHelper.ALL_COLUMNS,
                selection, null, null, null, DBOpenHelper.SWEETS_CALORIES + " DESC");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_SWEETS, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_SWEETS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_SWEETS, values, selection, selectionArgs);
    }
}

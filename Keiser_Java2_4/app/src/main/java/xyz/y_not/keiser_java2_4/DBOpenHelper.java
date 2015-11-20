package xyz.y_not.keiser_java2_4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sweets.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_SWEETS     = "sweets";
    public static final String SWEETS_ID        = "_id";
    public static final String SWEETS_TITLE     = "title";
    public static final String SWEETS_CALORIES  = "calories";
    public static final String SWEETS_PROTEINS  = "proteins";
    public static final String SWEETS_CARBS     = "carbs";
    public static final String SWEETS_FATS      = "fats";

    // All Columns
    public static final String[] ALL_COLUMNS =
            {SWEETS_ID, SWEETS_TITLE, SWEETS_CALORIES, SWEETS_PROTEINS, SWEETS_CARBS, SWEETS_FATS};

    // Create Table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SWEETS + " (" +
                    SWEETS_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SWEETS_TITLE    + " TEXT, " +
                    SWEETS_CALORIES + " INTEGER," +
                    SWEETS_PROTEINS + " INTEGER, " +
                    SWEETS_CARBS    + " INTEGER, " +
                    SWEETS_FATS     + " INTEGER" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWEETS);
        onCreate(db);
    }
}

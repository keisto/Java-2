package xyz.y_not.keiser_java2_4;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int NEW_REQUEST_CODE = 1001;
    private CursorAdapter adapter;
    private String action;
    private TextView sweet;
    private TextView proteins;
    private TextView carbs;
    private TextView fats;
    private TextView calories;
    private String sweetFilter;

    private String title;
    private String pro;
    private String car;
    private String fat;
    private String cal;

    private String sweetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sweet    = (TextView) findViewById(R.id.title);
        proteins = (TextView) findViewById(R.id.proteins);
        carbs    = (TextView) findViewById(R.id.carbs);
        fats     = (TextView) findViewById(R.id.fats);
        calories = (TextView) findViewById(R.id.calories);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(SweetsProvider.CONTENT_ITEM_TYPE);
        action = Intent.ACTION_EDIT;
        sweetFilter = DBOpenHelper.SWEETS_ID + "=" + uri.getLastPathSegment();
        Cursor cursor = getContentResolver().query(uri,
                DBOpenHelper.ALL_COLUMNS, sweetFilter, null, null);
        cursor.moveToFirst();
        sweetId = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_ID));
        title = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_TITLE));
        cal = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_CALORIES));
        pro = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_PROTEINS));
        car = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_CARBS));
        fat = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_FATS));

        sweet.setText(title);
        calories.setText(cal + " Calories");
        proteins.setText(pro);
        carbs.setText(car);
        fats.setText(fat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
                deleteItem();
                break;
            case R.id.action_edit:
                editItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItem() {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show();
        getContentResolver().delete(SweetsProvider.CONTENT_URI, sweetFilter, null);
        setResult(RESULT_OK);
        finish();
    }
    private void editItem() {
        Intent intent = new Intent(DetailActivity.this, FormActivity.class);
        Uri uri = Uri.parse(SweetsProvider.CONTENT_URI + "/" + sweetId);
        intent.putExtra(SweetsProvider.CONTENT_ITEM_TYPE, uri);
        startActivityForResult(intent, NEW_REQUEST_CODE);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_REQUEST_CODE && resultCode == RESULT_OK){
            restart();
        }
    }
    private void restart() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, SweetsProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

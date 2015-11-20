package xyz.y_not.keiser_java2_4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListActivity extends Activity
implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int NEW_REQUEST_CODE = 1001;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new SweetsCursorAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                Uri uri = Uri.parse(SweetsProvider.CONTENT_URI + "/" + id);
                intent.putExtra(SweetsProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, NEW_REQUEST_CODE);
            }
        });

        restart();
    }

    private void insertSweet(String title, int cal, int pro, int car, int fat){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SWEETS_TITLE, title);
        values.put(DBOpenHelper.SWEETS_CALORIES, cal);
        values.put(DBOpenHelper.SWEETS_PROTEINS, pro);
        values.put(DBOpenHelper.SWEETS_CARBS, car);
        values.put(DBOpenHelper.SWEETS_FATS, fat);
        Uri sweetsUri = getContentResolver().insert(SweetsProvider.CONTENT_URI, values);
        Log.d("ListActivity: ", "Inserted sweet: " + sweetsUri.getLastPathSegment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sample:
                loadSample();
                break;
            case R.id.action_delete_all:
                deleteAll();
                break;
            case R.id.action_new:
                newItem();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void newItem() {
        Intent intent = new Intent(this, FormActivity.class);
        startActivityForResult(intent, NEW_REQUEST_CODE);
    }

    private void deleteAll() {
        // Alert User before deleting data
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            getContentResolver().delete(SweetsProvider.CONTENT_URI, null, null);
                            restart();
                            Toast.makeText(ListActivity.this, "All Data Deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?")
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    private void loadSample() {
        insertSweet("Cupcake", (2*4)+(29*4)+(2*9), 2, 29, 2);
        insertSweet("Chocolate Cake", (5*4)+(51*4)+(14*9), 5, 51, 14);
        insertSweet("Cherry Pie", (3*4)+(60*4)+(17*9), 3, 60, 17);
        restart();
    }

    private void restart() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_REQUEST_CODE && resultCode == RESULT_OK){
            restart();
        }
    }
}

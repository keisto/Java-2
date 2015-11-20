package xyz.y_not.keiser_java2_4;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends Activity {
    private String action;

    private EditText sweet;
    private EditText proteins;
    private EditText carbs;
    private EditText fats;
    private TextView calories;
    // OLD ITEMS
    private String oldSweet;
    private String oldProteins;
    private String oldCarbs;
    private String oldFats;
    private String oldCalories;

    private String sweetFilter;

    private String sweetString;
    private int proInt = 0;
    private int carInt = 0;
    private int fatInt = 0;
    private int calInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        sweet    = (EditText) findViewById(R.id.titleText);
        proteins = (EditText) findViewById(R.id.proText);
        carbs    = (EditText) findViewById(R.id.carText);
        fats     = (EditText) findViewById(R.id.fatText);
        calories = (TextView) findViewById(R.id.calText);

        proteins.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (proteins.getText().toString().trim().equals("")) proInt = 0;
                else if (carbs.getText().toString().trim().equals("")) carInt = 0;
                else if (fats.getText().toString().trim().equals("")) fatInt = 0;
                else {
                    proInt = Integer.parseInt(proteins.getText().toString().trim());
                    carInt = Integer.parseInt(carbs.getText().toString().trim());
                    fatInt = Integer.parseInt(fats.getText().toString().trim());
                    calInt = (proInt * 4) + (carInt * 4) + (fatInt * 9);
                    calories.setText(String.valueOf(calInt) + "Calories");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
        carbs.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (proteins.getText().toString().trim().equals("")) proInt = 0;
                else if (carbs.getText().toString().trim().equals("")) carInt = 0;
                else if (fats.getText().toString().trim().equals("")) fatInt = 0;
                else {
                    proInt = Integer.parseInt(proteins.getText().toString().trim());
                    carInt = Integer.parseInt(carbs.getText().toString().trim());
                    fatInt = Integer.parseInt(fats.getText().toString().trim());
                    calInt = (proInt * 4) + (carInt * 4) + (fatInt * 9);
                    calories.setText(String.valueOf(calInt) + "Calories");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
        fats.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (proteins.getText().toString().trim().equals("")) proInt = 0;
                else if (carbs.getText().toString().trim().equals("")) carInt = 0;
                else if (fats.getText().toString().trim().equals("")) fatInt = 0;
                else {
                    proInt = Integer.parseInt(proteins.getText().toString().trim());
                    carInt = Integer.parseInt(carbs.getText().toString().trim());
                    fatInt = Integer.parseInt(fats.getText().toString().trim());
                    calInt = (proInt * 4) + (carInt * 4) + (fatInt * 9);
                    calories.setText(String.valueOf(calInt) + " Calories");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(SweetsProvider.CONTENT_ITEM_TYPE);
        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle("Create Item");
        } else {
            action = Intent.ACTION_EDIT;
            sweetFilter = DBOpenHelper.SWEETS_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.ALL_COLUMNS, sweetFilter, null, null);
            cursor.moveToFirst();
            oldSweet    = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_TITLE));
            oldCalories = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_CALORIES));
            oldProteins = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_PROTEINS));
            oldCarbs    = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_CARBS));
            oldFats     = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SWEETS_FATS));

            sweet.setText(oldSweet);
            calories.setText(oldCalories + " Calories");
            proteins.setText(oldProteins);
            carbs.setText(oldCarbs);
            fats.setText(oldFats);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                finishSweet();
                break;
            case android.R.id.home:
                finishSweet();
                break;
            case R.id.action_delete:
                if (action.equals(Intent.ACTION_EDIT)){
                    deleteSweet();
                } else {
                    Toast.makeText(this, "Invalid Item", Toast.LENGTH_LONG).show();
                }
                break;
        }

        return true;
    }

    private void finishSweet(){
        if (action.equals(Intent.ACTION_INSERT)) {
            sweetString = sweet.getText().toString().trim();
            if (sweetString.length() == 0 ||
                    calories.getText().toString().equals("Calories")   ||
                    proteins.getText().toString().trim().length() == 0 ||
                    carbs.getText().toString().trim().length()    == 0 ||
                    fats.getText().toString().trim().length()     == 0 ||
                    calInt == 0 ){
                setResult(RESULT_CANCELED);
            } else {
                proInt = Integer.parseInt(proteins.getText().toString().trim());
                carInt = Integer.parseInt(carbs.getText().toString().trim());
                fatInt = Integer.parseInt(fats.getText().toString().trim());
                createSweet(sweetString, calInt, proInt, carInt, fatInt);
            }
        }
        if (action.equals(Intent.ACTION_EDIT)) {
            sweetString = sweet.getText().toString().trim();
            if (sweetString.length() == 0 ) {
                deleteSweet();
            } else if (oldSweet.equals(sweetString)) {
                setResult(RESULT_CANCELED);
            } else if ( proteins.getText().toString().trim().length() == 0 ||
                        carbs.getText().toString().trim().length()    == 0 ||
                        fats.getText().toString().trim().length()     == 0 ||
                        calInt == 0 ){
                    setResult(RESULT_CANCELED);
            } else {
                proInt = Integer.parseInt(proteins.getText().toString().trim());
                carInt = Integer.parseInt(carbs.getText().toString().trim());
                fatInt = Integer.parseInt(fats.getText().toString().trim());
                updateSweet(sweetString, calInt, proInt, carInt, fatInt);
            }
        }
        finish();
    }

    private void deleteSweet() {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show();
        getContentResolver().delete(SweetsProvider.CONTENT_URI, sweetFilter, null);
        setResult(RESULT_OK);
        finish();
    }

    private void updateSweet(String title, int cal, int pro, int car, int fat) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SWEETS_TITLE, title);
        values.put(DBOpenHelper.SWEETS_CALORIES, cal);
        values.put(DBOpenHelper.SWEETS_PROTEINS, pro);
        values.put(DBOpenHelper.SWEETS_CARBS, car);
        values.put(DBOpenHelper.SWEETS_FATS, fat);
        getContentResolver().update(SweetsProvider.CONTENT_URI, values, sweetFilter, null);
        setResult(RESULT_OK);
    }

    private void createSweet(String title, int cal, int pro, int car, int fat) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SWEETS_TITLE, title);
        values.put(DBOpenHelper.SWEETS_CALORIES, cal);
        values.put(DBOpenHelper.SWEETS_PROTEINS, pro);
        values.put(DBOpenHelper.SWEETS_CARBS, car);
        values.put(DBOpenHelper.SWEETS_FATS, fat);
        getContentResolver().insert(SweetsProvider.CONTENT_URI, values);
        Toast.makeText(this, "Item Updated", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);        
    }
    public void onBackPressed(){
        finishSweet();
    }
}

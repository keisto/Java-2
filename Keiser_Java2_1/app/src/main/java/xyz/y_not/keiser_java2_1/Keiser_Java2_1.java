package xyz.y_not.keiser_java2_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class Keiser_Java2_1 extends Activity {

    ConnectivityManager mgr;
    // Variables
    EditText input;
    Button submit;
    ArrayList<JSONObject> movieJson = new ArrayList<JSONObject>();
    int jData = 100;
    String jsonData = "";
    String setColor = "#000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keiser__java2_1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Set Variables
        input = (EditText) findViewById(R.id.input);
        submit = (Button) findViewById(R.id.submit);

        readJson();
    }

    public void comingSoon() throws JSONException {
        //Add Local Movies
        JSONObject movie1 = new JSONObject();
        JSONObject movie2 = new JSONObject();
        JSONObject movie3 = new JSONObject();
        JSONObject movie4 = new JSONObject();
        JSONObject movie5 = new JSONObject();
        JSONObject movie6 = new JSONObject();
        try {
            movie1.put("Title", "The Hunger Games: Mockingjay - Part 2");
            movie1.put("Plot", "After being symbolized as the \"Mockingjay\", " +
                    "Katniss Everdeen and District 13 engage in an all-out revolution " +
                    "against the autocratic Capitol.");
            movie1.put("Rated", "PG-13");
            movie1.put("imdbRating", "Not Rated");
            movie1.put("Released", "20 Nov 2015");
            movie1.put("Genre", "Adventure, Sci-Fi");
        //LINE       BREAK       LINE       BREAK       LINE       BREAK       LINE       BREAK
            movie2.put("Title", "The Night Before");
            movie2.put("Plot", "In New York City for their annual tradition of Christmas " +
                    "Eve debauchery, three lifelong best friends set out to find the " +
                    "Holy Grail of Christmas parties since their yearly reunion might " +
                    "be coming to an end.");
            movie2.put("Rated", "R");
            movie2.put("imdbRating", "Not Rated");
            movie2.put("Released", "20 Nov 2015");
            movie2.put("Genre", "Comedy");
        //LINE       BREAK       LINE       BREAK       LINE       BREAK       LINE       BREAK
            movie3.put("Title", "Legend");
            movie3.put("Plot", "The film tells the story of the identical twin gangsters" +
                    " Reggie and Ronnie Kray, two of the most notorious criminals in " +
                    "British history, and their organised crime empire in the East End " +
                    "of London during the 1960s.");
            movie3.put("Rated", "R");
            movie3.put("imdbRating", "Not Rated");
            movie3.put("Released", "20 Nov 2015");
            movie3.put("Genre", "Biography, Crime, Thriller");
        //LINE       BREAK       LINE       BREAK       LINE       BREAK       LINE       BREAK
            movie4.put("Title", "Creed");
            movie4.put("Plot", "The former World Heavyweight Champion Rocky Balboa serves " +
                    "as a trainer and mentor to Adonis Johnson, the son of his late friend and " +
                    "former rival Apollo Creed.");
            movie4.put("Rated", "PG-13");
            movie4.put("imdbRating", "Not Rated");
            movie4.put("Released", "25 Nov 2015");
            movie4.put("Genre", "Drama, Sport");
        //LINE       BREAK       LINE       BREAK       LINE       BREAK       LINE       BREAK
            movie5.put("Title", "Spectre");
            movie5.put("Plot", "A cryptic message from Bond's past sends him on a trail to " +
                    "uncover a sinister organization. While M battles political forces to " +
                    "keep the secret service alive, Bond peels back the layers of deceit to " +
                    "reveal the terrible truth behind SPECTRE.");
            movie5.put("Rated", "PG-13");
            movie5.put("imdbRating", "7.6");
            movie5.put("Released", "6 Nov 2015");
            movie5.put("Genre", "Action, Adventure, Thriller");
        //LINE       BREAK       LINE       BREAK       LINE       BREAK       LINE       BREAK
            movie6.put("Title", "The Peanuts Movie");
            movie6.put("Plot", "Snoopy embarks upon his greatest mission as he and his " +
                    "team take to the skies to pursue their arch-nemesis, while his best pal " +
                    "Charlie Brown begins his own epic quest back home.");
            movie6.put("Rated", "G");
            movie6.put("imdbRating", "8.3");
            movie6.put("Released", "6 Nov 2015");
            movie6.put("Genre", "Action, Adventure, Thriller");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movieJson.add(movie1);
        movieJson.add(movie2);
        movieJson.add(movie3);
        movieJson.add(movie4);
        movieJson.add(movie5);
        movieJson.add(movie6);
        updateList();
    }

    private class asyncTask extends AsyncTask<URL, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            String jsonString = null;
            URLConnection connection;
            for (URL queryURL : urls) {
                try {
                    connection = queryURL.openConnection();
                    jsonString = IOUtils.toString(connection.getInputStream());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JSONObject apiData;
            try {
                apiData = new JSONObject(jsonString.toString());
                if (apiData.getString("Response").equals("False")){
                    apiData = null;
                    isCancelled();
                } else {
                    return apiData;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                apiData = null;
            }
            return null;
        }
        protected void onPostExecute(JSONObject apiData) {
            if(apiData != null) {
                movieJson.add(apiData);
            }
            // Clear Input
            input.setText("");
            // Update List
            updateList();
        }
    }

    private void updateList() {
        MasterFragment frag = MasterFragment.newInstance();
        frag.setMoviesList(movieJson);
        frag.setTextColor(setColor);
        getFragmentManager().beginTransaction()
                .replace(R.id.master_fragment, frag, "Detail Frag : ").commit();
        // Save Data
        writeMovies();
    }

    public void onConnect() {
        try {
            String text = input.getText().toString();
            // Check White Space
            if(text.trim().equals("")){
                Toast.makeText(this, "Please Enter a Movie Title", Toast.LENGTH_LONG).show();
            } else {
                String search = text.replace(" ", "+");
                String baseURL = "http://www.omdbapi.com/?t=" + search + "&y=&plot=short&r=json";
                URL queryURL = new URL(baseURL);
                new asyncTask().execute(queryURL);
            }
        } catch (Exception e) {
            Log.e("ERROR: ", e.toString());
        }
    }

    // Check Internet Connection
    public void onSubmit(View view) throws IOException {
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "Connection: Mobile", Toast.LENGTH_SHORT).show();
                onConnect();
            } else if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "Connection: WIFI", Toast.LENGTH_SHORT).show();
                onConnect();
            }
        } else {
            Toast.makeText(this, "No Connection! Loading Defaults", Toast.LENGTH_SHORT).show();
            try {
                comingSoon();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMovies() {
        File external = getExternalFilesDir(null);
        File file = new File(external, "movies.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            // Write bytes to the stream
            for (int i = 0; i < movieJson.size(); i++) {
                osw.write(movieJson.get(i).toString() + "\n");
            }
            // Close the stream to save the file.
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clearData(){
        movieJson.clear();
        updateList();
    }
    private void readJson() {
        File external = getExternalFilesDir(null);
        File file = new File(external, "movies.txt");
        try {
            FileInputStream fin= new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fin);
            char[] data = new char[jData];
            int size;
            try {
                while ((size = isr.read(data))>0){
                    String readData = String.copyValueOf(data,0,size);
                    jsonData += readData;
                    data = new char[jData];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] breakup = jsonData.split("\n");
        for (int i = 0; i <breakup.length; i++) {
            try {
                JSONObject converter = new JSONObject(breakup[i]);
                movieJson.add(converter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keiser__java2_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent intent = new Intent();
            intent.setClass(this, PreferencesActivity.class);
            startActivityForResult(intent, 2015);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2015){
            SharedPreferences myPref =
                    PreferenceManager.getDefaultSharedPreferences(this);
            boolean red = myPref.getBoolean("Red", false);
            boolean white = myPref.getBoolean("White", false);
            boolean blue = myPref.getBoolean("Blue", false);
            boolean clear = myPref.getBoolean("Clear", false);
            boolean load = myPref.getBoolean("Default", false);

            if(red==true){
                setColor = "#990000";
                updateList();
            }
            if(white==true){
                setColor = "#FFFFFF";
                updateList();
            }
            if(blue==true){
                setColor = "#000099";
                updateList();
            }
            if(clear==true){
                clearData();
            }
            if(load==true){
                try {
                    comingSoon();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


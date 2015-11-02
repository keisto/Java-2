package xyz.y_not.keiser_java2_1;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment1 extends ListFragment {

    ArrayList<JSONObject> jsonMovies = new ArrayList<JSONObject>();
    ArrayList<String> movieTitles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    public static final String TAG = "Fragment1.TAG";

    public static Fragment1 newInstance(){
        Fragment1 frag = new Fragment1();
        return frag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieTitles.clear();
        for (int i = 0; i < jsonMovies.size(); i++) {
            try {
                movieTitles.add(jsonMovies.get(i).getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                movieTitles);
        setListAdapter(arrayAdapter);

    }

    public Fragment1() {
        super();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
//            title.setText(jsonMovies.get(position).getString("Title"));
//            plot.setText(jsonMovies.get(position).getString("Plot"));
//            rated.setText("Rated: " +
//                    jsonMovies.get(position).getString("Rated"));
//            released.setText("Released: " +
//                    jsonMovies.get(position).getString("Released"));
//            genre.setText("Genre: " +
//                    jsonMovies.get(position).getString("Genre"));
//            if (jsonMovies.get(position).getString("imdbRating") == "Not Rated") {
//                rating.setText("Not Available");
//            } else {
//                rating.setText("Rating: " +
//                        jsonMovies.get(position).getString("imdbRating") +
//                        " out of 10");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setJson(ArrayList jsonMovies) {
        this.jsonMovies = jsonMovies;
    }
}

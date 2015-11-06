package xyz.y_not.keiser_java2_1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private String textColor = "#000000";
    private int position;
    private ArrayList<JSONObject> moviesList = new ArrayList<JSONObject>();
    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    private TextView title;
    private TextView plot;
    private TextView rated;
    private TextView rating;
    private TextView genre;
    private TextView released;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_fragment, container, false);

        title = (TextView) view.findViewById(R.id.title);
        plot = (TextView) view.findViewById(R.id.plot);
        rated = (TextView) view.findViewById(R.id.rated);
        rating = (TextView) view.findViewById(R.id.rating);
        genre = (TextView) view.findViewById(R.id.genre);
        released = (TextView) view.findViewById(R.id.released);

        title.setText("");
        plot.setText("");
        rated.setText("");
        rating.setText("");
        genre.setText("");
        released.setText("");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title.setTextColor(Color.parseColor(textColor));
        plot.setTextColor(Color.parseColor(textColor));
        rated.setTextColor(Color.parseColor(textColor));
        released.setTextColor(Color.parseColor(textColor));
        genre.setTextColor(Color.parseColor(textColor));
        if(moviesList.size()==0){
            plot.setText("Select or Add a Movie");
        } else {
            try {
                title.setText(moviesList.get(position).getString("Title"));
                plot.setText(moviesList.get(position).getString("Plot"));
                rated.setText("Rated: " +
                        moviesList.get(position).getString("Rated"));
                released.setText("Released: " +
                        moviesList.get(position).getString("Released"));
                genre.setText("Genre: " +
                        moviesList.get(position).getString("Genre"));

            } catch (JSONException e) {
             e.printStackTrace();
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<JSONObject> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(ArrayList<JSONObject> moviesList) {
        this.moviesList = moviesList;
    }
}
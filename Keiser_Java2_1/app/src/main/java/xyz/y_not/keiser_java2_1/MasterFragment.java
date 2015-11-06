package xyz.y_not.keiser_java2_1;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


    public class MasterFragment extends ListFragment {
        private ArrayList<JSONObject> moviesList = new ArrayList<JSONObject>();
        private ArrayList<String> titlesList = new ArrayList<String>();
        private ListAdapter arrayAdapter;
        private String textColor;

        public static MasterFragment newInstance() {
            MasterFragment frag = new MasterFragment();
            return frag;
        }
        public void setMoviesList(ArrayList _moviesList) {
            this.moviesList = _moviesList;
        }


        @Override
        public void onActivityCreated(Bundle _savedInstanceState) {
            super.onActivityCreated(_savedInstanceState);

            for (int i = 0; i < moviesList.size(); i++) {
                try {
                    titlesList.add(moviesList.get(i).getString("Title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        titlesList);
                setListAdapter(arrayAdapter);
            }
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            DetailFragment details = new DetailFragment();
            details.setMoviesList(moviesList);
            details.setTextColor(textColor);
            details.setPosition(position);
            getFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, details, "Movie Frag : ").commit();
            Toast.makeText(
                    getActivity(),
                    getListView().getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show();
        }

        public String getTextColor() {
            return textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = textColor;
        }
    }
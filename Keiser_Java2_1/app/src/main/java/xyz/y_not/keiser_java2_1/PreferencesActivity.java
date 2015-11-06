package xyz.y_not.keiser_java2_1;

import android.app.Activity;
import android.os.Bundle;

import java.util.prefs.Preferences;

public class PreferencesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreference())
                .commit();

    }
}

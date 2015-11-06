package xyz.y_not.keiser_java2_1;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MyPreference extends PreferenceFragment {

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

package android.hioa.s178816_s188098_mappe2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by marhag on 19.10.14.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

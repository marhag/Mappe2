package android.hioa.s178816_s188098_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

/**
 * Created by marhag on 19.10.14.
 */
public class PrefsFragment extends PreferenceFragment{

    final CheckBoxPreference cbSms = (CheckBoxPreference) findPreference("checkbox_preference_sms");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final SavedVariables sv = new SavedVariables(getActivity());

        final CheckBoxPreference pref = (CheckBoxPreference) findPreference("checkbox_preference_sms");
        pref.setChecked(sv.getService()); // check whats saved
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {

                if (pref.isChecked()) {
                    Log.d("Service er på", "true");
                    sv.setService(true);
                    startService();
                } else {
                    Log.d("Service er av", "false");
                    sv.setService(false);
                    stopService();
                }

                return true;
            }
        });

        final EditTextPreference editTextPreference =  (EditTextPreference)findPreference("PREF_EDITTEXT_MESSAGE");
        editTextPreference.setText("Saved");// check whats saved
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                EditTextPreference etp = (EditTextPreference) preference;

                String newHostValue = newValue.toString();
                Log.i("Service ", newHostValue);

                return true;
            }
        });
        //0 = Norwegian
        //1 = English
        ListPreference languagelist = (ListPreference) findPreference("language");
        languagelist.setValueIndex(sv.getLanguageValue());// check whats saved
        languagelist.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                sv.setLanguageValue(Integer.parseInt(newValue.toString()));
                Log.d("Service selected",newValue.toString());
                return true;


            }

        });
    }
    public void startService() {
        Intent intent = new Intent();
        intent.setAction ("android.hioa.s178816_s188098_mappe2.mybroadcastreceiver");
        getActivity().sendBroadcast(intent);
    }
    public void stopService()
    {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pSmsIntent = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), SMSService.class), 0);
        alarmManager.cancel(pSmsIntent);
    }

}

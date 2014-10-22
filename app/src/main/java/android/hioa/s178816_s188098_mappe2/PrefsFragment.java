package android.hioa.s178816_s188098_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

import java.util.Locale;

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
                sv.saveState();
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
                sv.saveState();
                return true;
            }
        });

        final TimePreference timePreference = (TimePreference)findPreference("TIMEPREF");
        timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                TimePreference tp = (TimePreference)preference;
                int[] time = tp.getCalendar();
                sv.setHour(time[0]);
                sv.setMin(time[1]);
                sv.saveState();
                //resets the alarmmanager with the new time
                stopService();
                startService();
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
                Log.d("Service selected", newValue.toString());
                setLanguage(Integer.parseInt(newValue.toString()));
                sv.saveState();
                return true;
            }

        });
    }

    public void setLanguage(int langCode) {
        String lang;
        switch(langCode){
            case 1:
                lang = "en";
                break;
            default:
                lang = "no";
        }
        Locale newLoc = new Locale(lang);
        Locale.setDefault(newLoc);
        Configuration config = new Configuration();
        config.locale = newLoc;
        getResources().updateConfiguration(config,null);

        Intent intent = new Intent(getActivity(), Settings.class);
        startActivity(intent);
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

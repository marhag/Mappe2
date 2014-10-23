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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final SavedVariables sv = new SavedVariables(getActivity());

        final CheckBoxPreference pref = (CheckBoxPreference) findPreference("checkbox_preference_sms");
        //Log.d("service er",sv.getService() + "");
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
                //sv.saveState();
                return true;
            }
        });

        ListPreference languagelist = (ListPreference) findPreference("language");
        languagelist.setValueIndex(currentLanguage());// check whats saved
        languagelist.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                setLanguage(Integer.parseInt(newValue.toString()));

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
        getActivity().finish();
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

    public int currentLanguage(){
        String langCode = Locale.getDefault().getLanguage();
        if(langCode.equals("no"))
            return 0;
        else if(langCode.equals("en"))
            return 1;
        else
            return 0;
    }
}

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

        final EditTextPreference editTextPreference =  (EditTextPreference)findPreference("PREF_EDITTEXT_MESSAGE");
        String text = (sv.getLanguageValue()==0)?sv.getChosenLangNor():sv.getChosenLangEng();
        if(text=="")
            text = getActivity().getString(R.string.smsDefault);
        editTextPreference.setText(text);// check whats saved
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                EditTextPreference etp = (EditTextPreference) preference;
                if(sv.getLanguageValue()==0)
                {
                    sv.setChosenLangNor(newValue.toString());
                }
                else
                    sv.setChosenLangEng(newValue.toString());
                //sv.saveState();
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
                //sv.saveState();
                //resets the alarmManager with the new time
                if(sv.getService()) {
                    stopService();
                    startService();
                }
                return true;
            }
        });


        ListPreference languagelist = (ListPreference) findPreference("language");
        languagelist.setValueIndex(sv.getLanguageValue());// check for active language
        languagelist.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                setLanguage(Integer.parseInt(newValue.toString()), sv);

                return true;
            }

        });
    }

    public void setLanguage(int langCode, SavedVariables sv) {
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

        sv.setLanguageValue(langCode);//delete
        Log.d("Service selected", langCode + "");

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

}

package android.hioa.s178816_s188098_mappe2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by marhag on 21.10.14.
 */
public class SavedVariables {
    private Context c;
    private final String FILENAME = "variables_file";
    private String chosenLangNor,chosenLangEng;
    private boolean service;
    private int hour,min;

    public SavedVariables(Context c)
    {
        this.c = c;
        loadState();
    }

    public void saveState() {
        SharedPreferences settings = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("hour",hour);
        editor.putInt("min",min);

        editor.putString("chosenLangNor", chosenLangNor);
        editor.putString("chosenLangEng",chosenLangEng);

        editor.putBoolean("service",service);

        // Commit the edits!
        editor.commit();
    }

	/*
	 * Methode for loading the var's back
	 */

    public void loadState() {
        SharedPreferences settings = c.getSharedPreferences(FILENAME, 0);

        hour = settings.getInt("hour", 14);
        min = settings.getInt("min",30);

        chosenLangNor = settings.getString("chosenLangNor","");
        chosenLangEng = settings.getString("chosenLangEng","");

        service = settings.getBoolean("service", true);

    }

    public String getChosenLangNor() {
        return chosenLangNor;
    }

    public void setChosenLangNor(String chosenLangNor) {
        this.chosenLangNor = chosenLangNor;
        saveState();
    }

    public String getChosenLangEng() {
        return chosenLangEng;
    }

    public void setChosenLangEng(String chosenLangEng) {
        this.chosenLangEng = chosenLangEng;
        saveState();
    }

    public boolean getService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
        saveState();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        saveState();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        saveState();
    }
}

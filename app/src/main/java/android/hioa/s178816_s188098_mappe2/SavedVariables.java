package android.hioa.s178816_s188098_mappe2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marhag on 21.10.14.
 */
public class SavedVariables {
    private Context c;
    private final String FILENAME = "variables_file";
    private int languageValue;
    private String chosenLangNor,chosenLangEng;
    private boolean service;

    public SavedVariables(Context c)
    {
        this.c = c;
        loadState();
    }
    public void saveState() {
        SharedPreferences settings = c.getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("languageValue", languageValue);

        editor.putString("chosenLangNor",chosenLangNor);
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

        languageValue = settings.getInt("languageValue",1);

        chosenLangNor = settings.getString("chosenLangNor","");
        chosenLangEng = settings.getString("chosenLangEng","");

        service = settings.getBoolean("service",true);

    }

    public int getLanguageValue() {
        return languageValue;
    }

    public void setLanguageValue(int languageValue) {
        this.languageValue = languageValue;
    }

    public String getChosenLangNor() {
        return chosenLangNor;
    }

    public void setChosenLangNor(String chosenLangNor) {
        this.chosenLangNor = chosenLangNor;
    }

    public String getChosenLangEng() {
        return chosenLangEng;
    }

    public void setChosenLangEng(String chosenLangEng) {
        this.chosenLangEng = chosenLangEng;
    }

    public boolean getService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }
}

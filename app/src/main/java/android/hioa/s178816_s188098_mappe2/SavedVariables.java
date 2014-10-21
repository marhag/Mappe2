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

    public SavedVariables(Context c)
    {
        this.c = c;
        loadState();
    }
    public void saveState() {
        SharedPreferences settings = c.getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("languageValue", languageValue);

        // Commit the edits!
        editor.commit();
    }

	/*
	 * Methode for loading the var's back
	 */

    public void loadState() {
        SharedPreferences settings = c.getSharedPreferences(FILENAME, 0);

        languageValue = settings.getInt("languageValue",1);


    }

    public int getLanguageValue() {
        return languageValue;
    }

    public void setLanguageValue(int languageValue) {
        this.languageValue = languageValue;
    }
}

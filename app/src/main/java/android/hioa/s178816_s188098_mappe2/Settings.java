package android.hioa.s178816_s188098_mappe2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;


public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_quit:
                Intent iQuit = new Intent();
                setResult(RESULT_OK, iQuit);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setLanguage(String lang) {
        Locale newLoc = new Locale(lang);
        Locale.setDefault(newLoc);
        Configuration config = new Configuration();
        config.locale = newLoc;
        getResources().updateConfiguration(config,null);

        //Restarter aktiviteten for å oppdatere språket i nåværende vindu.
        startActivity(new Intent(this, Settings.class));
        finish();
    }
}

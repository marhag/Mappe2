package android.hioa.s178816_s188098_mappe2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends FragmentActivity {

    private MenuFragment listFragment;
    private CreatePerson cp;
    private SavedVariables sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);
        /*Person p = new Person("Mons","Monsen", 12341234,"26/11/1993");
        p.setCustomMessage("NÅ SKAL VI SE HER");

        db.addPerson(p);
        db.addPerson(new Person("Per","Nilsen", 12341234,"01/10/2004"));
        db.addPerson(new Person("Nils","Pettersen", 12341234,"20/10/2004"));*/
        for(Person p : db.getAllPersons())
            Log.d("Denne finnes",p.getFirstname() + " " + p.getDayMonth());

        if(savedInstanceState!=null)
        {

        }
        else
        {
            listFragment = new MenuFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container,listFragment);
            transaction.commit();
            sv = new SavedVariables(this);
        }

        final Button createBtn = (Button)findViewById(R.id.regNew);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToCreate(new Person(),0);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                changeFragment(0,null);
                return true;
            case R.id.action_settings:
                goToSettings();
                return true;
            case R.id.action_quit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //can be used for changing fragments
    public void changeFragment(int i, Person p)
    {
        TextView headline = (TextView)findViewById(R.id.headline);

        switch (i) {
            case 1:
                changeToCreate(p,1);
                return;
            default:
                getActionBar().setDisplayHomeAsUpEnabled(false);
                headline.setText(getString(R.string.overview));
                findViewById(R.id.regNew).setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();
                listFragment.updateList();
                return;
        }

    }

    public void changeToCreate(Person p,int i)
    {
        cp = new CreatePerson();
        Bundle args = new Bundle();
        args.putInt("id",p.getId());
        args.putString("firstname",p.getFirstname());
        args.putString("lastname",p.getLastname());
        args.putInt("phone", p.getMobile());
        args.putString("date",p.getBday());
        args.putString("message",p.getCustomMessage());
        if(i==1)
            args.putString("type","EDIT");
        else
            args.putString("type","CREATE");

        cp.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, cp);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToSettings()
    {
        Intent settings = new Intent(this, Settings.class);
        startActivityForResult(settings, 101);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            changeFragment(0,null);
        else
            super.onBackPressed();
    }

    public SavedVariables getSv() {
        return sv;
    }

    public void saveSv() {
        sv.saveState();
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveSv();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        saveSv();
    }


    //service starts at boot, only used for testing
    /*public void startService() {
        Intent intent = new Intent();
        intent.setAction ("android.hioa.s178816_s188098_mappe2.mybroadcastreceiver");
        sendBroadcast(intent);
    }*/

    //Avslutter appen fra Settings
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if(resultCode == RESULT_OK)
                finish();
        }
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
    }
}

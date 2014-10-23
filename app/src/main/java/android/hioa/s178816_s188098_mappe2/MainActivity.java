package android.hioa.s178816_s188098_mappe2;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
        db.addPerson(new Person("Nils","Pettersen", 12341234,"20/10/2004"));
        for(Person p : db.getAllPersons())
            Log.d("Denne finnes",p.getFirstname() + " " + p.getDayMonth());*/
        listFragment = new MenuFragment();
        if(savedInstanceState!=null)
        {
        }
        else{
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, listFragment, "liste");
            transaction.commit();
        }
            //sv = new SavedVariables(this);
        //}

        sv = new SavedVariables(this);
        final Button createBtn = (Button)findViewById(R.id.regNew);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToCreate(new Person(),0);
            }
        });

        final Button editBtn = (Button)findViewById(R.id.edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageOverlay();
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
    public void changeToMenu()
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, listFragment);
        transaction.commit();
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
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            changeFragment(0, null);
        }
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


    public void startService() {
        Intent intent = new Intent();
        intent.setAction ("android.hioa.s178816_s188098_mappe2.mybroadcastreceiver");
        this.sendBroadcast(intent);
    }
    public void stopService()
    {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pSmsIntent = PendingIntent.getService(this, 0, new Intent(this, SMSService.class), 0);
        alarmManager.cancel(pSmsIntent);
    }

    //Avslutter appen fra Settings
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if(resultCode == RESULT_OK)
                finish();
        }
    }
    private void showMessageOverlay() {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.set_message);

        SavedVariables saved = new SavedVariables(this);

        final EditText editMessage = (EditText)dialog.findViewById(R.id.serviceMessage);
        String text = (currentLanguage()==0)?sv.getChosenLangNor():sv.getChosenLangEng();
        if(text=="")
            text = this.getString(R.string.smsDefault);
        editMessage.setText(text);

        final TimePicker time = (TimePicker)dialog.findViewById(R.id.timePicker);
        time.setIs24HourView(true);
        time.setCurrentHour(saved.getHour());
        time.setCurrentMinute(saved.getMin());

        final Button btn = (Button)dialog.findViewById(R.id.confirmBtn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(currentLanguage()==0)
                {
                    sv.setChosenLangNor(editMessage.getText().toString());
                }
                else
                    sv.setChosenLangEng(editMessage.getText().toString());
                sv.setHour(time.getCurrentHour());
                sv.setMin(time.getCurrentMinute());

                if(sv.getService()) {
                    stopService();
                    startService();
                }
                dialog.dismiss();
            }
        });
        dialog.show();

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

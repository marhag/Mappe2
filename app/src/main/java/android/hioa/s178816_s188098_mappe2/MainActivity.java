package android.hioa.s178816_s188098_mappe2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;


public class MainActivity extends FragmentActivity {

    private MenuFragment listFragment;
    private CreatePerson cp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* DBHandler db = new DBHandler(this);
        Person p = new Person("Mons","Monsen", 12341234,"26/11/1993");
        p.setCustomMessage("NÅ SKAL VI SE HER");

        db.addPerson(p);
        db.addPerson(new Person("Per","Nilsen", 12341234,"01/10/2004"));*/

        if(savedInstanceState!=null)
        {

        }
        else
        {
            listFragment = new MenuFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container,listFragment);
            transaction.commit();
           // getSupportFragmentManager().beginTransaction()
                   // .add(R.id.container, listFragment).commit();
        }


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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //can be used for changing fragments
    public void changeFragment(int i, Person p)
    {

        switch (i) {

            case 1:
                changeToCreate(p,1);
                return;
            default:
                FragmentManager fm = getSupportFragmentManager();
                fm.popBackStack();
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
        transaction.addToBackStack("main");
        transaction.commit();
    }
}

package android.hioa.s178816_s188098_mappe2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;


public class MainActivity extends FragmentActivity {

    private MenuFragment listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* DBHandler db = new DBHandler(this);
        Person p = new Person("Mons","Monsen", 12341234,"dato");
        p.setCustomMessage("NÅ SKAL VI SE HER");

        db.addPerson(p);
        db.addPerson(new Person("Per","Nilsen", 12341234,"dato"));*/

        if(savedInstanceState!=null)
        {

        }
        else
        {
            listFragment = new MenuFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listFragment).commit();
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

    }
}

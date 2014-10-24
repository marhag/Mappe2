package android.hioa.s178816_s188098_mappe2;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class ContactList extends ListFragment {

	TypedArray menuIcons;

    DBHandler db;
	ContactListAdapter adapter;
	private List<Person> menuListPersons;

    public ContactList() {
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
        if(savedInstanceState != null){
           //saved stuff
        }else{
            //else
        }

        updateList();
	}

    public void updateList(Context c){
        db = new DBHandler(c);
        menuListPersons = db.getAllPersons();

        adapter = new ContactListAdapter(getActivity(), menuListPersons);
        setListAdapter(adapter);
    }
    public void updateList(){
        menuListPersons = db.getAllPersons();

        adapter = new ContactListAdapter(getActivity(), menuListPersons);
        setListAdapter(adapter);
    }

    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		// Notify the parent activity of selected person
		Person person = menuListPersons.get(position);
        changeFragment(person);
	}

    public void changeFragment(Person p)
    {
        ((MainActivity)getActivity()).changeFragment(1,p);
    }

}
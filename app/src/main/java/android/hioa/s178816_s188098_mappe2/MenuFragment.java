package android.hioa.s178816_s188098_mappe2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class MenuFragment extends ListFragment {

	TypedArray menuIcons;

	MenuListAdapter adapter;
	private List<Person> menuListPersons;

    public MenuFragment() {
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler(getActivity());
        if(savedInstanceState != null){
           //saved stuff
        }else{
            //else
        }
        menuListPersons = db.getAllPersons();

        adapter = new MenuListAdapter(getActivity(), menuListPersons);
		setListAdapter(adapter);

	}

    public void updateList(){
        adapter.notifyDataSetChanged();
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
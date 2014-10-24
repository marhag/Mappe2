package android.hioa.s178816_s188098_mappe2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {

    private Context context;
    private List<Person> menuListPerson;

    ContactListAdapter(Context context, List<Person> menuListPerson) {
        this.context = context;
        this.menuListPerson = menuListPerson;
    }

    @Override
    public int getCount() {

        return menuListPerson.size();
    }

    @Override
    public Object getItem(int position) {

        return menuListPerson.get(position);
    }

    @Override
    public long getItemId(int position) {

        return menuListPerson.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.contact_list_item, null);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.Name);
        TextView txtPhone = (TextView) convertView.findViewById(R.id.Phone);
        
        Person person = menuListPerson.get(position);

        txtName.setText(person.getFirstname()+" " + person.getLastname());
        txtPhone.setText(person.getMobile()+"");

        return convertView;
    }

}
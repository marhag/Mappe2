package android.hioa.s178816_s188098_mappe2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by marhag on 18.10.14.
 */
public class EditContact extends Fragment{
    private int id,phone;
    private String firstname, lastname, date,message, type;

    private EditText editFirst,editLast,editPhone,editMessage;
    private DatePicker datePicker;
    private DBHandler db;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new DBHandler(getActivity());

        getActivity().findViewById(R.id.regNew).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.edit).setVisibility(View.INVISIBLE);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
            phone = savedInstanceState.getInt("phone");
            firstname = savedInstanceState.getString("firstname");
            lastname = savedInstanceState.getString("lastname");
            date = savedInstanceState.getString("date");
            message = savedInstanceState.getString("message");
            type = savedInstanceState.getString("type");


        } else {
            id = getArguments().getInt("id");
            phone = getArguments().getInt("phone");
            firstname = getArguments().getString("firstname");
            lastname = getArguments().getString("lastname");
            date = getArguments().getString("date");
            message = getArguments().getString("message");
            type = getArguments().getString("type");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_contact, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        updateFragment();

    }

    public void updateFragment() {
        //adding up btn in actionbar
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        editFirst = (EditText)getActivity().findViewById(R.id.editFirstname);
        editLast = (EditText)getActivity().findViewById(R.id.editLastname);
        editPhone = (EditText)getActivity().findViewById(R.id.editPhone);
        editMessage = (EditText)getActivity().findViewById(R.id.editMessage);

        datePicker = (DatePicker)getActivity().findViewById(R.id.datePicker);

        int year=0;
        int month=0;
        int day = 0;

        if(date == null) // new Person - show date today
        {
            Calendar cal= Calendar.getInstance();
            year=cal.get(Calendar.YEAR);
            month=cal.get(Calendar.MONTH);
            day=cal.get(Calendar.DAY_OF_MONTH);
        }
        else // edit Person - show registered birthday
        {
            String[] dateArray = date.split("/");
            year = Integer.parseInt(dateArray[2]);
            month = Integer.parseInt(dateArray[1]) - 1;
            day = Integer.parseInt(dateArray[0]);
        }
        datePicker.updateDate(year, month, day);

        editFirst.setText(firstname);
        editLast.setText(lastname);
        if(phone==0)
            editPhone.setText("");
        else
            editPhone.setText(phone+"");
        editMessage.setText(message);

        //Tilpass header og knapper
        TextView headline = (TextView)getActivity().findViewById(R.id.headline);
        if(type == "EDIT") {
            headline.setText(getString(R.string.editContact));
            getActivity().findViewById(R.id.deletePersonBtn).setVisibility(View.VISIBLE);
        }
        else {
            headline.setText(getString(R.string.addContact));
            getActivity().findViewById(R.id.deletePersonBtn).setVisibility(View.GONE);
        }

        final Button regBtn = (Button)getActivity().findViewById(R.id.addPersonBtn);
        regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validateInput()) {
                    savePerson();
                    //getActivity().getFragmentManager().popBackStack();
                    ((MainActivity)getActivity()).changeFragment(0,null);
                }
            }
        });

        final Button delBtn = (Button)getActivity().findViewById(R.id.deletePersonBtn);
        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deletePerson();
                //getActivity().getFragmentManager().popBackStack();
                ((MainActivity)getActivity()).changeFragment(0,null);
            }
        });

    }

    public void deletePerson() {
        Person person = db.getPerson(id);
        db.deletePerson(person);
    }

    public void savePerson()
    {
        Person person = new Person();
        //set all fields of Person
        if(type.equals("EDIT"))
            person.setId(id);
        person.setFirstname(editFirst.getText().toString());
        person.setLastname(editLast.getText().toString());
        person.setMobile(Integer.parseInt(editPhone.getText().toString()));
        person.setCustomMessage(editMessage.getText().toString());
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        person.setBday(day+ "/" + month + "/" + year);
        person.setDayMonth(day+ "/" + month);

        if(type.equals("CREATE"))
            db.addPerson(person);
        else
            db.updatePerson(person);
    }

    public boolean validateInput(){
        boolean valid = true;
        String error = "";

        if(!editFirst.getText().toString().matches("[A-Åa-å \\-]{1,30}"))
            error = "Fornavn må være bokstaver eller mellomrom. Maks 30 tegn.";
        else if(!editLast.getText().toString().matches("[A-Åa-å \\-]{1,30}"))
            error = "Etternavn må være bokstaver eller mellomrom. Maks 30 tegn.";
        else if(!editPhone.getText().toString().matches("\\d{8}"))
            error = "Telefonnummer må være 8 siffer uten mellomrom.";

        if(error != "")
            valid = false;

        if(!valid){
            Toast toast = Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT);
            toast.show();
        }

        return valid;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //reads edit-area
        String pho = editPhone.getText().toString();
        int nr = (pho.equals(""))?0:Integer.parseInt(pho);
        phone = nr;
        firstname = editFirst.getText().toString();
        lastname = editLast.getText().toString();
        message = editMessage.getText().toString();
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        date =  day+ "/" + month + "/" + year;
        //saves
        outState.putInt("id",id);
        outState.putInt("phone",phone);
        outState.putString("firstname",firstname);
        outState.putString("lastname",lastname);
        outState.putString("date",date);
        outState.putString("message",message);
        outState.putString("type",type);
    }
}

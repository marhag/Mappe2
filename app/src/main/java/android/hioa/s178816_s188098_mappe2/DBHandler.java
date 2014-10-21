package android.hioa.s178816_s188098_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHandler  extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "Personreg";
 
    // Contacts table name
    private static final String TABLE_PERSONS = "Persons";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PH_NO = "telefon";
    private static final String KEY_BDATE = "date";
    private static final String KEY_DAYMONTH = "daymonth";
    private static final String KEY_MESSAGE = "message";
 
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + TABLE_PERSONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"+ KEY_PH_NO + " TEXT,"
                + KEY_BDATE + " TEXT,"+ KEY_DAYMONTH + " TEXT,"
                + KEY_MESSAGE + " Text)";
        db.execSQL(CREATE_PERSONS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new person
    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, person.getFirstname()); // Person firstname
        values.put(KEY_LASTNAME, person.getLastname()); // Person lastname
        values.put(KEY_PH_NO, person.getMobile()); // Person mobile
        values.put(KEY_BDATE, person.getBday()); // Person bday
        values.put(KEY_DAYMONTH, person.getDayMonth()); // Person day and month
        values.put(KEY_MESSAGE, person.getCustomMessage()); // Person custom message
 
        // Inserting Row
        db.insert(TABLE_PERSONS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single person
    public Person getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PERSONS, new String[] { KEY_ID,
                KEY_FIRSTNAME,KEY_LASTNAME, KEY_PH_NO, KEY_BDATE,KEY_DAYMONTH, KEY_MESSAGE },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Person person = new Person(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)),cursor.getString(4));
        person.setDayMonth(cursor.getString(5));
        person.setCustomMessage(cursor.getString(6));
        // return person
        return person;
    }
     
    // Getting All Contacts
    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSONS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setFirstname(cursor.getString(1));
                person.setLastname(cursor.getString(2));
                person.setMobile(Integer.parseInt(cursor.getString(3)));
                person.setBday(cursor.getString(4));
                person.setDayMonth(cursor.getString(5));
                person.setCustomMessage(cursor.getString(6));
                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
 
        // return person list
        return personList;
    }
 
    // Updating single person
    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, person.getFirstname());
        values.put(KEY_LASTNAME, person.getLastname());
        values.put(KEY_PH_NO, person.getMobile());
        values.put(KEY_BDATE,person.getBday());
        values.put(KEY_DAYMONTH,person.getDayMonth());
        values.put(KEY_MESSAGE,person.getCustomMessage());
 
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(person.getId()) });
    }
 
    // Deleting single person
    public void deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONS, KEY_ID + " = ?",
                new String[] { String.valueOf(person.getId()) });
        db.close();
    }
 
 
    // Getting person Count
    public int getTotalCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    // Getting All Contacts
    public List<Person> getAllPersonsWithBirthday() {
        List<Person> personList = new ArrayList<Person>();

        //gets today
        Calendar cal= Calendar.getInstance();
        String month=(cal.get(Calendar.MONTH) +1)+"";
        String day=cal.get(Calendar.DAY_OF_MONTH) + "";
        String daymonth = day+"/"+month;
        Log.d("dagens dato",daymonth);

        String selectQuery = "SELECT  * FROM " + TABLE_PERSONS + " WHERE "
                + KEY_DAYMONTH + " = '" +daymonth+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setFirstname(cursor.getString(1));
                person.setLastname(cursor.getString(2));
                person.setMobile(Integer.parseInt(cursor.getString(3)));
                person.setBday(cursor.getString(4));
                person.setDayMonth(cursor.getString(5));
                person.setCustomMessage(cursor.getString(6));
                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }

        // return person list
        return personList;
    }
 
}

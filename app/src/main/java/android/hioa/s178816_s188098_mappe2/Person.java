package android.hioa.s178816_s188098_mappe2;

/**
 * Created by marhag on 16.10.14.
 */
public class Person {
    private int id;
    private String firstname,lastname,mobile;
    private String bday; // DD/MM/YYYY - can use Date


    public Person()
    {}
    public Person(String fname, String lname, String mob, String day)
    {
        firstname = fname;
        lastname = lname;
        mobile = mob;
        bday  = day;
    }
    public Person(int i,String fname, String lname, String mob, String day)
    {
        id = i;
        firstname = fname;
        lastname = lname;
        mobile = mob;
        bday  = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }
}

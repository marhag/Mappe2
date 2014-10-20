package android.hioa.s178816_s188098_mappe2;

/**
 * Created by marhag on 16.10.14.
 */
public class Person {
    private int id;
    private String firstname;
    private String lastname;
    private int mobile;
    private String bday; // DD/MM/YYYY - can use Date
    private String dayMonth;
    private String customMessage = "";


    public Person()
    {}
    public Person(String fname, String lname, int mob, String day)
    {
        firstname = fname;
        lastname = lname;
        mobile = mob;
        bday  = day;
        String[] dm = day.split("/");
        dayMonth = dm[0] + "/" + dm[1];
    }
    public Person(int i,String fname, String lname, int mob, String day)
    {
        id = i;
        firstname = fname;
        lastname = lname;
        mobile = mob;
        bday  = day;
        String[] dm = day.split("/");
        dayMonth = dm[0] + "/" + dm[1];
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

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
        //String[] dm = bday.split("/");
        //dayMonth = dm[0] + "/" + dm[1];
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getDayMonth() {
        return dayMonth;
    }
    public void setDayMonth(String dm)
    {
        dayMonth = dm;
    }

}

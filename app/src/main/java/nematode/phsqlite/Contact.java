package nematode.phsqlite;

/**
 * Created by harde on 5/14/2017.
 */

public class Contact {

    //Variables
    int    mId;
    String mName;
    String mPhone;

    //Constructors

    //Empty
    public Contact(){
    }

    //with ID
    public Contact(int id, String name, String phone){
        this.mId = id;
        this.mName = name;
        this.mPhone = phone;
    }

    //without ID
    public Contact(String name, String phone){
        this.mName = name;
        this.mPhone = phone;
    }

    //Getting ID
    public int getID() {
        return this.mId;
    }

    //Setting ID
    public void setID(int id) {
        this.mId = id;
    }

    //Getting Name
    public String getName() {
        return this.mName;
    }

    //Setting Name
    public void setName(String name) {
        this.mName = name;
    }

    //Getting Phone
    public String getPhone() {
        return this.mPhone;
    }

    //Setting Phone
    public void setPhone(String phone) {
        this.mPhone = phone;
    }


}

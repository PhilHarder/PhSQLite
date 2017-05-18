package nematode.phsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harde on 5/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //Static Variables

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "contactManager";

    //Contacts Table Name
    private static final String TABLE_CONTACTS = "contacts";

    //Contacts Table - Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    //Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        //Create Tables again
        onCreate(db);
    }

    //Add a new contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,contact.getName());       //Contact Name
        values.put(KEY_PHONE,contact.getPhone());     //Contact Phone

        //insert row
        db.insert(TABLE_CONTACTS,null,values);
        db.close();                                   //close database connection
    }

    //Get a single contact by ID
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[] { KEY_ID, KEY_NAME, KEY_PHONE},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));
        cursor.close();
        //return the contact
        return contact;
    }

    //Find contact by Name
    public List<Contact> getContactsByName(String name){
        List<Contact> contactList = new ArrayList<Contact>();
        //Select by name query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE NAME LIKE '%" + name + "%'";
        Log.d("TAG - DB Query", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //loop through all rows in table
        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                //add contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //return contact list
        return contactList;
    }

    //Find contact by Phone
    public List<Contact> getContactsByPhone(String phone){
        List<Contact> contactList = new ArrayList<Contact>();
        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE PHONE LIKE '" + phone + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //loop through all rows in table
        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                //add contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //return contact list
        return contactList;
    }

    //Get ALL contacts
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<Contact>();
        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //loop through all rows in table
        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                //add contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //return contact list
        return contactList;
    }

    //Get COUNT of all contacts
    public int contactCount(){
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    //Update a single contact
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());

        //update row
        return db.update(TABLE_CONTACTS,values,KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    //Delete a single contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS,KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

}

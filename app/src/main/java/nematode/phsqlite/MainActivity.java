package nematode.phsqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int                     searchID;
    EditText                searchName;
    EditText                searchPhone;
    ListView                listContacts;
    ArrayAdapter<String>    adapter;
    ArrayList<String>       arrayList;
    DatabaseHandler         db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchName   = (EditText) findViewById(R.id.searchName);
        searchPhone  = (EditText) findViewById(R.id.searchPhone);
        listContacts = (ListView) findViewById(R.id.listContacts);
        arrayList = new ArrayList<String>();

        // Adapter takes three parameters:
        // 1 the context
        // 2 the ID of the layout where the data will be shown
        // 3 the array that contains the data
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item ,arrayList);
        listContacts.setAdapter(adapter);

        db = new DatabaseHandler(this);

        // CRUD Operations

        // reading the database
        Log.d("LOADING: ", "Initial load of database. Reading all contacts...");

        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "ID: " + cn.getID() +
                    " " + cn.getName() +
                    " " + cn.getPhone();
            Log.d("Contact: ", log);
        }

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) parent.getItemAtPosition(position);
                String[] ma = selectedFromList.split(" : ");
                if(ma.length < 3){
                    Log.e("ERROR: ","Selected record is corrupted" + selectedFromList);
                    // deleting record from database
                    db.deleteContact(db.getContact(Integer.parseInt(ma[0])));

                }else{
                    searchID = Integer.parseInt(ma[0]);
                    searchName.setText(ma[1]);
                    searchPhone.setText(ma[2]);
                }
            }
        });

    }

    public void clickName(View view) {

        searchName   = (EditText) findViewById(R.id.searchName);
        listContacts = (ListView) findViewById(R.id.listContacts);
        arrayList.clear();

        List<Contact> contacts = db.getContactsByName(searchName.getText().toString());

        for (Contact cn : contacts) {
            String contactElement = cn.getID() + " : " + cn.getName() + " : " + cn.getPhone();
            arrayList.add(contactElement);
            adapter.notifyDataSetChanged();
        }
    }

    public void clickPhone(View view) {

        searchPhone  = (EditText) findViewById(R.id.searchPhone);
        listContacts = (ListView) findViewById(R.id.listContacts);
        arrayList.clear();

        List<Contact> contacts = db.getContactsByPhone(searchPhone.getText().toString());

        for (Contact cn : contacts) {
            String contactElement = cn.getID() + " : " + cn.getName() + " : " + cn.getPhone();
            arrayList.add(contactElement);
            adapter.notifyDataSetChanged();
        }
    }

    public void clickNew(View view) {
        searchName   = (EditText) findViewById(R.id.searchName);
        searchPhone  = (EditText) findViewById(R.id.searchPhone);

        //CRUD operations - insert new record
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact(searchName.getText().toString(), searchPhone.getText().toString()));

    }

    public void clickEdit(View view) {

        listContacts = (ListView) findViewById(R.id.listContacts);
        arrayList.clear();

        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String contactElement = cn.getID() + " : " + cn.getName() + " : " + cn.getPhone();
            arrayList.add(contactElement);
            adapter.notifyDataSetChanged();
        }
    }

    public void clickDel(View view) {
        listContacts = (ListView) findViewById(R.id.listContacts);
        arrayList.clear();

        //TODO: delete selected row

    }

    public void clearNameText(View view) {
        searchName  = (EditText) findViewById(R.id.searchName);
        searchName.setText("");
    }

    public void clearPhoneText(View view) {
        searchPhone.setText("");
    }

 }

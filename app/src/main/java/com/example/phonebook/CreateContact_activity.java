package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import Databases.ContactList;

public class CreateContact_activity extends AppCompatActivity {
    EditText NameET,P_numberET,Email_ET;
    Button savebtn;
    ContactList contactListDB;
    TextInputLayout NameErrorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_activity);
        NameET = (EditText) findViewById(R.id.NameETID);
        P_numberET = (EditText) findViewById(R.id.P_numberETID);
        Email_ET = (EditText) findViewById(R.id.EmailETID);
        savebtn = (Button) findViewById(R.id.SavebtnID);
        NameErrorLayout = (TextInputLayout) findViewById(R.id.NameErrorID);

        contactListDB = new ContactList(this);
        SQLiteDatabase sqLiteDatabase = contactListDB.getWritableDatabase();

        savebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String Name = NameET.getText().toString();
               String Phone = P_numberET.getText().toString();
               String Email = Email_ET.getText().toString();

               Boolean get_name = contactListDB.CheckNameMethod(Name);
               if (get_name == true || Name.isEmpty()){
                   NameErrorLayout.setError("Same or Invalid Name");
               }
               else {
                   long rowID = contactListDB.InsertContactData(Name,Phone,Email);
                   if (rowID == -1){
                       Toast.makeText(CreateContact_activity.this,"Unsuccessful",Toast.LENGTH_SHORT).show();

                   }else {
                       Toast.makeText(CreateContact_activity.this,"Contact saved",Toast.LENGTH_SHORT).show();
                       MainActivity.mainActivity.finish();
                       Intent intent = new Intent(CreateContact_activity.this,MainActivity.class);
                       startActivity(intent);
                       finish();
                   }
               }
           }
       });

    }
}
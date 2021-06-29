package com.example.phonebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Databases.ContactList;

public class ContactDetails_activity extends AppCompatActivity {
    EditText PhoneET,EmailET;
    TextView NameTV;
    ContactList contactListDB;
    Button deletebtn,updatebtn;
    AlertDialog.Builder delete_alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details_activity);
        NameTV = (TextView) findViewById(R.id.nameTVID);
        PhoneET = (EditText) findViewById(R.id.phoneETID);
        EmailET = (EditText) findViewById(R.id.emailETID);
        deletebtn = (Button) findViewById(R.id.deletebtnID);
        updatebtn = (Button) findViewById(R.id.updatebtnID);

        contactListDB = new ContactList(this);
        SQLiteDatabase sqLiteDatabase = contactListDB.getWritableDatabase();

        String R_Name = getIntent().getStringExtra("names");
        NameTV.setText(R_Name);
        getDetails(R_Name);


        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_alertdialog = new AlertDialog.Builder(ContactDetails_activity.this);
                delete_alertdialog.setTitle("Delete");
                delete_alertdialog.setMessage("Delete this contact?");
                delete_alertdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int del_item = contactListDB.deleteMethod(NameTV.getText().toString());
                        if (del_item>0){
                            Toast.makeText(ContactDetails_activity.this,"deleted", Toast.LENGTH_SHORT).show();
                            MainActivity.mainActivity.finish();
                            Intent intent = new Intent(ContactDetails_activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(ContactDetails_activity.this, "delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                delete_alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = delete_alertdialog.create();
                alertDialog.show();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = NameTV.getText().toString();
                String Phone = PhoneET.getText().toString();
                String Mail = EmailET.getText().toString();
                Boolean result = contactListDB.updateMethod(Name,Phone,Mail);
                if (result==true){
                    Toast.makeText(ContactDetails_activity.this, "Contact updated", Toast.LENGTH_SHORT).show();
                    MainActivity.mainActivity.finish();
                    Intent intent = new Intent(ContactDetails_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ContactDetails_activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getDetails(String R_Name) {
        Cursor cursor = contactListDB.fetchData(R_Name);
        cursor.moveToNext();
        PhoneET.setText(cursor.getString(2));
        EmailET.setText(cursor.getString(3));
    }

}
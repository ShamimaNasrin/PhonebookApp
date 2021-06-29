package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Databases.ContactList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ContactList contactListDB;
    ArrayList<String> Names;
    ArrayAdapter<String> arrayAdapter;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        listView =(ListView) findViewById(R.id.ContactLVID);
        searchView =(SearchView) findViewById(R.id.searchviewID);
        contactListDB = new ContactList(this);
        SQLiteDatabase sqLiteDatabase = contactListDB.getWritableDatabase();
        LoadContactData();
    }
    private void LoadContactData() {
         Names = new ArrayList<>();

        Cursor cursor = contactListDB.showContact();
        if(cursor.getCount()==0){
            Toast.makeText(this, "null database", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                Names.add(cursor.getString(1));
            }
        }
        arrayAdapter = new ArrayAdapter<>(this,R.layout.listview_sampledesign,R.id.NameTVID,Names);
        listView.setAdapter(arrayAdapter);
        //AscendingMethod(Names,arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String values = adapterView.getItemAtPosition(i).toString();
                String name = values;
                Intent intent = new Intent(MainActivity.this,ContactDetails_activity.class);
                intent.putExtra("names",name);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void AscendingMethod(ArrayList<String> Names,ArrayAdapter<String> arrayAdapter) {
        Collections.sort(Names);
        arrayAdapter.notifyDataSetChanged();
    }
    /*private void DescendingMethod(ArrayList<String> Names,ArrayAdapter<String> arrayAdapter){
        Collections.sort(Names,Collections.<String>reverseOrder());
        arrayAdapter.notifyDataSetChanged();
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.CreatecontactID){
            Intent intent = new Intent(MainActivity.this,CreateContact_activity.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.SortbyAlphaID){
            AscendingMethod(Names,arrayAdapter);
        }else if (item.getItemId()==R.id.SortbyTimeID){
               LoadContactData();
            //DescendingMethod(Names,arrayAdapter);
        }
        return super.onOptionsItemSelected(item);
    }


}
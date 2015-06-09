package com.rayziken.vindictusassistant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WeaponList extends ActionBarActivity {
    private String chosenWeaponCategory;
    DatabaseHelper DB;
    ListView weaponListView;
    List<String> weaponNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        chosenWeaponCategory = bundle.getString("chosenWeaponCategory");
        DB = new DatabaseHelper(this);
        try {
            DB.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            DB.openDataBase();
        } catch (SQLException sqle) {

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_list);

        setTitle(chosenWeaponCategory.replace("_", " "));
        weaponListView = (ListView)findViewById(R.id.weaponListView);
        populateListView();
        registerClickCallback();
    }

    private void populateListView() {
        String columns[] = {"name"};
        Cursor cursor = DB.getColumnsFromTable(chosenWeaponCategory, columns);
        weaponNames = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            weaponNames.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.display_item_view, weaponNames);
        weaponListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weapon_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerClickCallback() {
        weaponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent weaponActivityIntent = new Intent(WeaponList.this, WeaponActivity.class);
                TextView clickedWeapon = (TextView)view;
                weaponActivityIntent.putExtra("chosenWeapon", clickedWeapon.getText().toString());
                weaponActivityIntent.putExtra("chosenWeaponCategory", chosenWeaponCategory);
                startActivity(weaponActivityIntent);
            }
        });
    }
}

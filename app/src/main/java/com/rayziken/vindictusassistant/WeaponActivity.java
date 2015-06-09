package com.rayziken.vindictusassistant;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class WeaponActivity extends ActionBarActivity {
    private String chosenWeapon;
    private String chosenWeaponCategory;
    private TabHost weaponTabHost;
    private DatabaseHelper DB;
    private int craftId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        Bundle bundle = getIntent().getExtras();
        chosenWeapon = bundle.getString("chosenWeapon");
        chosenWeaponCategory = bundle.getString("chosenWeaponCategory");
        setTitle(chosenWeapon);

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

        createTabs();
        populateTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weapon, menu);
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

    private void createTabs() {
        weaponTabHost = (TabHost)findViewById(R.id.weaponTabHost);
        weaponTabHost.setup();

        TabHost.TabSpec tabSpec = weaponTabHost.newTabSpec("Details");
        tabSpec.setContent(R.id.weaponTabDetails);
        tabSpec.setIndicator("Details");
        weaponTabHost.addTab(tabSpec);

        tabSpec = weaponTabHost.newTabSpec("Craft");
        tabSpec.setContent(R.id.weaponTabCraft);
        tabSpec.setIndicator("Craft");
        weaponTabHost.addTab(tabSpec);
    }

    private void populateTabs() {
        populateDetailsTab();
        populateCraftTab();
    }

    private void populateDetailsTab() {
        Cursor cursor = DB.getItemFromTable(chosenWeaponCategory, chosenWeapon);

        TextView name = (TextView)findViewById(R.id.weaponDetailsName);
        TextView level = (TextView)findViewById(R.id.weaponDetailsLevel);
        TextView rank = (TextView)findViewById(R.id.weaponDetailsRank);
        TextView attack = (TextView)findViewById(R.id.weaponDetailsAttack);
        TextView balance = (TextView)findViewById(R.id.weaponDetailsBalance);
        TextView critical = (TextView)findViewById(R.id.weaponDetailsCritical);
        TextView speed = (TextView)findViewById(R.id.weaponDetailsSpeed);
        TextView strength = (TextView)findViewById(R.id.weaponDetailsStrength);
        TextView intelligence = (TextView)findViewById(R.id.weaponDetailsIntelligence);
        TextView willpower = (TextView)findViewById(R.id.weaponDetailsWillpower);
        TextView agility = (TextView)findViewById(R.id.weaponDetailsAgility);
        TextView mattack = (TextView)findViewById(R.id.weaponDetailsMAttack);

        int currentIndex = 0;
        currentIndex++;

        name.setText(cursor.getString(currentIndex++));
        level.setText(cursor.getInt(currentIndex++) + "");
        rank.setText(cursor.getInt(currentIndex++) + "");
        attack.setText(cursor.getInt(currentIndex++) + "");
        mattack.setText(cursor.getInt(currentIndex++) + "");
        balance.setText(cursor.getInt(currentIndex++) + "");
        speed.setText(cursor.getInt(currentIndex++) + "");
        critical.setText(cursor.getInt(currentIndex++) + "");
        strength.setText(cursor.getInt(currentIndex++) + "");
        agility.setText(cursor.getInt(currentIndex++) + "");
        intelligence.setText(cursor.getInt(currentIndex++) + "");
        willpower.setText(cursor.getInt(currentIndex++) + "");

        craftId = cursor.getInt(currentIndex);

        cursor.close();
    }

    private void populateCraftTab() {
        if (craftId == 0)
            return;

        Cursor cursor = DB.getCraftFromTable(craftId);

        TextView item1Name = (TextView)findViewById(R.id.weaponCraftItem1Name);
        TextView item2Name = (TextView)findViewById(R.id.weaponCraftItem2Name);
        TextView item3Name = (TextView)findViewById(R.id.weaponCraftItem3Name);
        TextView item4Name = (TextView)findViewById(R.id.weaponCraftItem4Name);
        TextView item5Name = (TextView)findViewById(R.id.weaponCraftItem5Name);
        TextView item6Name = (TextView)findViewById(R.id.weaponCraftItem6Name);
        TextView item7Name = (TextView)findViewById(R.id.weaponCraftItem7Name);

        TextView item1Quantity = (TextView)findViewById(R.id.weaponCraftItem1Quantity);
        TextView item2Quantity = (TextView)findViewById(R.id.weaponCraftItem2Quantity);
        TextView item3Quantity = (TextView)findViewById(R.id.weaponCraftItem3Quantity);
        TextView item4Quantity = (TextView)findViewById(R.id.weaponCraftItem4Quantity);
        TextView item5Quantity = (TextView)findViewById(R.id.weaponCraftItem5Quantity);
        TextView item6Quantity = (TextView)findViewById(R.id.weaponCraftItem6Quantity);
        TextView item7Quantity = (TextView)findViewById(R.id.weaponCraftItem7Quantity);

        int currentIndex = 0;
        ++currentIndex;

        item1Name.setText(cursor.getString(currentIndex++));
        item1Quantity.setText(cursor.getInt(currentIndex++) + "");

        item2Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item2Quantity.setText(cursor.getInt(currentIndex++) + "");

        item3Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item3Quantity.setText(cursor.getInt(currentIndex++) + "");

        item4Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item4Quantity.setText(cursor.getInt(currentIndex++) + "");

        item5Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item5Quantity.setText(cursor.getInt(currentIndex++) + "");

        item6Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item6Quantity.setText(cursor.getInt(currentIndex++) + "");

        item7Name.setText(cursor.getString(currentIndex++));
        if (cursor.getInt(currentIndex) == 0)
            return;
        item7Quantity.setText(cursor.getInt(currentIndex) + "");

        cursor.close();
    }
}

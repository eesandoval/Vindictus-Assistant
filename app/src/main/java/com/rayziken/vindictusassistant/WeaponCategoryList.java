package com.rayziken.vindictusassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WeaponCategoryList extends Fragment {
    View rootview;
    ListView list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Weapons");
        rootview = inflater.inflate(R.layout.weapon_category_list, container, false);
        list = (ListView)rootview.findViewById(R.id.listView);
        populateListView();
        registerClickCallback();
        return rootview;
    }

    private void populateListView() {
        String[] weaponItems = {"Twin Swords", "Twin Spears", "Longswords", "Long Hammers",
                                "Battle Scythes", "Staves", "Pillars", "Cestuses",
                                "Bows", "Cross Guns", "Twin Chainblades", "Greatswords",
                                "Glaives", "Spellswords"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.display_item_view, weaponItems);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent weaponListIntent = new Intent(getActivity(), WeaponList.class);
                TextView clickedWeapon = (TextView)view;
                weaponListIntent.putExtra("chosenWeaponCategory", clickedWeapon.getText().toString().replace(" ", "_"));
                startActivity(weaponListIntent);
            }
        });
    }
}

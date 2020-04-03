package com.spirograph.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class FavouritesDB {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    public FavouritesDB(Context context) {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences("fav", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

    }

    public void remove(String key) {
        sharedPreferencesEditor.remove(key);
        sharedPreferencesEditor.commit();
    }

    public void add(String key) {
        sharedPreferencesEditor.putString(key, key);
        sharedPreferencesEditor.commit();
    }

    public Set<String> getAllValues() {
        return sharedPreferences.getAll().keySet();
    }

}

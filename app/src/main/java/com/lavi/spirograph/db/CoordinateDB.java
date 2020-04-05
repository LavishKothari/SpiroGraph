package com.lavi.spirograph.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class CoordinateDB {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    public CoordinateDB(Context context) {
        sharedPreferences = context.getSharedPreferences("coordinate-db", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void clear() {
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }

    public void remove(String key) {
        sharedPreferencesEditor.remove(key);
        sharedPreferencesEditor.commit();
    }

    public void add(String key) {
        sharedPreferencesEditor.putString(key, key);
        sharedPreferencesEditor.commit();
    }

    public void clearThenAdd(String key) {
        clear();
        add(key);
    }

    public boolean isEmpty() {
        return sharedPreferences.getAll().keySet().isEmpty();
    }

    public String getFirstValue() {
        return sharedPreferences.getAll().keySet().iterator().next();
    }

    public Set<String> getAllValues() {
        return sharedPreferences.getAll().keySet();
    }

}

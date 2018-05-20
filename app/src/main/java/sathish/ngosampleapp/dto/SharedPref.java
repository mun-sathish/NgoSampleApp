package sathish.ngosampleapp.dto;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences sharedPreferences;

    public SharedPref(Context context, String myPref) {
        sharedPreferences = context.getSharedPreferences(myPref, Context.MODE_PRIVATE);
    }

    public SharedPreferences getInstance() {
        return sharedPreferences;
    }

    public void addString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void deleteString(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}

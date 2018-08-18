package com.example.ndambuki.shoppingtracker.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Methods {
    SharedPreferences sharedPreferences;
    Context context;
    public static final String MYPREFS ="myprefs";

    public Methods(Context context) {
        this.context = context;
    }

    public void setAmountValue(String amountValue){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount",amountValue);
        editor.apply();
    }

    public String getAmount(){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
       return sharedPreferences.getString("amount","");
    }

    public void clearPreferences(){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

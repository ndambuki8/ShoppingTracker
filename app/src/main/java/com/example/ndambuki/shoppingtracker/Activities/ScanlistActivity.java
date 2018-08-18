package com.example.ndambuki.shoppingtracker.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.ndambuki.shoppingtracker.Adapter.ScannedListAdapter;
import com.example.ndambuki.shoppingtracker.Database.ColumnsContract;
import com.example.ndambuki.shoppingtracker.Database.DatabaseHelper;
import com.example.ndambuki.shoppingtracker.R;
import com.example.ndambuki.shoppingtracker.Utils.Methods;

import java.util.ArrayList;

public class ScanlistActivity extends AppCompatActivity {

    public ListView scannedList;
    DatabaseHelper myDb;
    ScannedListAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanlist);
        myDb = new DatabaseHelper(this);
        myAdapter = new ScannedListAdapter(this,myDb.getAllAmounts());
        scannedList = (ListView)findViewById(R.id.scan_list);
        scannedList.setAdapter(myAdapter);

        }

    }

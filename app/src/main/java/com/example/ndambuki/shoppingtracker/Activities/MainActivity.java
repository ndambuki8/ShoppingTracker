package com.example.ndambuki.shoppingtracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ndambuki.shoppingtracker.R;

public class MainActivity extends AppCompatActivity {

    private Button scanPrice;
    private Button shoppingList;
    private Button calculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();


        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewShopListActivity();

            }
        });


        scanPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewAmountActivity();

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewCalcActivity();

            }
        });

    }

    private void setupUIViews(){

        scanPrice = (Button)findViewById(R.id.btnScanner);
        shoppingList = (Button)findViewById(R.id.btnShoplist);
        calculate = (Button)findViewById(R.id.btnCalc);

    }

    private void openNewShopListActivity(){

        startActivity(new Intent(this, ShopListActivity.class));
    }

    private void openNewAmountActivity(){
        startActivity(new Intent(this, AmountActivity.class));
    }

    private void openNewCalcActivity(){
        startActivity(new Intent(this, CalcActivity.class));
    }





}



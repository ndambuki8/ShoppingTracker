package com.example.ndambuki.shoppingtracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ndambuki.shoppingtracker.R;
import com.example.ndambuki.shoppingtracker.Utils.Methods;

public class AmountActivity extends AppCompatActivity {

    public Button submit;
    public EditText etAmount;
    //DatabaseHelper myDb;
    Methods myMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        //myDb = new DatabaseHelper(this);
        myMethods = new Methods(this);

        etAmount = (EditText)findViewById(R.id.etAmount);

        submit = (Button)findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (!etAmount.getText().toString().trim().isEmpty()){
                    long result = myDb.insertScannedItem(etAmount.getText().toString().trim());
                    if (result != -1){
                        Toast.makeText(AmountActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        openNewScanActivity();
                    }else {
                        Toast.makeText(AmountActivity.this, "Value not saved", Toast.LENGTH_SHORT).show();
                    }
                }*/

                  if (!etAmount.getText().toString().trim().isEmpty()){
                      myMethods.setAmountValue(etAmount.getText().toString().trim());
                      Toast.makeText(AmountActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                      ScanActivity.totalAmount = Integer.parseInt(etAmount.getText().toString().trim());
                      openNewScanActivity();
                } else {
                      Toast.makeText(AmountActivity.this, "Enter Amount to Proceed", Toast.LENGTH_SHORT).show();
                  }


            }
        });

    }


    private void openNewScanActivity() {
        startActivity(new Intent(this, ScanActivity.class));
    }


}

package com.example.employeeezzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class launcher2 extends AppCompatActivity {
    private static int SPLASH_TOME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(launcher2.this,login2.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TOME_OUT);
    }
    public void moveToSecondary(){
        // use an intent to travel from one activity to another.


        Intent intent = new Intent(this,launcher2.class);

        startActivity(intent);

    }
}
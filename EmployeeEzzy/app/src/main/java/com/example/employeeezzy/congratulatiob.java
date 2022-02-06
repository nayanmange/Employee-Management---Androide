package com.example.employeeezzy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class congratulatiob extends AppCompatActivity {

    private static int SPLASH_TOME_OUT=2000;
    TextView t2;
    int code1,code2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformation);

        t2=findViewById(R.id.lt2);
        code1=getIntent().getIntExtra("code1",0);
        code2=getIntent().getIntExtra("code2",0);

        if(code1==1)
        {
            t2.setText("Account Created");
        }
        if(code2==2)
        {
            t2.setText("Password Updated");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(congratulatiob.this,login2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        },SPLASH_TOME_OUT);
    }
    public void moveToSecondary(){
        // use an intent to travel from one activity to another.


        Intent intent = new Intent(this,congratulatiob.class);

        startActivity(intent);

    }

    }

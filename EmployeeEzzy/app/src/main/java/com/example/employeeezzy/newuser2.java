package com.example.employeeezzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class newuser2 extends AppCompatActivity {

    Button b1;
    TextView t1,t2;
    String name,surname,email,phone,otp2,match,number;
    EditText e1,e2,e3,e4;
    int code1,code2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser2);

        b1=findViewById(R.id.next2);
        e1=findViewById(R.id.p1);
        e2=findViewById(R.id.p2);
        e3=findViewById(R.id.p3);
        e4=findViewById(R.id.p4);
        t1=findViewById(R.id.mobile);
        t2=findViewById(R.id.check);


        code1=getIntent().getIntExtra("code",0);
         code2=getIntent().getIntExtra("code1",0);

        if(code1==1) {
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("phone");
            otp2=getIntent().getStringExtra("otp");
            t1.setText("+91 "+phone+" No");
        }
        if(code2==2)
        {
            number=getIntent().getStringExtra("number");
            otp2=getIntent().getStringExtra("otp");
            t1.setText("+91 "+number+" No");

        }

        otp();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(code1==1) {

                    if(e1.length()>0 && e2.length() >0 && e3.length()>0 && e4.length()>0) {

                        match=e1.getText().toString();
                        match =match+e2.getText().toString();
                        match =match+e3.getText().toString();
                        match =match+e4.getText().toString();

                        if(otp2.matches(match)) {

                            Intent intent = new Intent(newuser2.this, newuser4.class);
                            intent.putExtra("name", name);
                            intent.putExtra("surname", surname);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            t2.setText("OTP Not Matched");
                        }
                    }
                    else
                    {
                        t2.setText("*Please Enter OTP Properly");
                    }
                }
                if(code2==2) {
                    if(e1.length()>0 && e2.length() >0 && e3.length()>0 && e4.length()>0) {

                        match=e1.getText().toString();
                        match =match+e2.getText().toString();
                        match =match+e3.getText().toString();
                        match =match+e4.getText().toString();

                        if(otp2.matches(match)) {

                            t2.setText("");
                            Intent intent = new Intent(newuser2.this, forgot3.class);
                            intent.putExtra("phone", number);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            t2.setText("OTP Not Matched");
                        }
                    }
                    else
                    {
                        t2.setText("*Please Enter OTP Properly");
                    }
                }
            }
        });

    }
    public void otp(View view) {

        if(code1==1) {
            Integer otp;

            Random rnd = new Random();
            otp = rnd.nextInt(9999);
            otp2 = String.format("%04d", otp);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, otp2, null, null);

            t2.setText("OTP Send SuccssesFully");
        }
        if(code2==2) {
            Integer otp;

            Random rnd = new Random();
            otp = rnd.nextInt(9999);
            otp2 = String.format("%04d", otp);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, otp2, null, null);

            t2.setText("OTP Sent SuccssesFully");
        }

    }

    public void wrongcontact(View view) {

        finish();
    }
    public void otp()
    {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==1)
                {
                    e2.requestFocus();
                }
            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().isEmpty())
                {
                    e1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==1)
                {
                    e3.requestFocus();
                }
            }
        });
        e3.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s.toString().trim().isEmpty())
            {
                e2.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().length()==1)
            {
                e4.requestFocus();
            }
        }
    });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().isEmpty())
                {
                    e3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
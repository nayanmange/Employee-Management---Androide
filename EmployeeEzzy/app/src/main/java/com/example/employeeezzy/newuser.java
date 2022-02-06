package com.example.employeeezzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class newuser extends AppCompatActivity {

    Button b1;
    EditText name,surname,email,phone;
    Integer otp,check1=1;
    String n1,s1,e1,p1,otp2;
    ProgressDialog pr;
    String result;
    String check;
    int code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        b1=findViewById(R.id.next);
        name=findViewById(R.id.editTextTextPersonName2);
        surname=findViewById(R.id.editTextTextPersonName3);
        email=findViewById(R.id.editTextTextEmail);
        phone=findViewById(R.id.editTextTextPhoneNumber);

        check=null;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check1 = 1;

                n1 = name.getText().toString();
                s1 = surname.getText().toString();
                e1 = email.getText().toString();
                p1 = phone.getText().toString();


                if (n1.matches("")) {
                    name.setError("Please Enter Name");
                }
                if (s1.matches("")) {
                    surname.setError("Please Enter Surname");
                }
                if (e1.matches("")) {
                    email.setError("Please Enter Email");
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(e1).matches()) {
                    email.setError("Please Enter Valid Email");
                }
                if (p1.matches("")) {
                    phone.setError("Please Enter PhoneNumber");
                }
                if (p1.length() != 10) {
                    phone.setError("Please Enter PhoneNumber Properly");
                }
                if (p1.length() == 10) {
                    new Asyncnumber().execute(p1);

                    pr = new ProgressDialog(v.getContext());
                    pr.setCancelable(true);
                    pr.setMessage("Validating");
                    pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pr.show();

                }
            }
        });
    }

    public void haveaccount(View view) {

        Intent intent=new Intent(this,login2.class);
        startActivity(intent);
        finishAffinity();
    }
    private class Asyncnumber extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/check_users.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("phone", params[0]);
                String query = builder.build().getEncodedQuery();
                System.out.println(query);
                System.out.println(params[0]);

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    System.out.println(response_code);
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader=null;
                    reader = new BufferedReader(new InputStreamReader(input));
                    String line="";

                    result = reader.readLine();

                    System.out.println(result);

                    // Pass data to onPostExecute method
                    return result;

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject reader = new JSONObject(result);
                check= reader.getString("sessionID");
                if(check!=null)
                {
                    pr.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (check.matches("0")) {
                phone.setError("Number Already Used");
                check1=0;
            }
            if(check1==1 && n1.length()>0 && s1.length()>0 && e1.length()>0 && p1.length()==10 && Patterns.EMAIL_ADDRESS.matcher(e1).matches()) {


                Random rnd = new Random();
                otp = rnd.nextInt(9999);
                otp2 = String.format("%04d", otp);

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(p1, null, otp2, null, null);

                Intent intent = new Intent(newuser.this, newuser2.class);
                intent.putExtra("code", code);
                intent.putExtra("name", n1);
                intent.putExtra("surname", s1);
                intent.putExtra("email", e1);
                intent.putExtra("phone", p1);
                intent.putExtra("otp", otp2);
                startActivity(intent);
            }

        //this method will be running on UI thread

             if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(newuser.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
}
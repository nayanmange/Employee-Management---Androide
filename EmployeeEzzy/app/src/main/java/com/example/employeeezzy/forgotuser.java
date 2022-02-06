package com.example.employeeezzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class forgotuser extends AppCompatActivity {

    Button b1;
    int code=2;
    EditText num;
    String number;
    TextView t;
    ProgressDialog pr;
    String result;
    String check;
    int check1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotuser);

        b1=findViewById(R.id.submit2);
        num=findViewById(R.id.editnumber);
        t=findViewById(R.id.err);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = num.getText().toString();

                if (number.length() != 10) {
                    num.setError("Please Enter PhoneNumber Properly");
                }
                if (number.length() == 10) {
                    new Asyncnumber2().execute(number);

                    pr = new ProgressDialog(v.getContext());
                    pr.setCancelable(true);
                    pr.setMessage("Validating");
                    pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pr.show();
                }
            }
        });
    }

    public void login2(View view) {
        Intent intent = new Intent(forgotuser.this, login2.class);
        startActivity(intent);
        finishAffinity();
    }
    private class Asyncnumber2 extends AsyncTask<String, String, String>
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

            if (check.matches("1")) {
                t.setText("Number Not Found");
                check1=0;
            }
           else
            {
                Integer otp;
                String otp2;

                Random rnd = new Random();
                otp = rnd.nextInt(9999);
                otp2 = String.format("%04d", otp);

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, null, otp2, null, null);


                Intent intent = new Intent(forgotuser.this, newuser2.class);
                intent.putExtra("code1", code);
                intent.putExtra("number", number);
                intent.putExtra("otp",otp2);
                startActivity(intent);
            }
            //this method will be running on UI thread

            if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(forgotuser.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
}
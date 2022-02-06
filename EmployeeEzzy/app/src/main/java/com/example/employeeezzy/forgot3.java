package com.example.employeeezzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class forgot3 extends AppCompatActivity {

    Button b1;
    EditText pass1,pass2;
    String p1,p2,number;
    ImageView mg1,mg2;
    ProgressDialog pr;
    String result;
    String check;
    TextView t1;
    int code=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot3);

        b1=findViewById(R.id.submit3);
        pass1=findViewById(R.id.editTextTextPassword2);
        pass2=findViewById(R.id.editTextTextPassword3);
        number=getIntent().getStringExtra("phone");
        mg1=findViewById(R.id.hide2);
        mg2=findViewById(R.id.hide3);
        t1=findViewById(R.id.error4);

        hide2();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p1=pass1.getText().toString();
                p2=pass2.getText().toString();

                if (p1.matches("")) {
                    pass1.setError("Please Enter Password");
                }
                if (p1.length()< 8) {
                    pass1.setError("Minimum Requried Length Is 8 ");
                }
                if (p2.matches("")) {
                    pass2.setError("Please Enter Password");
                }
                if (p2.length()<8) {
                    pass2.setError("Minimum Requried Length Is 8");
                }

                if(p1.length() >7 && p2.length() >7) {

                    if(p1.matches(p2)) {

                        if(p1.matches("[0-9]+"))
                        {
                            t1.setText("Please Use Character And Number");
                        }
                        else {


                            new Asyncnumber3().execute(number,p1);

                            pr = new ProgressDialog(v.getContext());
                            pr.setCancelable(true);
                            pr.setMessage("Validating");
                            pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pr.show();

                        }
                    }
                    else
                    {
                        t1.setText("Password Not Matched");
                    }
                }
            }
        });
    }

    public void cancle3(View view) {
        Intent intent = new Intent(forgot3.this, login2.class);
        startActivity(intent);
        finishAffinity();
    }
    public void viewch2(View view) {

        if (pass1.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            //Show Password
            pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
    public void viewch3(View view) {

        if (pass2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            //Show Password
            pass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
    public void hide2() {
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    mg1.setVisibility(View.VISIBLE);
                }
                if(s.toString().trim().length()==0)
                {
                    mg1.setVisibility(View.INVISIBLE);
                }
            }
        });
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    mg2.setVisibility(View.VISIBLE);
                }
                if(s.toString().trim().length()==0)
                {
                    mg2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private class Asyncnumber3 extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/update_password.php");

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
                        .appendQueryParameter("phone", params[0])
                        .appendQueryParameter("password", params[1]);


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

            String check=null;
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
                t1.setText("Password Not Updated");
            }
            else
            {
                Intent intent = new Intent(forgot3.this, congratulatiob.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("code2", code);
                startActivity(intent);
            }


            //this method will be running on UI thread

            if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(forgot3.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


}
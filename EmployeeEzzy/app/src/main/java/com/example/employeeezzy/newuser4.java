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

public class newuser4 extends AppCompatActivity {

    Button b1;
    EditText e1,e2,e3;
    String name,surname,email,phone,username,password,pass;
    TextView t1;
    ProgressDialog pr;
    String result;
    String check;
    ImageView mg1,mg2;
    Integer code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser4);

        b1=findViewById(R.id.submit1);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.editTextTextPassword2);
        e3=findViewById(R.id.editTextTextPassword3);
        t1=findViewById(R.id.error2);
        mg1=findViewById(R.id.hide4);
        mg2=findViewById(R.id.hide5);

        name = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        hide3();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=e1.getText().toString();
                pass=e2.getText().toString();
                password=e3.getText().toString();

                if(username.length()<6)
                {
                    e1.setError("Minimum Length Is 6");
                }
                if(username.matches(""))
                {
                    e1.setError("Please Enter UserName");
                }
                if(password.length()<8)
                {
                    e3.setError("Minimum Length Is 8");
                }
                if(password.matches(""))
                {
                    e3.setError("Please Enter Password");
                }
                if(pass.length()<8)
                {
                    e2.setError("Minimum Length Is 8");
                }
                if(pass.matches(""))
                {
                    e2.setError("Please Enter Password");
                }
                if(username.length()>5 && pass.length()>7 && password.length()>7) {

                    if(password.matches(pass))
                    {

                            if(password.matches("[0-9]+"))
                            {
                                t1.setText("Please Use Character And Number");
                            }
                            else {

                                new Asyncuser().execute(username);


                                pr = new ProgressDialog(v.getContext());
                                pr.setCancelable(true);
                                pr.setMessage("Validating");
                                pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pr.show();


                        }
                    }
                    else
                    {
                        t1.setText("*Password Not Matched");
                    }
                }

            }
        });
    }

    public void cancle(View view) {
        Intent intent = new Intent(newuser4.this, login2.class);
        startActivity(intent);
        finishAffinity();
    }
    public void viewch4(View view) {

        if (e2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            //Show Password
            e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            e2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
    public void viewch5(View view) {

        if (e3.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            //Show Password
            e3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            e3.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
    public void hide3() {
        e2.addTextChangedListener(new TextWatcher() {
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
        e3.addTextChangedListener(new TextWatcher() {
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
    private class Asyncuser extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/check_users2.php");

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
                        .appendQueryParameter("username", params[0]);
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (check.matches("0")) {
                pr.dismiss();
                e1.setError("Username Already Used");
            }
            else
            {
                new Asyninsert().execute(name,surname,email,phone,username,pass);
            }


            //this method will be running on UI thread

            if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(newuser4.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class Asyninsert extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/insert_users.php");

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
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("surname", params[1])
                        .appendQueryParameter("email", params[2])
                        .appendQueryParameter("phone", params[3])
                        .appendQueryParameter("username", params[4])
                        .appendQueryParameter("password", params[5]);

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
                e1.setError("User Not Created");
            }
            else
            {
                Intent intent = new Intent(newuser4.this, congratulatiob.class);
                intent.putExtra("code1", code);
                startActivity(intent);
            }


            //this method will be running on UI thread

            if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(newuser4.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

}
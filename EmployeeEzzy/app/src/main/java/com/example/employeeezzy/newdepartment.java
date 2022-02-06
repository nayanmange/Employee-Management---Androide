package com.example.employeeezzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class newdepartment extends Fragment {

    View view;
    Button firstButton;
    EditText e1,e2;
    String d1,d2;
    TextView t1;
    String result;
    ProgressDialog p1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newdepartment, container, false);
        firstButton = (Button) view.findViewById(R.id.submitdep);
        e1=(EditText) view.findViewById(R.id.nameofdep);
        e2=(EditText) view.findViewById(R.id.detailofdep);
        t1=(TextView) view.findViewById(R.id.errd);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int c=1;


                d1=e1.getText().toString();
                d2=e2.getText().toString();

                if(d1.matches(""))
                {
                    e1.setError("Enter Std");
                    c=0;
                }

                if(d2.matches(""))
                {
                    e2.setError("Enter Department Description");
                    c=0;
                }
                if(c==1) {

                    p1 = new ProgressDialog(v.getContext());
                    p1.setCancelable(true);
                    p1.setMessage("Validating");
                    p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    p1.show();



                    new Asyncdep().execute(d1);

                }
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    home nextFrag= new home();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, nextFrag)
                            .commit();
                    return true;
                }
                return false;
            }
        } );
        return view;
    }
    private class Asyncdep extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/check_std.php");

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
                        .appendQueryParameter("name", params[0]);
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


            //this method will be running on UI thread

            if(check.matches("1"))
            {
                t1.setText("");
                e1.setText("");
                e2.setText("");

                Date ca = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String month2 = df.format(ca);

                new Asyncdep2().execute(d1,d2,month2);

            }else if (check.matches("0")){

                p1.dismiss();


                // If username and password does not match display a error message
                Toast.makeText(getActivity(), "Department Alredy Have", Toast.LENGTH_LONG).show();

            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class Asyncdep2 extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/insert_std.php");

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
                        .appendQueryParameter("des", params[1])
                        .appendQueryParameter("datec", params[2]);

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
                    p1.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            if(check.matches("1"))
            {
                Toast.makeText(getActivity(), "Department Created", Toast.LENGTH_LONG).show();


            }else if (check.matches("0")){

                // If username and password does not match display a error message
                Toast.makeText(getActivity(), "Department Not Entered", Toast.LENGTH_LONG).show();

            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

}
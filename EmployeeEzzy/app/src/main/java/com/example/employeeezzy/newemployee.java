package com.example.employeeezzy;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class newemployee extends Fragment{

    View view;
    EditText n1,b1,c1,e1,s1;
    RadioGroup r1;
    Spinner d1;
    TextView t1;
    Button a1;
    String dep="";
    RadioButton r;
    String result;
    ProgressDialog p1;
    ArrayList<String> labels= new ArrayList<String>();

    String n2,b2,c2,e2,s2,r2,t2;
    int c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_newemployee, container, false);

        n1=(EditText) view.findViewById(R.id.nameofemp);
        b1=(EditText) view.findViewById(R.id.edate);
        c1=(EditText) view.findViewById(R.id.enumber);
        e1=(EditText) view.findViewById(R.id.eemail);
        s1=(EditText) view.findViewById(R.id.salary);
        r1=(RadioGroup) view.findViewById(R.id.gender);
        d1=(Spinner) view.findViewById(R.id.spinner);
        t1=(TextView) view.findViewById(R.id.erremp);
        a1=(Button) view.findViewById(R.id.submitemp);

        loadspinner();

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                n2=n1.getText().toString();
                b2=b1.getText().toString();
                c2=c1.getText().toString();
                e2=e1.getText().toString();
                s2=s1.getText().toString();

                if(n2.matches(""))
                {
                    n1.setError("Enter Name");
                    c=0;
                }
                else
                {
                    if(n2.matches("[a-zA-Z]+"))
                    {
                        t1.setText("");
                    }
                    else
                    {
                        n1.setError("Please Enter Only Word");
                        c=0;
                    }
                }
                if(b2.matches(""))
                {
                    b1.setError("Enter BirthDate");
                    c=0;
                }
                else
                {
                    if(b2.matches("[a-zA-Z]+"))
                    {
                        b1.setError("Please Enter Only Number");
                        c=0;
                    }
                }

                    if(c2.matches(""))
                {
                    c1.setError("Enter Number");
                    c=0;
                }
                else
                {
                    if(c2.matches("[a-zA-Z]+"))
                    {
                        c1.setError("Please Enter Only Number");
                        c=0;
                    }
                    else
                    {
                        if(c2.length()<9)
                        {
                            c1.setError("Required Length Is 10 For Number");
                        }
                    }
                }
                if(e2.matches(""))
                {
                    e1.setError("Enter Email");
                    c=0;
                }
                else
                {
                    if (!Patterns.EMAIL_ADDRESS.matcher(e2).matches()) {
                        e1.setError("Please Enter Valid Email");
                    }
                }
                if(s2.matches(""))
                {
                    s1.setError("Enter Div");
                    c=0;
                }
                else
                {
                    if(s2.matches("[a-zA-Z]+"))
                    {
                    }
                    else {
                        s1.setError("Please Enter Only Number");
                        c=0;
                    }
                }
                int selectedId = r1.getCheckedRadioButtonId();
                r = (RadioButton) view.findViewById(selectedId);
                r2=r.getText().toString();
                if(c==1)
                {
                    p1 = new ProgressDialog(v.getContext());
                    p1.setCancelable(true);
                    p1.setMessage("Validating");
                    p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    p1.show();


                    Date ca = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String month2 = df.format(ca);


                    new Asyncemp().execute(n2,r2,b2,c2,e2,dep,s2,month2);

                }
            }
        });
        d1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){

                dep = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

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

    public void loadspinner() {
        new Async().execute();
    }
    private class Asyncemp extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/insert_student.php");

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
                        .appendQueryParameter("gender", params[1])
                        .appendQueryParameter("birthdate", params[2])
                        .appendQueryParameter("contact", params[3])
                        .appendQueryParameter("email", params[4])
                        .appendQueryParameter("std", params[5])
                        .appendQueryParameter("div", params[6])
                        .appendQueryParameter("datec", params[7])
                        ;
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
            p1.dismiss();



            //this method will be running on UI thread

            if(check.matches("1"))
            {
                n1.setText("");
                b1.setText("");
                c1.setText("");
                e1.setText("");
                s1.setText("");

                Toast.makeText(getActivity(), "Student Entered", Toast.LENGTH_LONG).show();


            }else if (check.matches("0")){


                // If username and password does not match display a error message
                Toast.makeText(getActivity(), "Student Not Entered", Toast.LENGTH_LONG).show();

            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class Async extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/select_std.php");

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
                JSONArray r2=reader.getJSONArray("result");
                JSONObject jsonObject = r2.getJSONObject(0);
                check= jsonObject.getString("sessionID");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //this method will be running on UI thread

            if(check.matches("1"))
            {

                try {
                    System.out.println(result);
                    JSONObject reader = new JSONObject(result);
                    JSONArray r2=reader.getJSONArray("result");

                    for (int i=1;i<r2.length();i++) {

                        System.out.println(i);
                        JSONObject jsonObject = r2.getJSONObject(i);
                        System.out.println(jsonObject);
                        String r3=jsonObject.getString("name");
                        System.out.println(r3);
                        labels.add(r3);
                    }
                    System.out.println(labels);

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, labels);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        d1.setAdapter(dataAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if (check.matches("0")){

                t1.setText("Please Enter Department");

            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }





}
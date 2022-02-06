package com.example.employeeezzy;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class department extends Fragment {

    View view;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter ;
    ListView LISTVIEW;
    String result;
    ProgressDialog p1;

    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_department, container, false);

        LISTVIEW = (ListView) view.findViewById(R.id.ll);

        ID_Array = new ArrayList<String>();

        NAME_Array = new ArrayList<String>();


        ID_Array.clear();
        NAME_Array.clear();

        p1 = new ProgressDialog(getActivity());
        p1.setCancelable(true);
        p1.setMessage("Validating");
        p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p1.show();



        new Async().execute();



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
        return  view;
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
                url = new URL("https://studentezzy.000webhostapp.com/select_id2.php");

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
            p1.dismiss();

            //this method will be running on UI thread

            if(check.matches("1"))
            {

                try {
                    System.out.println(result);
                    JSONObject reader = new JSONObject(result);
                    JSONArray r2=reader.getJSONArray("result");

                    for (int i=1;i<r2.length();i++) {

                        JSONObject jsonObject = r2.getJSONObject(i);
                        String r3=jsonObject.getString("id");
                        String r4=jsonObject.getString("name");
                        ID_Array.add(r3);
                        NAME_Array.add(r4);
                    }
                    listAdapter = new com.example.employeeezzy.ListAdapter2(getActivity(),

                            ID_Array,
                            NAME_Array
                    );

                    LISTVIEW.setAdapter(listAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if (check.matches("0")){


            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


}
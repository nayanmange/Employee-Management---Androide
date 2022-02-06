package com.example.employeeezzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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


public class home extends Fragment {

    View view;
    TextView t1,t2,t3,t4,t5,t6;
    String result;
    ProgressDialog p1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);

        t1=(TextView) view.findViewById(R.id.depa);
        t2=(TextView) view.findViewById(R.id.empt);
        t3=(TextView) view.findViewById(R.id.vtmp);
        t4=(TextView) view.findViewById(R.id.vtdp);
        t5=(TextView) view.findViewById(R.id.info);
        t6=(TextView) view.findViewById(R.id.salt);

        Date ca = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String month2 = df.format(ca);

        p1 = new ProgressDialog(getActivity());
        p1.setCancelable(true);
        p1.setMessage("Loading");
        p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p1.show();



        new Async().execute();
        new Async2().execute();
        new Async3().execute();
        new Async4().execute(month2);
        new Async5().execute(month2);


        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());

        File file=new File(getActivity().getExternalFilesDir(month_name).toString());
        File[] list = file.listFiles();
        Integer count = 0;
        for (File f: list){

                count++;
        }
        t6.setText(count.toString());

        return  view;
    }
    private class Async extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("1");

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/count_std.php");

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
                        JSONObject jsonObject = r2.getJSONObject(1);
                        String r3=jsonObject.getString("count");

                    t1.setText(r3.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }else if (check.matches("0")){

                t1.setText("0");


            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    private class Async2 extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("2");

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/count_student.php");

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
                    JSONObject jsonObject = r2.getJSONObject(1);
                    String r3=jsonObject.getString("count");

                    t2.setText(r3.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }else if (check.matches("0")){

                t2.setText("0");


            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    private class Async3 extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("3");


        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/count_users.php");

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
                    JSONObject jsonObject = r2.getJSONObject(1);
                    String r3=jsonObject.getString("count");

                    t5.setText(r3.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }else if (check.matches("0")){

                t5.setText("0");


            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    private class Async4 extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("4");

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/countstd2.php");

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
                        .appendQueryParameter("date", params[0]);
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
                JSONArray r2=reader.getJSONArray("result");
                JSONObject jsonObject = r2.getJSONObject(0);
                check= jsonObject.getString("sessionID");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            //this method will be running on UI thread

            if(check.equalsIgnoreCase("1"))
            {

                try {
                    System.out.println(result);
                    JSONObject reader = new JSONObject(result);
                    JSONArray r2=reader.getJSONArray("result");
                    JSONObject jsonObject = r2.getJSONObject(1);
                    String r3=jsonObject.getString("count");

                    t4.setText(r3.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if (check.equalsIgnoreCase("0")){

                // If username and password does not match display a error message

                t4.setText("0");
            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    private class Async5 extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("5");

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/countstudent2.php");

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
                        .appendQueryParameter("date", params[0]);
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
                JSONArray r2=reader.getJSONArray("result");
                JSONObject jsonObject = r2.getJSONObject(0);
                check= jsonObject.getString("sessionID");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            p1.dismiss();

            //this method will be running on UI thread

            if(check.equalsIgnoreCase("1"))
            {

                try {
                    System.out.println(result);
                    JSONObject reader = new JSONObject(result);
                    JSONArray r2=reader.getJSONArray("result");
                    JSONObject jsonObject = r2.getJSONObject(1);
                    String r3=jsonObject.getString("count");

                    t3.setText(r3.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if (check.equalsIgnoreCase("0")){

                // If username and password does not match display a error message

                t3.setText("0");
            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

}
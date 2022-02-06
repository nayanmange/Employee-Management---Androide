package com.example.employeeezzy;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class salaryslip extends Fragment {

    String id="";
    EditText ed;
    View view;
    Button b1;
    String result;
    ProgressDialog p1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_salaryslip, container, false);

        ed = (EditText) view.findViewById(R.id.nameofiep);
        b1 = (Button) view.findViewById(R.id.salary);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id = ed.getText().toString();

                if(id.matches(""))
                {
                    ed.setError("Please Enter Id");
                }
                else {
                    p1 = new ProgressDialog(v.getContext());
                    p1.setCancelable(true);
                    p1.setMessage("Validating");
                    p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    p1.show();
                    new Async().execute(id);
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
                url = new URL("https://studentezzy.000webhostapp.com/check_stu.php");

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

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id", params[0]);
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

            if(check.matches("0"))
            {
                new Async2().execute(id);
            }else if (check.matches("1")){

                Toast.makeText(getActivity(), "User Not Found", Toast.LENGTH_LONG).show();


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

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://studentezzy.000webhostapp.com/salary.php");

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

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id", params[0]);
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {

            String check=null;
            String name=null;
            String salary=null;
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
                    JSONObject jsonObject = r2.getJSONObject(1);
                    name=jsonObject.getString("name");
                    salary=jsonObject.getString("div");

                    } catch (JSONException e) {
                    e.printStackTrace();
                }

                Calendar cal=Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                String month_name = month_date.format(cal.getTime());

                Date ca = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String month2 = df.format(ca);

                final File fol;
                if (Build.VERSION.SDK_INT < 11) {
                    fol = new File(Environment.getExternalStorageDirectory().getPath() + month_name);
                } else {
                    fol = new File(getActivity().getExternalFilesDir(month_name).toString());
                }
                System.out.println(fol);
                if (fol.exists()) {
                    if (Build.VERSION.SDK_INT < 11) {
                        fol.mkdirs();
                    } else {
                        getActivity().getExternalFilesDir(month_name);
                    }
                }
                String pn;
                pn=id+"_"+month_name+".pdf";

                try {
                    final File file = new File(fol, pn.toString());
                    file.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(file);
                    String data = "Employee Ezzy";
                    String data2 = "Name:";
                    String data3 = "Date:";
                    String data4 = "Salary:";
                    String data5 = "Employee No:";
                    String data6 = "------------------------------------------------------------------------------------------";


                    PdfDocument document = new PdfDocument();
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 200, 1).create();
                    PdfDocument.Page page = document.startPage(pageInfo);
                    Canvas canvas = page.getCanvas();
                    Paint paint = new Paint();
                    Paint paint2 = new Paint();


                    paint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(15);


                    canvas.drawText(data, 200, 20, paint);
                    canvas.drawText(data6, 200, 35, paint);
                    paint.setTextSize(10);
                    canvas.drawText(data5, 55, 65, paint2);
                    canvas.drawText(id,150,65,paint2);
                    canvas.drawText(data3, 250, 65, paint2);
                    canvas.drawText(month2,290,65,paint2);
                    canvas.drawText(data2, 55, 80, paint2);
                    canvas.drawText(name,150,80,paint2);
                    canvas.drawText(data4, 55, 95, paint2);
                    canvas.drawText(salary,150,95,paint2);


                    document.finishPage(page);
                    document.writeTo(fOut);
                    document.close();
                    Toast.makeText(getActivity(), "PDF Generated", Toast.LENGTH_LONG).show();
                    ed.setText("");

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }else if (check.matches("0")){
                Toast.makeText(getActivity(), "PDF Not Generated", Toast.LENGTH_LONG).show();


            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }

}
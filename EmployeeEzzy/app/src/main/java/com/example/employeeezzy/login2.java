package com.example.employeeezzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.OkHttpClient;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class login2 extends AppCompatActivity {

    Button b1;
    ImageView mg;
    TextView st,ft,er;
    EditText uname,password;
    String name,pass;
    String result;
    ProgressDialog p1;
    public static final int RequestPermissionCode = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);



        b1=findViewById(R.id.button_login);
        ft=findViewById(R.id.forgot);
        st=findViewById(R.id.signup);
        uname=findViewById(R.id.editTextTextPersonName1);
        password=findViewById(R.id.Password);
        mg=findViewById(R.id.hide);
        er=findViewById(R.id.error);
        if(CheckingPermissionIsEnabledOrNot())
        {
        }

        else {

            //Calling method to enable permission.
            RequestMultiplePermission();

        }

        hide();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name=uname.getText().toString();
                pass=password.getText().toString();

                if(name.matches(""))
                {
                    uname.setError("Please Enter UserName");
                }
                if(pass.matches(""))
                {
                    password.setError("Please Enter Password");
                }
                if(name.length()>0 && pass.length()>0)
                {
                    if(CheckingPermissionIsEnabledOrNot())
                    {

                        p1 = new ProgressDialog(v.getContext());
                        p1.setCancelable(true);
                        p1.setMessage("Validating");
                        p1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        p1.show();



                        new AsyncLogin().execute(name,pass);

                    }
                    else
                    {
                        Toast.makeText(login2.this, "Please Reinstall The App", Toast.LENGTH_LONG).show();
                    }
                    }

                }
        });
    }


    public void signup(View view) {

        Intent intent=new Intent(login2.this,newuser.class);
        startActivity(intent);
    }

    public void forgot(View view) {

        Intent intent=new Intent(login2.this,forgotuser.class);
        startActivity(intent);
    }
    public void hide() {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    mg.setVisibility(View.VISIBLE);
                }
                if(s.toString().trim().length()==0)
                {
                    mg.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    public void viewch(View view) {

        if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            //Show Password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(login2.this, new String[]
                {
                        SEND_SMS,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        INTERNET,
                        ACCESS_NETWORK_STATE
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordAudioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean SendSMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean  internet= grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean acceess=grantResults[4]== PackageManager.PERMISSION_GRANTED;


                    if (CameraPermission && RecordAudioPermission && SendSMSPermission && internet && acceess) {

                        Toast.makeText(login2.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(login2.this, "Please Reinstall The App", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int fifthPer=ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_NETWORK_STATE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult ==PackageManager.PERMISSION_GRANTED &&
                fifthPer == PackageManager.PERMISSION_GRANTED;
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
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
                url = new URL("https://studentezzy.000webhostapp.com/login.php");

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
                        .appendQueryParameter("pass", params[1]);
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


            //this method will be running on UI thread

            if(check.equalsIgnoreCase("1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(login2.this, NavmapActivity.class);
                startActivity(intent);
                finishAffinity();
                er.setText("");

            }else if (check.equalsIgnoreCase("0")){

                // If username and password does not match display a error message
                Toast.makeText(login2.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (check.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(login2.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


}
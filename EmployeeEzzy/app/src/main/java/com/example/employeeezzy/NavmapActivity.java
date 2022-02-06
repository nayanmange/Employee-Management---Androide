package com.example.employeeezzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class NavmapActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navmap);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new home()).commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navmap, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new home()).commit();
                break;

            case R.id.nav_dep:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new newdepartment()).commit();
                break;

            case R.id.nav_depa:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new department()).commit();
                break;
            case R.id.nav_emp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new newemployee()).commit();
                break;
            case R.id.nav_empa:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new employee()).commit();
                break;
            case R.id.nav_sal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new salaryslip()).commit();
                break;
            case R.id.nav_con:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new contact()).commit();
                break;
            case R.id.nav_abu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new about()).commit();
                break;
            case R.id.nav_log:
                Intent intent=new Intent(NavmapActivity.this,login2.class);
                startActivity(intent);
                finishAffinity();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
        }
    }
}
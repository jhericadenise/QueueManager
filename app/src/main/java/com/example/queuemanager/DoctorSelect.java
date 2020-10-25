package com.example.queuemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.queuemanager.adapter.DoctorAdapter;
import com.example.queuemanager.list.DepartmentList;
import com.example.queuemanager.model.Department;
import com.example.queuemanager.model.Doctor;
import com.example.queuemanager.list.DoctorList;
import com.example.queuemanager.session.KeruxSession;
import com.example.queuemanager.session.QueueSession;

import java.util.ArrayList;

public class DoctorSelect extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Doctor> doctors;
    private DoctorAdapter doctorAdapter;

    private TextView depname;

    private KeruxSession session;
    private QueueSession qs;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_select);

        session = new KeruxSession(getApplicationContext());
        qs=new QueueSession(getApplicationContext());

        depname = (TextView)findViewById(R.id.txtView_departmentName);
        depname.setText(qs.getdepartmentname());

        drawerLayout = findViewById(R.id.drawer_layout);

        listView =(ListView) findViewById(R.id.listView_Doctors);
        DoctorList dl = new DoctorList(DoctorSelect.this, listView, session.getclinicid());
        dl.execute();
        Log.d("HELP", session.getclinicid());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(DoctorSelect.this, Queue.class);
                Doctor item = (Doctor)listView.getAdapter().getItem(position);
                qs.setdoctorid(Integer.toString(item.getDoctorid()));
                qs.setdoctorfirstname(item.getDoctorfirstname());
                qs.setdoctorlastname(item.getDoctorlastname());
                startActivity(intent);
                finish();
            }
        });
    }

    public void ClickMenu (View view){
        //open drawer
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo (View view){
        //Close drawer
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        //Redirect activity to dashboard
        Dashboard.redirectActivity(this, MainActivity.class);
    }

    public void ClickCurrentQueue(View view){
        //Redirect activity to manage accounts
        Dashboard.redirectActivity(this, Queue.class);
    }

    public void ClickLogout(View view){
        Dashboard.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        Dashboard.closeDrawer(drawerLayout);
    }
}
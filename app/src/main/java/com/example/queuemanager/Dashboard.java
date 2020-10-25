package com.example.queuemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.queuemanager.adapter.DepartmentAdapter;
import com.example.queuemanager.list.DepartmentList;
import com.example.queuemanager.model.Department;
import com.example.queuemanager.session.KeruxSession;
import com.example.queuemanager.session.QueueSession;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Department> departments;
    private DepartmentAdapter departmentAdapter;

    private TextView qmname;

    private KeruxSession session;
    private QueueSession qs;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new KeruxSession(getApplicationContext());
        qs = new QueueSession(getApplicationContext());

        qmname = (TextView)findViewById(R.id.txtView_qmname);
        qmname.setText(session.getfirstname()+" "+session.getlastname());

        drawerLayout = findViewById(R.id.drawer_layout);

        listView =(ListView) findViewById(R.id.listView_Departments);
        DepartmentList dl = new DepartmentList(Dashboard.this, listView, session.getclinicid());
        dl.execute();
        Log.d("HELP", session.getclinicid());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(Dashboard.this, DoctorSelect.class);
                Department item = (Department)listView.getAdapter().getItem(position);
                qs.setdepartmentid(Integer.toString(item.getDepartmentId()));
                qs.setdepartmentname(item.getDepartmentName());
                startActivity(intent);
            }
        });

    }


    public void ClickMenu (View view){
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        //Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        //Recreate activity
        recreate();
    }

    public void ClickCurrentQueue(View view){
        //Redirect activity to manage accounts
        redirectActivity(this, Queue.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(final Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });

        //negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dismiss dialog
                dialogInterface.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Initialize intent
        Intent intent = new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
}
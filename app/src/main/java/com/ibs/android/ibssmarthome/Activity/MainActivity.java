package com.ibs.android.ibssmarthome.Activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Fragment.HomeFragment;
import com.ibs.android.ibssmarthome.Fragment.NewFeedFragment;
import com.ibs.android.ibssmarthome.Fragment.NewsFragment;
import com.ibs.android.ibssmarthome.Fragment.RequestFragment;
import com.ibs.android.ibssmarthome.Fragment.UtilitiesFragment;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.HomeObject;
import com.ibs.android.ibssmarthome.Object.TCPClient;
import com.ibs.android.ibssmarthome.Object.TCPMessageObject;
import com.ibs.android.ibssmarthome.R;
import com.ibs.android.ibssmarthome.Utils.JSONDeserializeObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView    mBnvMain;
    private FragmentManager         mFrMngMain;
    private DrawerLayout            mDlMain;
    private String                  mApartmentId,mAccount,mPassword;
    public static TCPClient         mTcpClient=null;
    private ProgressDialog          dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get Apartment ID from Login activity
        mApartmentId = getIntent().getStringExtra("LOGIN_APARTMENT_ID");
        mAccount     = getIntent().getStringExtra("LOGIN_USER_ACCOUNT");
        mPassword    = getIntent().getStringExtra("LOGIN_USER_PASSWORD");
        Global.apartmentId = mApartmentId;

        ConnectServer();
        SendLoginMessage();

        //region Animation
        //Activity animation in
        Fade enterTransition = new Fade();
        enterTransition.setDuration(500);
        getWindow().setEnterTransition(enterTransition);
        //endregion

        //region Denife Control
        mFrMngMain = getSupportFragmentManager();
        mDlMain = findViewById(R.id.drawer_layout_main);
        mBnvMain = findViewById(R.id.bottomNavigationView_main_menu);
        NavigationView nvMainRight = findViewById(R.id.navigationView_main_right);

        //endregion

        //region Set Control
        mBnvMain.setItemIconTintList(null);
        mBnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Menu menu = mBnvMain.getMenu();

                //Reset item bottom navigation view to default
                menu.findItem(R.id.item_main_newfeed).setIcon(R.drawable.main_newfeeditemdefault_48px);
                menu.findItem(R.id.item_main_home).setIcon(R.drawable.main_homeitemdefault_48px);
                menu.findItem(R.id.item_main_request).setIcon(R.drawable.main_requestitemdefault_48px);
                menu.findItem(R.id.item_main_utilities).setIcon(R.drawable.main_utilityitemdefault_48px);
                menu.findItem(R.id.item_main_notification).setIcon(R.drawable.main_notificationitemdefault_48px);

                //Load selected fragment
                switch (menuItem.getItemId()) {
                    case R.id.item_main_newfeed:
                        menu.findItem(R.id.item_main_newfeed).setIcon(R.drawable.main_newfeeditemselected_48px);
                        LoadFragment(new NewFeedFragment());
                        break;
                    case R.id.item_main_home:
                        menu.findItem(R.id.item_main_home).setIcon(R.drawable.main_homeitemselected_48px);
                        LoadFragment(new HomeFragment());
                        break;
                    case R.id.item_main_request:
                        menu.findItem(R.id.item_main_request).setIcon(R.drawable.main_requestitemselected_48px);
                        LoadFragment(new RequestFragment());
                        break;
                    case R.id.item_main_utilities:
                        menu.findItem(R.id.item_main_utilities).setIcon(R.drawable.main_utilitiesitemselected_48px);
                        LoadFragment(new UtilitiesFragment());
                        break;
                    case R.id.item_main_notification:
                        menu.findItem(R.id.item_main_notification).setIcon(R.drawable.main_notificationitemselected_48px);
                        LoadFragment(new NewsFragment());
                        break;
                }
                return true;
            }
        });

        //Right menu event
        mDlMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        //Add default fragment
        AddFragment(new NewFeedFragment());
        //endregion

        LoadApartmentData();

    }

    private void LoadFragment(Fragment fragment) {
        FragmentTransaction ft_rep = mFrMngMain.beginTransaction();
        ft_rep.replace(R.id.relativelayout_main, fragment);
        //  t_rep.addToBackStack(null);
        ft_rep.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_rep.commit();
    }
    private void AddFragment(Fragment fragment) {
        FragmentTransaction ft_add = mFrMngMain.beginTransaction();
        ft_add.add(R.id.relativelayout_main, fragment);
        //ft_rep.addToBackStack(null);
        ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_add.commit();

    }
    public class connectTask extends AsyncTask<String,String, TCPClient> {
        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.Connect();
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //Process message
            String[] Data = values[0].split(";");

            if (Data.length>0){
                switch (Data[0]){
                    case Comm.MESSAGE_CODE_LOGIN_RSP:
//                        if (Data[2].matches("FAILD")){
//                            //Login fail
//                            Global.sHome = null;
//                        }
//                        else {
//                            HomeObject home = JSONDeserializeObject.JSONToHomeObject(Data[2]);
//                            Global.sHome = home;
//                            roomsAdapter.notifyDataSetChanged();
//                        }
                        break;
                    case Comm.MESSAGE_CODE_SEND_POINT:
                        String topic = Data[2];
                        String value = Data[3];
                        Log.d("SmartHome","POINT - Topic: "+topic);
                        Log.d("SmartHome","POINT - Message: "+value);
                        Global.UpdatePointValueByTopic(topic,value);
                        break;
                }
            }
            //in the arrayList we add the messaged received from server
            //arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            //mAdapter.notifyDataSetChanged();
        }
    }
    private void SendLoginMessage(){
        dialog = ProgressDialog.show(this, "Connecting server", "Please wait...", true);
        //When not yet connected to server
        while (mTcpClient==null){
            dialog.show();
            Log.d("SmartHome","MainActivity: TCP CLIENT NULL");
        }

        //When connected to server
        dialog.dismiss();
        Log.d("SmartHome","MainActivity: CLIENT NOT NULL");

        //region Make login message
        ArrayList<String> params = new ArrayList<>();
        params.add(mAccount);
        params.add(mPassword);
        TCPMessageObject loginMess = new TCPMessageObject(Comm.MESSAGE_CODE_LOGIN, params);
        //endregion
        if (mTcpClient!=null)
        {
            mTcpClient.SendMessage(loginMess.toString());
            //mTcpClient.SendMessage("1201;0025;manhdb;12071993");
            Log.d("SmartHome","MainActivity: LOGIN MESS SEND");
        }
        else {
            Log.d("SmartHome","MainActivity: LOGIN MESS NOT SEND");
        }
    }
    private void ConnectServer(){
        //Connect server
        try {
            new connectTask().execute("");
        }
        catch (Exception ex){
            Comm.ShowDialog("Error connect",ex.toString(),MainActivity.this);
            return;
        }

    }
    private void LoadApartmentData() {
        String Url = Comm.strAPI + "Apartment/"+mApartmentId;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response;
                Global.sHome = JSONDeserializeObject.JSONToHomeObject(jsonData);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SmartHome","HomeFragment: Can't get home info");
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    public void finish() {
        super.finish();
        ShowDialogFinish("Logout","Do you really want log out?");
    }
    public void ShowDialogFinish(String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

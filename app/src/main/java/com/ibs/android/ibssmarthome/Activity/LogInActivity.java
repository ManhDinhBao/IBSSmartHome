package com.ibs.android.ibssmarthome.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.blurry.Blurry;

public class LogInActivity extends AppCompatActivity {
    private EditText mEtAccount,mEtPassword;
    private Button mBtLogIn,mBtForgotPass;
    private ImageView mIvBlurBackground;
    private String sAccount, sPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //region Control Define
        mEtAccount          =findViewById(R.id.editText_login_account);
        mEtPassword         =findViewById(R.id.editText_login_password);
        mBtLogIn            =findViewById(R.id.button_login_signIn);
        mBtForgotPass       =findViewById(R.id.button_login_forGotPassword);
        mIvBlurBackground   =findViewById(R.id.imageView_login_blurBackground);
        //endregion

        //region Control Set
        Bitmap bm           = BitmapFactory.decodeResource(getResources(), R.drawable.login_background);
        Blurry.with(this).sampling(1).from(bm).into(mIvBlurBackground);

        mEtPassword.setText("12071993");
        mEtAccount.setText("manhdb");
        //endregion

        //region Control Event
        mBtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAccount  = mEtAccount.getText().toString();
                sPassword     = mEtPassword.getText().toString();

                CheckAccount(sAccount,sPassword);
            }
        });
        //endregion
    }

    private void CheckAccount(String account, String password) {
        //Check valid account
        String Url = Comm.strAPI + "Login";
        final String user = account, pass = password;
        Map<String,String> params = new HashMap<String, String>();
        params.put("Username",user);
        params.put("Password",pass);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST, Url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String apartmentId;
                try {
                    apartmentId = response.getString("ApartmentID");
                }
                catch (JSONException ex){
                    apartmentId = null;
                }

                //User have 1 apartment, go to main activity
                if (apartmentId!=null){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this);
                    Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                    intent.putExtra("LOGIN_APARTMENT_ID",apartmentId);
                    intent.putExtra("LOGIN_USER_ACCOUNT",sAccount);
                    intent.putExtra("LOGIN_USER_PASSWORD",sPassword);
                    startActivity(intent,options.toBundle());
                }
                else {
                    //Show message
                    Global.ShowDialog("Error","User don't have role with any apartment",LogInActivity.this);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Global.ShowDialog("Error","Wrong account or password",LogInActivity.this);
            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}

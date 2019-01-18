package com.ibs.android.ibssmarthome.Object;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibs.android.ibssmarthome.Comm;

public class ServerObject {
    private String IPAddress;
    private String ExtraAđress;
    private String Body;
    private String Header;

    public ServerObject(String IPAddress, String extraAđress, String body, String header) {
        this.IPAddress = IPAddress;
        ExtraAđress = extraAđress;
        Body = body;
        Header = header;
    }

    public ServerObject() {
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getExtraAđress() {
        return ExtraAđress;
    }

    public void setExtraAđress(String extraAđress) {
        ExtraAđress = extraAđress;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    private String Put(Context context) {
        final String[] result = {Comm.API_RESULT_OK};
        String Url = getIPAddress() + getExtraAđress();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               result[0] = response.toString();
               return;
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result[0] = error.toString();
                return;
            }
        });

        Volley.newRequestQueue(context).add(stringRequest);

        return  result[0] ;
    }

    private String Post(Context context) {
        final String[] result = {Comm.API_RESULT_OK};
        String Url = getIPAddress() + getExtraAđress();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] = response.toString();
                return;
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result[0] = error.toString();
                return;
            }
        });

        Volley.newRequestQueue(context).add(stringRequest);

        return  result[0] ;
    }
}

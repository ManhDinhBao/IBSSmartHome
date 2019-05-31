package com.ibs.android.ibssmarthome.Object;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private String serverMessage;
    public static final String SERVER_IP = "192.168.1.51";
    public static final int SERVER_PORT = 16707;
    private OnMessageReceived mMessageListener=null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void SendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.print(message);
            out.flush();
            Log.d("SmartHome","TCPClient - Send: "+message);
        }
    }

    public void StopClient() {
        mRun = false;
    }

    public void Connect() {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.e("TCPClient", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            try {

                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.d("TCPClient", "C: Sent.");

                Log.d("TCPClient", "C: Done.");

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int charsRead = 0;
                char[] buffer = new char[1024];
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    charsRead = in.read(buffer);
                    //serverMessage = in.readLine();
                    serverMessage = new String(buffer).substring(0, charsRead);

                    if (serverMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
                }

            } catch (Exception e) {

                Log.e("TCPClient", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);
        }
    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the Activity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        void messageReceived(String message);
    }
}


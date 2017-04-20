package com.dtt.zackrzot.lightcommandclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zack on 7/22/2016.
 */
public final class SocketManager {

    public static Boolean validConfigDetected = false;
    public static String validIP = null;
    public static String validPort = null;


    public static Boolean connect(String ip, String port){

        Socket socket = null;
        DataOutputStream dataOutputStream = null;

        try {
            socket = new Socket(ip, Integer.parseInt(port));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBytes("Test Connect.<EOF>");
            validConfigDetected = true;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            validConfigDetected = false;
        }
        finally{
            if (socket != null){
                try {
                    socket.close();
                    validConfigDetected = true;
                    validIP = ip;
                    validPort = port;
                    return true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    validConfigDetected = false;
                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    validConfigDetected = false;
                }
            }
        }

        validConfigDetected = false;
        return false;

    }

    public static void sendMessage(String message, Context x){

        if(!validConfigDetected){
            Toast toast = Toast.makeText(x, "Not connected to server you shit head.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(!connect(validIP , validPort)) {
            Toast toast = Toast.makeText(x, "Connection invalid. Go back and and check the settings you ass.", Toast.LENGTH_SHORT);
            toast.show();
            validConfigDetected = false;
            return;
        }

        Socket socket = null;
        DataOutputStream dataOutputStream = null;

        try {
            socket = new Socket(validIP, Integer.parseInt(validPort));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBytes(message + "<EOF>");
            validConfigDetected = true;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            validConfigDetected = false;
        }
        finally{
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    validConfigDetected = false;
                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    validConfigDetected = false;
                }
            }
        }

        Toast toast = Toast.makeText(x, "Command sent!", Toast.LENGTH_SHORT);
        toast.show();

    }



}

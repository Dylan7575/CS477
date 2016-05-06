package com.example.dylan.cs477;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by user on 4/20/16.
 */

public abstract class Connection extends AsyncTask<String, Integer, String>{

    String serverResponse = null;
    String content;
    String error;
    String data = "";
    static String dataUrl = "localhost:8080";
    int port = 8080;

    public Connection(){

    }

    class EstablishmentData{

    }



    protected static JSONObject getRequest(String params) {

        // this is the address of the data or where you want to put the data
        URL url;
        HttpURLConnection connection = null;
        String result = "";
        try {
            // Create connection
            url = new URL(new StringBuilder().append(dataUrl).append(params).toString());
            // opens the connection to the url
            connection = (HttpURLConnection) url.openConnection();
            // prep the connection
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // Get Response
            // Read response to string
            try {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    data = reader.read();
                    result += current;
                }
            } catch (Exception e) {
                return null;
            }
            JSONObject jsonO = new JSONObject(result);
            Log.d("Server response: \n", result);
            return jsonO;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
        JSONObject job = null;
        try {
            job = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return job;
    }


    protected String putRequest(String params, JSONObject jsonParam) {
        HttpURLConnection client;
        DataOutputStream printout;
        URL url;
        StringBuilder stringBuilder = null;
        try {
            url = new URL(new StringBuilder().append(dataUrl).append(params).toString());
            client = (HttpURLConnection) url.openConnection();
            client.setDoInput(true);
            client.setDoOutput(true);
            client.setRequestProperty("Content-Type", "application/json");
            client.setRequestMethod("PUT");
            client.setRequestProperty("Accept", "application/json");

            client.connect();
            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());

            printout = new DataOutputStream(client.getOutputStream());
            printout.writeUTF(jsonParam.toString());
            printout.flush();
            printout.close();

            InputStream is = client.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            stringBuilder = new StringBuilder();

            String line = rd.readLine();

            while (line != null) {
                stringBuilder.append(line + " \n");
                line = rd.readLine();
            }
            rd.close();
            Log.d("Server response: \n", stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    protected String postRequest(String params, JSONObject jsonParam) {
        URL url;
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = null;
        try {
            // Create connection from the
            url = new URL(new StringBuilder().append(dataUrl).append(params).toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.connect();
            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            wr.writeUTF(jsonParam.toString());
            wr.flush();
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            stringBuilder = new StringBuilder();
            String line = rd.readLine();

            while (line != null) {
                stringBuilder.append(line + " \n");
                line = rd.readLine();
            }
            rd.close();
            Log.d("Server response: \n", stringBuilder.toString());

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
        return stringBuilder.toString();
    }

    public String login(String username, String password){
        String clientid = null;
        String theURL = "/client/";
        JSONObject reg = null;
        try {
            reg = new JSONObject();
            reg.put("username", username);
            reg.put("password", password);
            clientid = postRequest(theURL, reg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clientid;
    }

    // done
    public String register(String email, String username, String password){
        String clientid = null;
        String theURL = "/client";
        JSONObject reg = null;
        try {
            reg = new JSONObject();
            reg.put("email", email);
            reg.put("username", username);
            reg.put("password", password);
            clientid = postRequest(theURL, reg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clientid;
    }


    // todo needs to return a list of resturants
    public String[] getEstablishments() {
        JSONObject jsonRootObject = getRequest("/establishment");
        String[] data;
        String list;

        list = jsonRootObject.optString("establishments").toString();
        data = list.split("\n");

        return data;

    }

    // done returns a list of the establishments info name, location, open time
    public String[] getEstablishmentInfo(String establishmentID) {
        String sendParams = "/establishment/:";
        sendParams += establishmentID;
        String[] info = new String[0];
        try {
            JSONObject jsonRootObject = getRequest(sendParams);

            String name = jsonRootObject.optString("name").toString();
            String location = jsonRootObject.optString("location").toString();
            String open = jsonRootObject.optString("start_time").toString();


            info = new String[]{name, location, open};

        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public class TheMenu{
        String message;
        String name;
        String des;
        float price;
        Drawable image;

    }

    // done returns a list of TheMenu
    public TheMenu[] getMenu(String establishmentID){
        String sendParams = "/establishment/:";
        sendParams += establishmentID;
        sendParams += "/menu";
        TheMenu[] men = new TheMenu[0];
        try {
            JSONObject jsonRootObject = getRequest(sendParams);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Menu");
            men = new TheMenu[jsonArray.length()];
            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                men[i].message = jsonObject.optString("message").toString();
                men[i].name = jsonObject.optString("name").toString();
                men[i].des = jsonObject.optString("description").toString();
                men[i].price = Float.parseFloat(jsonObject.optString("price").toString());
                men[i].image = (Drawable) jsonObject.opt("image");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return men;
    }

    // done returns an array of the name, price, description, image, and age restriced
    public TheMenu getMenuEntry(String establishmentID, String menuEntry){
        String sendParams = "/establishment/:" + establishmentID + "/menu" + "/:" + menuEntry;
        TheMenu men = null;


        JSONObject jsonRootObject = getRequest(sendParams);

        men.message = jsonRootObject.optString("message").toString();
        men.name = jsonRootObject.optString("drink_name").toString();
        men.price = Float.parseFloat(jsonRootObject.optString("price").toString());
        men.des = jsonRootObject.optString("description").toString();
        men.image = (Drawable) jsonRootObject.opt("image");

        return men;


    }

    // done adds a client to the list with a parameters ad the client id
    public String postclientID(String username, String email, String password) {
        String sendParams = "/client";
        String json = "";
        JSONObject jsonObject = null;
        try {
            // build jsonObject
            jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            // convert JSONObject to a string
            json = jsonObject.toString();
            // set to a string entity

        } catch (Exception e) {
            e.printStackTrace();
        }
        serverResponse = postRequest(sendParams, jsonObject);

        // server responds with the client id this is needed to complete any order functions
        // including logging off
        return serverResponse;
    }

    // done deleted user by url
    public void logout(String username){
        String murl = "/client/";
        murl += username;
        URL myurl = null;
        try {
            myurl = new URL(murl);
            HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            InputStreamReader wr = new InputStreamReader(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // done sends the json of a menu to the server
    public String sendOrderRequest(String establishmentID, String[] drinks) {
        String order = "";
        String sendParams = "/establishment/" + establishmentID + "/" + "orders";
        JSONObject rootjo = new JSONObject();
        JSONArray ja = null;
        try {
            ja = new JSONArray();
            for (int i = 0; i < drinks.length; i++) {
                JSONObject jo = new JSONObject();
                jo.put("name", drinks[i]);
                ja.put(jo);
            }
            rootjo.put("menu", ja);
        } catch (Exception e) {
            e.printStackTrace();
        }
        order = postRequest(sendParams, rootjo);
        return order;
    }
    // use put to update
    public void getOrderRequestUpdate(String[] order) {

    }


    public void sendOrderRequestUpdate(String order, String[] update) {

    }

    //use http delete
    public void sendOrderRequestCancel(String clientID, String order) {
        String sendParams = "/client/:" + clientID + "/orders/:" + order + "/CANCEL_CANCEL";

    }
    public void sendPayment(String[] paymentStuff){

    }
/*
    public static void main(String [] args){
        System.out.println(getEstablishments());

    }

*/
}

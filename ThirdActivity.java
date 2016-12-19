package com.example.joseph.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ThirdActivity extends AppCompatActivity {

    public static String BIERS_UPDATE = "B";
    private BiersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
       // LocalBroadcastManager.getInstance(this).registerReceiver(new ThirdActivity.BierUpdate(), intentFilter);

        GetBieresServices.startACTION_GET_ALL_BIERES(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BiersAdapter(getBieresFromFiles());
        rv.setAdapter(mAdapter);
    }




    public JSONArray getBieresFromFiles(){
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bières.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

}

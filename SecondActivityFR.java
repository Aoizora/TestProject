package com.example.joseph.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondActivityFR extends AppCompatActivity {

    public static String BIERS_UPDATE = "B";
    private BiersAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new SecondActivityFR.BierUpdate(), intentFilter);

        Notification.Builder mBuilder = new Notification.Builder(SecondActivityFR.this).setSmallIcon(R.drawable.smallicon).setContentTitle("Notif")
                .setContentText("Début du téléchargement !").setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(MainActivity.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        GetBieresServices.startACTION_GET_ALL_BIERES(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BiersAdapter(getBieresFromFiles());
        rv.setAdapter(mAdapter);
    }

    public void  onFinish(){
        mAdapter.setNewBiere(getBieresFromFiles());
    }

    public class BierUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Notification.Builder mBuilder = new Notification.Builder(context).setSmallIcon(R.drawable.smallicon).setContentTitle("Notification")
                    .setContentText("Téléchargement fini !");
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());

            onFinish();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menufr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.EN :
                Intent intentEN = new Intent(SecondActivityFR.this, SecondActivityEN.class);
                startActivity(intentEN);
                return true;
        }
        return false;
    }

}

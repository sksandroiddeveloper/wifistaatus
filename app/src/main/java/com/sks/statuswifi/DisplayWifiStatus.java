package com.sks.statuswifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayWifiStatus extends AppCompatActivity {
    TextView textView;
    NetworkInfo wifiCheck;
  Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wifi_status);
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        textView = (TextView) findViewById(R.id.wifi_connection);
        button1 = (Button)findViewById(R.id.wifi_connectionstatus);


        if (wifiCheck.isConnected()) {
            // Do whatever here
            textView.setText("WiFi is Connected");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });


        } else {
            textView.setText("WiFi is not Connected");
        }
    }

    public void checkWiFi(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
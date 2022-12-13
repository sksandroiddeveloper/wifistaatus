package com.sks.statuswifi;

import static android.content.Context.WIFI_SERVICE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
class WifiReceiver extends BroadcastReceiver {

    WifiManager wifiManager;
    StringBuilder sb;
    ListView wifiDeviceList;
    String OurWifiName=null;


    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {

            // How to get connected wifi name
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo;
            wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED){
                Toast.makeText(context, wifiInfo.getSSID(), Toast.LENGTH_SHORT).show();
                OurWifiName= wifiInfo.getSSID().toString().replace("\"","");
            }
            //end
            sb = new StringBuilder();
            List<ScanResult> wifiList = wifiManager.getScanResults();

            ArrayList<String> deviceList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                if(OurWifiName.toLowerCase().equalsIgnoreCase(scanResult.SSID.toLowerCase())){

                    if (scanResult.capabilities.contains("WPA2")) {
                        //do something
                        sb.append("\n").append("Password Protected: "+scanResult.SSID).append(" - ").append(scanResult.capabilities);
                        deviceList.add(sb.toString());
                        break;
                    } else if (scanResult.capabilities.contains("WPA")) {
                        //do something
                        sb.append("\n").append("Password Protected: "+scanResult.SSID).append(" - ").append(scanResult.capabilities);
                        deviceList.add(sb.toString());
                        break;
                    } else if (scanResult.capabilities.contains("WEP")) {
                        //do something
                        sb.append("\n").append("Password Protected: "+scanResult.SSID).append(" - ").append(scanResult.capabilities);
                        deviceList.add(sb.toString());
                        break;
                    }else{
                        sb.append("\n").append("OPEN (Not protected): "+scanResult.SSID).append(" - ").append(scanResult.capabilities);
                        deviceList.add(sb.toString());
                        break;
                    }

                }

            }
            //Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();

            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, deviceList.toArray());
            wifiDeviceList.setAdapter(arrayAdapter);
        }
    }
}

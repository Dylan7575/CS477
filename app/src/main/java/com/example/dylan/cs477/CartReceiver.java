package com.example.dylan.cs477;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CartReceiver extends BroadcastReceiver {
    public CartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "Order has been successfully received.", Toast.LENGTH_SHORT).show();
        //not sure how to make the correct intent but pretty sure a toast message is what we want
    }
}

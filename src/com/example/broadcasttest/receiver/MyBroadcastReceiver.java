package com.example.broadcasttest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String theDate=intent.getStringExtra("theDate");
		Toast.makeText(context, "get theDate="+theDate, Toast.LENGTH_SHORT).show();
		Log.v("test", "Receiver1 get broadcast");
		//Sets the flag indicating that this receiver should abort the current broadcast
		//abortBroadcast();
	}
}

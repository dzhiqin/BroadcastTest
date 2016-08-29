package com.example.broadcasttest;

import java.util.Calendar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	private Button btnSendBroadcast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSendBroadcast=(Button)findViewById(R.id.btn_send);
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver=new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver,intentFilter);
		btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent(String action)
				//Create an intent with a given action
				Intent intent=new Intent("broadcast_mybroadcast");
				intent.putExtra("theDate", getDate());
				sendBroadcast(intent);
			}
		});
		
	}
	/**
	 * 获得时间的函数
	 */
	private String getDate(){
		Calendar c=Calendar.getInstance();//
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);//注意 月份要加1
		String day=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));//用的是24小时制的时间
		String minute=String.valueOf(c.get(Calendar.MINUTE));
		
		StringBuffer theDate=new StringBuffer();
		theDate.append(year+"-"+month+"-"+day+" "+hour+":"+minute);
		return theDate.toString();
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
	}
	class NetworkChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// This method is called when the BroadcastReceiver is receiving an Intent broadcast.
			ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			// NetworkInfo : Describes the status of a network interface
			NetworkInfo networkInfo=manager.getActiveNetworkInfo();
			if(networkInfo!=null&&networkInfo.isAvailable()){
				Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	                                            

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.example.broadcasttest;

import java.util.Calendar;

import com.example.broadcasttest.receiver.MyBroadcastReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	private IntentFilter localIntentFilter;
	private IntentFilter globalIntentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	private LocalReceiver localReceiver;
	/**
	 * ȫ�ֹ㲥������
	 */
	private MyBroadcastReceiver myBroadcastReceiver;
	/**
	 * ���ع㲥������
	 */
	private LocalBroadcastManager localBroadcastManager;
	private Button btnSendBroadcast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSendBroadcast=(Button)findViewById(R.id.btn_send);
		//
		/*myBroadcastReceiver=new MyBroadcastReceiver();
		globalIntentFilter=new IntentFilter();
		globalIntentFilter.addAction("broadcast_mybroadcast");
		registerReceiver(myBroadcastReceiver,globalIntentFilter);
		//��̬ע��㲥����
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver=new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver,intentFilter);*/
		//��ȡ���ع㲥������ʵ��
		localBroadcastManager=LocalBroadcastManager.getInstance(this);
		//��̬ע�᱾�ع㲥����
		localIntentFilter=new IntentFilter();
		localIntentFilter.addAction("local_broadcast");
		localReceiver=new LocalReceiver();
		localBroadcastManager.registerReceiver(localReceiver, localIntentFilter);
		//�������͹㲥
		btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent(String action)
				//Create an intent with a given action
				Intent intent=new Intent("broadcast_mybroadcast");
				intent.putExtra("theDate", getDate());
				//sendBroadcast(intent);
				//��������㲥
				sendOrderedBroadcast(intent,null);
				//���ͱ��ع㲥
				//Intent i=new Intent("local_broadcast");
				//localBroadcastManager.sendBroadcast(i);
				
			}
		});
		
	}
	/**
	 * ���ʱ��ĺ���
	 */
	private String getDate(){
		Calendar c=Calendar.getInstance();//
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);//ע�� �·�Ҫ��1
		String day=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));//�õ���24Сʱ�Ƶ�ʱ��
		String minute=String.valueOf(c.get(Calendar.MINUTE));
		
		StringBuffer theDate=new StringBuffer();
		theDate.append(year+"-"+month+"-"+day+" "+hour+":"+minute);
		return theDate.toString();
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
		localBroadcastManager.unregisterReceiver(localReceiver);
		unregisterReceiver(myBroadcastReceiver);
	}
	/**
	 * ���ڼ�������仯����Ĺ㲥������
	 * @author Administrator
	 *
	 */
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
	 /**
	  * ���ع㲥������                                           
	  */
	class LocalReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.v("test","get localBroadcast");
			
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

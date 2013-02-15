package com.impler.tradingterminal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText userInput;
	private EditText passInput;
	private Button initBtn;
	private TextView callbackText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userInput = (EditText) findViewById(R.id.userInput);
		passInput = (EditText) findViewById(R.id.passInput);
		initBtn = (Button) findViewById(R.id.initBtn);
		callbackText = (TextView) findViewById(R.id.callbackText);
		initBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callbackText.setText("");
				String user = userInput.getText().toString();
				String pass = passInput.getText().toString();
				if(user==null||user.length()==0){
					callbackText.setText(getString(R.string.init_user_empty));
					return;
				}
				if(pass==null||pass.length()==0){
					callbackText.setText(getString(R.string.init_pass_empty));
					return;
				}
				if(pass.length()<5){
					callbackText.setText(getString(R.string.init_pass_less));
					return;
				}
				callbackText.setText("ok");
				Intent intent = new Intent();
	            intent.setClass(MainActivity.this, ModeTabActivity.class);  
	            startActivity(intent);
	            finish();
			}
			
		});
		TelephonyManager phoneMgr=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String phone = phoneMgr.getLine1Number();
		if(phone==null||phone.length()==0)
			return;
		userInput.setText(phone);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}

}

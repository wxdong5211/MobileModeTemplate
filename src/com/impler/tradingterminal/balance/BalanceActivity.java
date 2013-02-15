package com.impler.tradingterminal.balance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.impler.tradingterminal.R;

public class BalanceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	
}

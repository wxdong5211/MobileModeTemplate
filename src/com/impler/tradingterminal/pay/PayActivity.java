package com.impler.tradingterminal.pay;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.impler.tradingterminal.R;

public class PayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}

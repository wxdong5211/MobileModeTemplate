package com.impler.tradingterminal;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.impler.tradingterminal.ModeMgr.ModeInfo;

public class ModeTabActivity extends TabActivity implements OnCheckedChangeListener {
	
	private RadioGroup mainTab;
    private TabHost tabhost;
    private HashMap<String,String> tags;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mode_tab);
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        tabhost = getTabHost();
        Resources resources = getResources();
        tags = new HashMap<String,String>();
        
        ArrayList<ModeInfo> modes = ModeMgr.getAllMode();
        for(ModeInfo mode : modes){
        	initMode(resources,mode);
        }
        
        tabhost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		switch(checkedId){
//        case R.id.radio_button0:
//            this.tabhost.setCurrentTabByTag("iHome");
//            break;
//        default:this.tabhost.setCurrentTabByTag("iHome");
//        }
		tabhost.setCurrentTabByTag(tags.get(checkedId+""));
	}
	
	private void initMode(Resources resources,ModeInfo mode){
		//new RadioButton(this);//,null,R.style.main_tab_bottom
		RadioButton ra = (RadioButton)LayoutInflater.from(this).inflate(R.layout.mode_radiobutton, null);
		int text = mode.getText();
		ra.setText(resources.getString(text));
		int drawableTop = mode.getDrawableTop();
        ra.setCompoundDrawablesWithIntrinsicBounds(0, drawableTop, 0, 0);
        mainTab.addView(ra);
        Class<? extends Activity> activity = mode.getActivity();
        Intent iHome = new Intent(this,activity);
        String tag = activity.getName();
        tabhost.addTab(tabhost.newTabSpec(tag)
              .setIndicator(resources.getString(text), resources.getDrawable(drawableTop))
              .setContent(iHome));
        tags.put(ra.getId()+"", tag);
	}

}

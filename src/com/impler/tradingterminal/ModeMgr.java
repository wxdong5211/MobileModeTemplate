package com.impler.tradingterminal;

import java.util.ArrayList;

import android.app.Activity;

import com.impler.tradingterminal.balance.BalanceActivity;
import com.impler.tradingterminal.pay.PayActivity;

public abstract class ModeMgr {
	
	private static ArrayList<ModeInfo> modes = new ArrayList<ModeInfo>();
	
	static {
		registMode(new ModeInfo(R.string.main_home,R.drawable.icon_1_n,BalanceActivity.class));
		registMode(new ModeInfo(R.string.main_news,R.drawable.icon_2_n,PayActivity.class));
	}
	
	public static void registMode(ModeInfo mode){
		modes.add(mode);
	}
	
	public static ArrayList<ModeInfo> getAllMode(){
		return modes;
	}
	
	public static class ModeInfo {
		private int text;
		private int drawableTop;
		private Class<? extends Activity> activity;

		public ModeInfo(int text, int drawableTop, Class<? extends Activity> activity){
			this.text = text; this.drawableTop = drawableTop; this.activity = activity;
		}
		
		public int getText() {
			return text;
		}

		public int getDrawableTop() {
			return drawableTop;
		}

		public Class<? extends Activity> getActivity() {
			return activity;
		}
	}

}

package com.impler.tradingterminal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Demo
 * @author Invalid
 */
@Deprecated
public class DetailsFragment extends Fragment {

	/**Create a new instance of DetailsFragment, initialized to show the text at 'index'.*/  
    public static DetailsFragment newInstance(int index) {  
    	DetailsFragment f = new DetailsFragment();
        // Supply index input as an argument.  
    	Bundle args = new Bundle();  
        args.putInt("index", index);  
        //给Fragment初始化参数  
        f.setArguments(args);
        return f;  
    }
    
    public int getShownIndex() {  
    	Bundle args = getArguments();
    	return args==null? 0:args.getInt("index", 0);  
    }  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        if (container == null) {  
            // We have different layouts, and in one of them this  
            // fragment's containing frame doesn't exist. The fragment  
            // may still be created from its saved state, but there is  
            // no reason to try to create its view hierarchy because it  
            // won't be displayed. Note this is not needed -- we could  
            // just run the code below, where we would create and return  
            // the view hierarchy; it would just never be used.  
//            return null;  
        }  
  
        ScrollView scroller = new ScrollView(getActivity());  
        TextView text = new TextView(getActivity());  
        int padding = (int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources()  
                        .getDisplayMetrics());  
        // hetao：设置边框大小  
        text.setPadding(padding, padding, padding, padding);  
        scroller.addView(text);  
        text.setText(DIALOGUE[getShownIndex()]);  
        return scroller;  
    }  
    public static final String[] DIALOGUE = {  
            "So shaken as we are, so wan with care,"  
                    + "In forwarding this dear expedience.",  
  
            "Hear him but reason in divinity,"  
                    + "From open haunts and popularity.",  
  
            "I come no more to make you laugh: things now,"  
                    + "A man may weep upon his wedding-day.",  
  
            "First, heaven be the record to my speech!"  
                    + "What my tongue speaks my right drawn sword may prove.",  
  
            "Now is the winter of our discontent"  
                    + "Clarence comes.",  
  
            "To bait fish withal: if it will feed nothing else,"  
                    + "will better the instruction.",  
  
            "Virtue! a fig! 'tis in ourselves that we are thus"  
                    + "you call love to be a sect or scion.",  
  
            "Blow, winds, and crack your cheeks! rage! blow!"};  
    public static final String[] TITLES = { "Henry IV (1)", "Henry V",  
            "Henry VIII", "Richard II", "Richard III", "Merchant of Venice",  
            "Othello", "King Lear" }; 
    
}

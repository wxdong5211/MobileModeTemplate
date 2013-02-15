package com.impler.tradingterminal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Demo
 * @author Invalid
 */
@Deprecated
public class TitlesFragment extends ListFragment {

	boolean mDualPane;  //检验是否有DetailFragment，没有就另起一个activity  
	int mCurCheckPosition = 0;  
	  
    @Override  
  //初始化TitleFragment  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        // Populate list with our static array of titles.  
        setListAdapter(new ArrayAdapter<String>(getActivity(),  
                android.R.layout.simple_list_item_1, TITLES));  
        // Check to see if we have a frame in which to embed the details  
        // fragment directly in the containing UI.  
        View detailsFrame = null;//getActivity().findViewById(R.id.details);  
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;  
  
        if (savedInstanceState != null) {  
            // Restore last state for checked position.  
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);  
        }  
  
        if (mDualPane) {  
            // In dual-pane mode, the list view highlights the selected item.  
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
            // Make sure our UI is in the correct state.  
            showDetails(mCurCheckPosition);  
        }  
    }  
  
    @Override  
    public void onSaveInstanceState(Bundle outState) {  
        super.onSaveInstanceState(outState);  
        outState.putInt("curChoice", mCurCheckPosition);  
    }  
  
    @Override  
    public void onListItemClick(ListView l, View v, int position, long id) {  
        showDetails(position);  
    }  
  
    /** 
     * Helper function to show the details of a selected item, either by 
     * displaying a fragment in-place in the current UI, or starting a 
     * whole new activity in which it is displayed. 
     */  
    void showDetails(int index) {  
        mCurCheckPosition = index;  
  
        if (mDualPane) {  
            // We can display everything in-place with fragments, so update  
            // the list to highlight the selected item and show the data.  
            getListView().setItemChecked(index, true);  
  
            // Check what fragment is currently shown, replace if needed.  
            DetailsFragment details = null;//(DetailsFragment)getFragmentManager().findFragmentById(R.id.details);  
            if (details == null || details.getShownIndex() != index) {  
                details = DetailsFragment.newInstance(index);  
  
                // Execute a transaction, replacing any existing fragment  
                // with this one inside the frame.  
                FragmentTransaction ft = getFragmentManager().beginTransaction();  
                //ft.replace(R.id.details, details);  
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);  
                ft.commit();  
            }  
  
        } else {  
            // Otherwise we need to launch a new activity to display  
            // the dialog fragment with selected text.  
//            Intent intent = new Intent();  
//            intent.setClass(getActivity(), TestActivity.class);  
//            intent.putExtra("index", index);  
//            startActivity(intent);  
        }  
    }  
    //copied by hetao  
    public static final String[] TITLES =   
    {  
            "Henry IV (1)",     
            "Henry V",  
            "Henry VIII",         
            "Richard II",  
            "Richard III",  
            "Merchant of Venice",    
            "Othello",  
            "King Lear"  
    };
    
}

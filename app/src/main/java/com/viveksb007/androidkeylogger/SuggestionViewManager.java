package com.viveksb007.androidkeylogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.GestureDetector;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by pkothari on 6/3/16.
 */
public class SuggestionViewManager extends GestureDetector.SimpleOnGestureListener {
	private final Context context;
	private String eventText;
	private CharSequence specificContactName = null;
	private Handler handler = new Handler();
	public AccessibilityNodeInfoCompat editTextViewNode;

	public SuggestionViewManager(Context context) {
		this.context = context;
	}

	public boolean onTextChanged(String eventText) {
		String lowercaseEvent = eventText.toLowerCase();
		return false;
	}



	public void event(AccessibilityEvent event) {

		AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
		AccessibilityNodeInfoCompat source = record.getSource();
		if (source == null) {
			return;
		}
	}


	public void getAllChildNode(AccessibilityNodeInfo node) {
		if (node == null) {
			return;
		}
		int child = node.getChildCount();
		if(node.getClassName()!=null && node.getClassName().equals("android.widget.RelativeLayout")){
			int tempChild=node.getChildCount();
//			if(tempChild==4){
//                //check its Contact type
//                AccessibilityNodeInfo firstChild=node.getChild(0);
//                AccessibilityNodeInfo secondChild=node.getChild(1);
//                AccessibilityNodeInfo thirdChild=node.getChild(2);
//                AccessibilityNodeInfo fourthChild=node.getChild(3);
//                if(firstChild!=null && firstChild.getClassName()!=null && firstChild.getClassName().equals("android.widget.ImageView")
//                        && secondChild!=null && secondChild.getClassName()!=null && secondChild.getClassName().equals("android.widget.TextView")
//                        && thirdChild!=null && thirdChild.getClassName()!=null && thirdChild.getClassName().equals("android.widget.TextView")
//                        && fourthChild!=null &&  fourthChild.getClassName()!=null && fourthChild.getClassName().equals("android.widget.TextView")
//                        ){
//                    if(thirdChild.getText()==null && secondChild.getText()!=null){
//                        groupContactSet.add(secondChild.getText().toString());
//                    }
//                }
//			}
		}

		for (int i = 0; i < child; i++) {
			getAllChildNode(node.getChild(i));
		}
	}

//	private void track(String action) {
//		track(action, "");
//	}
//	private void track(String action, String label) {
//		Intent intent = new Intent(LoggingService.APP_TRACKER);
//		intent.putExtra("action", action);
//		intent.putExtra("label", label);
//		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//	}

}

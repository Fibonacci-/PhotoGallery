package com.helwigdev.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tyler on 2/17/2015.
 * Copyright 2015 by Tyler Helwig
 */
public class NotificationReceiver extends BroadcastReceiver {
	private static final String TAG = "NotificationReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Received result: " + getResultCode());
		if(getResultCode() != Activity.RESULT_OK) return;//foreground activity canceled the notification

		int requestCode = intent.getIntExtra("REQUEST_CODE", 0);
		Notification notification = (Notification) intent.getParcelableExtra("NOTIFICATION");

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(requestCode, notification);
	}
}

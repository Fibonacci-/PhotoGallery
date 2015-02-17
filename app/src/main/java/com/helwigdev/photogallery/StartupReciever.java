package com.helwigdev.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tyler on 2/17/2015.
 * Copyright 2015 by Tyler Helwig
 */
public class StartupReciever extends BroadcastReceiver {
	private static final String TAG = "StartupReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Received broadcast boot intent: " + intent.getAction());
	}
}

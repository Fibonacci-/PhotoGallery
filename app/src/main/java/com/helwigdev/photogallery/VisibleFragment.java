package com.helwigdev.photogallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tyler on 2/17/2015.
 * Copyright 2015 by Tyler Helwig
 */
public abstract class VisibleFragment extends Fragment{
	public static final String TAG = "VisibleFragment";

	private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//Toast.makeText(getActivity(), "Got a broadcast: " + intent.getAction(), Toast.LENGTH_SHORT).show();
			//if we get this, we're visible, so cancel the notification
			Log.i(TAG, "Canceling notification");
			setResultCode(Activity.RESULT_CANCELED);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
		getActivity().registerReceiver(mOnShowNotification, intentFilter, PollService.PERM_PRIVATE, null);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(mOnShowNotification);
	}
}

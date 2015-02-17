package com.helwigdev.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by Tyler on 2/17/2015.
 * Copyright 2015 by Tyler Helwig
 */
public class PhotoPageActivity extends SingleFragmentActivity {
	@Override
	protected Fragment createFragment() {
		return new PhotoPageFragment();
	}
}

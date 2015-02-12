package com.helwigdev.photogallery;

import android.support.v4.app.Fragment;
import android.os.Bundle;


public class PhotoGalleryActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new PhotoGalleryFragment();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
	}


}

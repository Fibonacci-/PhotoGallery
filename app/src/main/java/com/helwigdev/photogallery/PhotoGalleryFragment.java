package com.helwigdev.photogallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tyler on 2/11/2015.
 * All code herein copyright Helwig Development 2/11/2015
 */
public class PhotoGalleryFragment extends Fragment {
	private static final String TAG = "PhotoGalleryFragment";

	GridView mGridView;
	ArrayList<GalleryItem> mItems;
	ThumbnailDownloader<ImageView> mThumbnailThread;

	int page = 1;
	private int mVisibleThreshold = 3;//cause why not
	boolean loading = false;

	private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<GalleryItem>>{

		@Override
		protected ArrayList<GalleryItem> doInBackground(Void... params) {
			return new FlickrFetchr().fetchItems(page++);

		}

		@Override
		protected void onPostExecute(ArrayList<GalleryItem> items){
			for(GalleryItem i : items){
				mItems.add(i);
			}
			setupAdapter();
		}
	}

	private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {

		public GalleryItemAdapter(ArrayList<GalleryItem> items) {
			super(getActivity(), 0, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			if(convertView == null){
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.gallery_item, parent, false);
			}

			ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_gallery_item);
			imageView.setImageResource(R.drawable.brian_up_close);

			GalleryItem item = getItem(position);
			mThumbnailThread.queueThumbnail(imageView, item.getUrl());

			//get the min and max for image preloading
			//preloading 10 images cause that's just why
			int startPos = position - 10;
			if(startPos < 0) startPos=0;
			int endPos = position + 10;
			if(endPos > mItems.size()){
				endPos = mItems.size();
			}

			return convertView;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		mItems = new ArrayList<>();
		new FetchItemsTask().execute();

		mThumbnailThread = new ThumbnailDownloader<>(new Handler());
		mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
			@Override
			public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
				if(isVisible()){
					imageView.setImageBitmap(thumbnail);
				}
			}
		});
		mThumbnailThread.start();
		mThumbnailThread.getLooper();
		Log.i(TAG, "Background thread started OK");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mThumbnailThread.quit();
		Log.i(TAG, "Background thread destroyed");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mThumbnailThread.clearQueue();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
	Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

		mGridView = (GridView) v.findViewById(R.id.gridView);
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if((totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold) && !loading){
					new FetchItemsTask().execute();
					loading = true;
				}

				int nextInvisible = firstVisibleItem + visibleItemCount;
				int lastToCache = nextInvisible + 50;

				if(lastToCache > totalItemCount){
					lastToCache = totalItemCount;
				}
				if(totalItemCount > 0) {
					for (int i = nextInvisible; i < lastToCache; i++) {
						mThumbnailThread.queuePrecache(null,
								mItems.get(i).getUrl());//precache next 10 images
						//no need to go backwards - they will have already been cached
					}
				}
			}
		});
		setupAdapter();

		return v;
	}

	private void setupAdapter() {
		if(getActivity() == null || mGridView == null){
			return;
		}

		if(mItems != null){
			if(mGridView.getAdapter() == null) {
				mGridView.setAdapter(new GalleryItemAdapter(mItems));
			} else {
				((GalleryItemAdapter)mGridView.getAdapter()).notifyDataSetChanged();
			}
		} else {
			mGridView.setAdapter(null);
		}
		loading = false;
	}


}

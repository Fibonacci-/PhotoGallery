package com.helwigdev.photogallery;

/**
 * Created by Tyler on 2/12/2015.
 * All code herein copyright Helwig Development 2/12/2015
 */
public class GalleryItem {
	private String mCaption;
	private String mId;
	private String mUrl;
	private String mOwner;

	public String getOwner() {
		return mOwner;
	}

	public void setOwner(String owner) {
		mOwner = owner;
	}

	public String getPhotoPageUrl(){
		return "https://www.flickr.com/photos/" + mOwner + "/" + mId;
	}

	public String toString(){
		return mCaption;
	}

	public String getCaption() {
		return mCaption;
	}

	public void setCaption(String caption) {
		mCaption = caption;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}
}

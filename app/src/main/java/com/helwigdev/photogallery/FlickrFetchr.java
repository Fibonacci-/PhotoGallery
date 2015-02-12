package com.helwigdev.photogallery;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tyler on 2/11/2015.
 * All code herein copyright Helwig Development 2/11/2015
 */
public class FlickrFetchr {
	public static final String TAG = "FlickrFetchr";

	private static final String ENDPOINT = "http://api.flickr.com/services/rest/";
	private static final String API_KEY = "b6f552fd9c97f5db4749980257720b24";
	private static final String API_SECRET = "8ebde285f21ecd1c";
	private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
	private static final String PARAM_EXTRAS = "extras";

	private static final String EXTRA_SMALL_URL = "url_s";

	byte[] getUrlBytes(String urlSpec) throws IOException{
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		try{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
				Log.e("Fetchr",connection.getResponseMessage() + " : " + connection.getResponseCode());
				return null;
			}

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while((bytesRead = in.read(buffer)) > 0){
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			return out.toByteArray();
		} finally {
			connection.disconnect();
		}
	}

	public String getUrl(String urlSpec) throws IOException{
		return new String(getUrlBytes(urlSpec));
	}

	public void fetchItems(){
		try{
			String url = Uri.parse(ENDPOINT).buildUpon()
					.appendQueryParameter("method", METHOD_GET_RECENT)
					.appendQueryParameter("api_key", API_KEY)
					.appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
					.build().toString();
			String  xmlString = getUrl(url);
			Log.i(TAG, "Recieved XML: " + xmlString);
		} catch (IOException e){
			Log.e(TAG, "Failed to fetch items: ", e);
		}
	}
}
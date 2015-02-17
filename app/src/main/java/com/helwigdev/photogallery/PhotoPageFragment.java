package com.helwigdev.photogallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Tyler on 2/17/2015.
 * Copyright 2015 by Tyler Helwig
 */
public class PhotoPageFragment extends VisibleFragment {
	private String mUrl;
	private WebView mWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

		final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.pb_web);
		progressBar.setMax(100);
		final TextView tvTitle = (TextView) v.findViewById(R.id.tv_web_title);


		mWebView = (WebView)v.findViewById(R.id.webView);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				return false;
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient(){
			public void onProgressChanged(WebView webView, int progress){
				if(progress == 100){
					progressBar.setVisibility(View.INVISIBLE);
				} else {
					progressBar.setVisibility(View.VISIBLE);
					progressBar.setProgress(progress);
				}
			}

			public void onReceivedTitle(WebView webView, String title){
				tvTitle.setText(title);
			}
		});

		mWebView.loadUrl(mUrl);

		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mUrl = getActivity().getIntent().getData().toString();
	}
}

//package io.cordova.zhqy.fragment;
//
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceError;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebResourceResponse;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.just.agentweb.AgentWeb;
//import com.just.agentweb.DefaultWebClient;
//
//import java.util.HashMap;
//
//import io.cordova.zhqy.R;
//import io.cordova.zhqy.web.AgentWebFragment;
//
///**
// * Created by cenxiaozhong on 2017/7/1.
// * source code  https://github.com/Justson/AgentWeb
// */
//
//public class BounceWebFragment extends AgentWebFragment {
//	private TextView mTitleTextView;
//	public static BounceWebFragment getInstance(Bundle bundle) {
//
//		BounceWebFragment mBounceWebFragment = new BounceWebFragment();
//		if (mBounceWebFragment != null)
//			mBounceWebFragment.setArguments(bundle);
//
//		return mBounceWebFragment;
//	}
//
//
//	@Override
//	public String getUrl() {
//		return super.getUrl();
//	}
//
//	@Override
//	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//		mTitleTextView =(TextView) view.findViewById(R.id.tv_title);
//		mAgentWeb = AgentWeb.with(this)//
//				.setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
//				.useDefaultIndicator(-1, 2)//
//				.setAgentWebWebSettings(getSettings())//
//				.setWebViewClient(mWebViewClient)
//				.setWebChromeClient(mWebChromeClient)
//				.setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
////                .setDownloadListener(mDownloadListener)  4.0.0 删除该API
//				.interceptUnkownUrl()
//				.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
//				.createAgentWeb()//
//				.ready()//
//				.go(getUrl());
//
//
//
//		addBGChild((FrameLayout) mAgentWeb.getWebCreator().getWebParentLayout()); // 得到 AgentWeb 最底层的控件
//
//
//	}
//
//	protected WebChromeClient mWebChromeClient = new WebChromeClient() {
//		@Override
//		public void onProgressChanged(WebView view, int newProgress) {
//			//  super.onProgressChanged(view, newProgress);
//			Log.i("TAG", "onProgressChanged:" + newProgress + "  view:" + view);
//		}
//
//		@Override
//		public void onReceivedTitle(WebView view, String title) {
//			super.onReceivedTitle(view, title);
//			if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
//				if (title.length() > 10) {
//					title = title.substring(0, 10).concat("...");
//				}
//			}
//			mTitleTextView.setText(title);
//		}
//	};
//
//	protected WebViewClient mWebViewClient = new WebViewClient() {
//
//		private HashMap<String, Long> timer = new HashMap<>();
//
//		@Override
//		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//			super.onReceivedError(view, request, error);
//		}
//
//
//
//		@Nullable
//		@Override
//		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//			return super.shouldInterceptRequest(view, request);
//		}
//
//		//
//		@Override
//		public boolean shouldOverrideUrlLoading(final WebView view, String url) {
//
//			Log.i("TAG", "view:" + new Gson().toJson(view.getHitTestResult()));
//			Log.i("TAG", "mWebViewClient shouldOverrideUrlLoading:" + url);
//			//intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
//			//例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
//			//优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
//			if (url.startsWith("intent://") && url.contains("com.youku.phone")) {
//				return true;
//			}
//
//
//			return false;
//		}
//
//		@Override
//		public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//			Log.i("TAG", "mUrl:" + url + " onPageStarted  target:" + getUrl());
//			timer.put(url, System.currentTimeMillis());
//
//		}
//
//		@Override
//		public void onPageFinished(WebView view, String url) {
//			super.onPageFinished(view, url);
//
//			if (timer.get(url) != null) {
//				long overTime = System.currentTimeMillis();
//				Long startTime = timer.get(url);
//				Log.i("TAG", "  page mUrl:" + url + "  used time:" + (overTime - startTime));
//			}
//
//		}
//
//		@Override
//		public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//			super.onReceivedHttpError(view, request, errorResponse);
//
////			Log.i(TAG, "onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse));
//		}
//
//		@Override
//		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//			super.onReceivedError(view, errorCode, description, failingUrl);
//
////			Log.i(TAG, "onReceivedError:" + errorCode + "  description:" + description + "  errorResponse:" + failingUrl);
//		}
//	};
//
//	protected void addBGChild(FrameLayout frameLayout) {
//		TextView mTextView = new TextView(frameLayout.getContext());
//		mTextView.setText("技术由 精华教育 提供");
//		mTextView.setTextSize(16);
//		mTextView.setTextColor(Color.parseColor("#727779"));
//		frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
//		FrameLayout.LayoutParams mFlp = new FrameLayout.LayoutParams(-2, -2);
//		mFlp.gravity = Gravity.CENTER_HORIZONTAL;
//		final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
//		mFlp.topMargin = (int) (15 * scale + 0.5f);
//		frameLayout.addView(mTextView, 0, mFlp);
//		TextView mTextView2 = new TextView(frameLayout.getContext());
//		mTextView2.setText("技术由 精华教育 提供");
//		mTextView2.setTextSize(16);
//		mTextView2.setTextColor(Color.parseColor("#727779"));
//		frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
//		FrameLayout.LayoutParams mFlp2 = new FrameLayout.LayoutParams(-2, -2);
//		mFlp2.gravity = (Gravity.BOTTOM/Gravity.CENTER_HORIZONTAL);
////		mFlp2.gravity = Gravity.;
//		final float scale2 = frameLayout.getContext().getResources().getDisplayMetrics().density;
//		mFlp2.topMargin = (int) (15 * scale2 + 0.5f);
//		mFlp2.leftMargin =  (55);
//		frameLayout.addView(mTextView2, 0, mFlp2);
//	}
//
//	@Override
//	public void onResume() {
//		mAgentWeb.getWebLifeCycle().onResume();//恢复
//		super.onResume();
//	}
//
//	@Override
//	public void onPause() {
//
//		mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
//		super.onPause();
//	}
//
//	@Override
//	public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
//		return mAgentWeb.handleKeyEvent(keyCode, event);
//	}
//
//	@Override
//	public void onDestroyView() {
//		mAgentWeb.getWebLifeCycle().onDestroy();
//		super.onDestroyView();
//	}
//}

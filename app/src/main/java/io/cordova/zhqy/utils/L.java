package io.cordova.zhqy.utils;

import android.util.Log;

/**
 * Log统一管理类
 * 
 * @author lilingfei
 * 
 */
public class L {

	private L() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}



	public static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "N";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	public static void w(String msg) {
		if (isDebug) {
			Log.w(TAG, msg);
		}
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
	public static void w(String tag, String msg) {
		if (isDebug)
			Log.w(tag, msg);
	}

	/**
	 * 分段打印出较长log文本
	 * @param logContent  打印文本
	 * @param showLength  规定每段显示的长度（AndroidStudio控制台打印log的最大信息量大小为4k）
	 * @param tag         打印log的标记
	 */
//	public static void showLargeLog(String logContent, int showLength, String tag){
//		if(logContent.length() > showLength){
//			String show = logContent.substring(0, showLength);
//			e(tag, show);
//            /*剩余的字符串如果大于规定显示的长度，截取剩余字符串进行递归，否则打印结果*/
//			if((logContent.length() - showLength) > showLength){
//				String partLog = logContent.substring(showLength,logContent.length());
//				showLargeLog(partLog, showLength, tag);
//			}else{
//				String printLog = logContent.substring(showLength, logContent.length());
//				e(tag, printLog);
//			}
//
//		}else{
//			e(tag, logContent);
//		}//}
}
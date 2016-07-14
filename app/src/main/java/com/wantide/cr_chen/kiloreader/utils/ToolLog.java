package com.wantide.cr_chen.kiloreader.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolLog {
	
	private static final String TAG = "Wantide";
	
	/**
	 * 上线后关闭log
	 */
	private static final Boolean DEBUG = true;
	
	public static void d(String msg) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.d(TAG, tag + " : " + msg);
		}
	}

	public static void d(String msg, Throwable error) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.d(TAG, tag + " : " + msg, error);
		}
	}

	public static void i(String msg) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.i(TAG, tag + " : " + msg);
		}
	}

	public static void i(String msg, Throwable error) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.i(TAG, tag + " : " + msg, error);
		}
	}

	public static void w(String msg) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.w(TAG, tag + " : " + msg);
		}
	}

	public static void w(String msg, Throwable error) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.w(TAG, tag + " : " + msg, error);
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.e(TAG, tag + " : " + msg);
		}
	}

	public static void e(String msg, Throwable error) {
		if (DEBUG) {
			String tag = Thread.currentThread().getName() + ":";
			Log.e(TAG, tag + " : " + msg, error);
		}
	}
}

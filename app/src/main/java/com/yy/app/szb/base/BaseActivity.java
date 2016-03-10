package com.yy.app.szb.base;

import szb.app.yy.com.sztreasure.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.yy.app.szb.dialogs.WaitDialog;
import com.yy.app.szb.utils.DensityUtils;
import com.yy.app.szb.utils.Preference;

/**
 * 基础的视图类
 * @author fuenmao
 *
 */
public abstract class BaseActivity extends Activity{

	/** 子类必须实现的方法 */
	protected abstract void bindViews();// 初始化视图

	protected abstract void initControl();// 初始化视图组件

	protected abstract void initData();// 初始化数据

	protected abstract void initActionBar();// 初始化标题栏

	protected String TAG;

	protected Preference preference;

	protected WaitDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = this.getClass().getSimpleName();
		System.gc();
		/** 强制竖屏 */
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		/** 隐藏标题 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** 设置系统的输入框的模式 */
		getWindow()
				.setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
								| WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
		super.onCreate(savedInstanceState);
		dialog = new WaitDialog(this);
		bindViews();
		initControl();
		initData();
		initActionBar();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 发送提示消息  顶部
	 *
	 * @param message
	 */
	protected void showToastTop(String message) {
		if (getApplicationContext() == null) {
			return;
		}
		Toast toast = Toast.makeText(getApplicationContext(), message,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0,
				DensityUtils.dp2px(getApplicationContext(), 50));
		toast.show();
	}

	/**
	 * 发送提示消息  底部
	 *
	 * @param message
	 */
	public void showToastBottom(String message) {
		Toast.makeText(getApplicationContext(), message + "~",Toast.LENGTH_SHORT).show();
	}

	/**
	 * 打印日志
	 *
	 * @param log
	 */
	protected void log(String tag,String log) {
		Log.i(tag, log);
	}

	/**
	 * 当activity触屏事件时，隐藏输入法
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imeManager.hideSoftInputFromWindow(getWindow().getDecorView()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		return super.onTouchEvent(event);
	}

	/**
	 * 隐藏输入法
	 */
	protected void hideSoftInput() {
		InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imeManager.isActive();
		imeManager.hideSoftInputFromWindow(getWindow().getDecorView()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 显示输入法
	 */
	protected void showSoftInput() {
		InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imeManager.isActive();
		imeManager.hideSoftInputFromWindow(getWindow().getDecorView()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		imeManager.showSoftInputFromInputMethod(getWindow().getDecorView()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	/**
	 * 监听手机上的后退键 (non-Javadoc)
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(0, R.anim.back_right_out);
	}

	/**
	 * 返回上一级
	 */
	protected void backView() {
		finish();
		overridePendingTransition(0, R.anim.back_right_out);
	}

	/**
	 * 显示对话框
	 */
	protected void showDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		if (dialog == null) {
			dialog = new WaitDialog(this);
			dialog.show();
		}
	}

	/**
	 * 隐藏对话框
	 */
	protected void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}

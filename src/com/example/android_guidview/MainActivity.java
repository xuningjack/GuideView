package com.example.android_guidview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.DoveboxApp;
import com.example.R;
import com.example.interfaces.OnConfirmListener;
import com.example.support.ConfigWrapper;
import com.example.support.Start;
import com.example.widget.GuideView;


public class MainActivity extends Activity {

	private GuideView mGuideView;
	private final String TAG = "IF_LOGEDIN";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGuideView = (GuideView)findViewById(R.id.guide_view);
		initView();
	}

	/**
	 * 显示GuideView
	 */
	public void initView() {
		if(!ConfigWrapper.get(TAG, false)){  //第一次进入
			mGuideView.setVisibility(View.VISIBLE);
			mGuideView.postDelayed(new Runnable() {
				public void run() {
					if (GuideView.isShowGuide(DoveboxApp.getInstance())) {
						showIndexGuide();
					} else {
						startMain();
					}
				}
			}, 0);
		}else{     //再次进入直接跳转到登陆界面
			startMain();
		}
	}

	/**
	 * 显示指引页面
	 */
	private void showIndexGuide() {
		GuideViewCallBack callBack = new GuideViewCallBack();
		mGuideView.initView(MainActivity.this, callBack);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.delete_fadein);
		mGuideView.startAnimation(anim);
		mGuideView.setVisibility(View.VISIBLE);
	}

	/**
	 * 指引页面关闭回调类
	 * @author Lifeix
	 */
	private class GuideViewCallBack implements OnConfirmListener {
		public void confirmListener() {
			startMain();
		}
	};

	/**
	 * 跳转到主界面
	 * @param extras
	 */
	private void startMain() {

		ConfigWrapper.put(TAG, true);
		Start.start(MainActivity.this, LoginActivity.class);
		finish();
	}
}
package com.example.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.DoveboxApp;
import com.example.R;
import com.example.interfaces.OnConfirmListener;
import com.example.support.ConfigWrapper;
import com.example.util.PackageUtils;


/**
 * 欢迎界面中的引导页侧滑控件布局
 * @author Jack  
 * @version 创建时间：2014年4月28日  上午10:38:26
 */
public class GuideView extends RelativeLayout {

    private Activity mContext;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    private List<View> mViews;
    private TextView mStartBtn;
    private int mPageCount;
    private OnConfirmListener mCallBack;
    public GuidePagerAdapter mGuidePagerAdatper;
    public boolean isChanging;

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideView(Context context) {
        this(context, null);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * 初始化viewPager相关信息，分引导页情况和没体头条情况
     * @param mContext
     * @param mCallBack
     */
    public void initView(Activity mContext, OnConfirmListener mCallBack) {
        this.mContext = mContext;
        this.mViews = getGuideViews();
        this.mCallBack = mCallBack;
        if (mViews == null || mViews.size() == 0) {
            this.mPageCount = 0;
        } else {
            this.mPageCount = mViews.size();
        }
        init();
    }

    private void init() {
        mInflater = mContext.getLayoutInflater();
        iniView();
        iniViewPager();
    }

    private void iniView() {
        View view = mInflater.inflate(R.layout.view_index_guide, null);
        addView(view);
        mViewPager = (ViewPager) view.findViewById(R.id.layout_index_guide_pager);
        mStartBtn = (TextView) view.findViewById(R.id.layout_index_guide_startBtn);
        if (mPageCount - 1 == mViewPager.getCurrentItem()) {
            mStartBtn.setVisibility(View.VISIBLE);
        } else {
            mStartBtn.setVisibility(View.GONE);
        }
        mStartBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.confirmListener();
                    Log.d("Guide", "------------点击开始--------------");
                }
            }
        });
    }

    private void iniViewPager() {
        // 设置适配器
        mGuidePagerAdatper = new GuidePagerAdapter();
        mViewPager.setAdapter(mGuidePagerAdatper);
        // 设置监听事件
        mViewPager.setOnPageChangeListener(new GuidePagerChangeListener());
    }

    /**
     * 设置引导页viewpager的数据源
     * @return
     */
    private List<View> getGuideViews() {
        int[] guideImgs = { R.drawable.img_bootpage1, R.drawable.img_bootpage2, R.drawable.img_bootpage3, R.drawable.img_bootpage4 };
        List<View> guideViews = new ArrayList<View>();

        for (int i = 0; i < guideImgs.length; i++) {
            ImageView imgView = new ImageView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            imgView.setLayoutParams(params);
            imgView.setScaleType(ScaleType.FIT_XY);
            imgView.setImageResource(guideImgs[i]);
            guideViews.add(imgView);
        }
        return guideViews;
    }

    public static boolean isShowGuide(DoveboxApp app) {
        String versionCode = ConfigWrapper.get("com.l99.firsttime.guide_flag_version", null);
        if (versionCode == null || !versionCode.equals(PackageUtils.getVersion(app))) {
            ConfigWrapper.put("com.l99.firsttime.guide_flag_version", PackageUtils.getVersion(app));
            ConfigWrapper.commit();
            return true;
        } else {
            return false;
        }
    }

    /**
     * viewpager适配器
     * @author Jack  
     * @version 创建时间：2014年4月28日  上午10:46:32
     */
    public class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews == null || mViews.size() == 0 || arg1 >= mViews.size()) {
                return;
            }
            ((ViewPager) arg0).removeView(mViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            if (mViews == null || mViews.size() == 0) {
                return 0;
            } else {
                return mViews.size();
            }
        }

        @Override
        public Object instantiateItem(View arg0, final int arg1) {
            if (mViews == null || mViews.size() == 0) {
                return null;
            } else {
                ((ViewPager) arg0).addView(mViews.get(arg1));
                return mViews.get(arg1);
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    /**
     * viewpager监听器
     * @author Jack  
     * @version 创建时间：2014年4月28日  上午10:47:00
     */
    private class GuidePagerChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 1) { // arg0 == 1标识正在滑动中
                isChanging = true;
            } else {
                isChanging = false;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // 翻页结束后调用
            if (mViews == null || mViews.size() == 0){
            	return;
            }
            DoveboxApp.getInstance().setPosition(position);
            if (position == mPageCount - 1) {
                mStartBtn.setVisibility(View.VISIBLE);
            } else {
                mStartBtn.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 封装的设置viewpager当前项的方法；其中会根据不同情况动态设置viewpager变换间隔的时间
     * @param currentNum
     */
    public void setCurrentItem(int currentNum) {
        if (isChanging){
        	return;
        }
        if (currentNum >= mPageCount) {
            currentNum = 0;
        }
        DoveboxApp.getInstance().setPosition(currentNum);
        Interpolator sInterpolator = new AccelerateInterpolator();   //通过反射设置自动翻页时的时长
        try {
            final Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            final FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), sInterpolator);
            if (currentNum == 0) {
                scroller.setDuration(0);
                mScroller.set(mViewPager, scroller);
                mViewPager.setCurrentItem(currentNum);
            } else {  //通过反射设置自动翻页时的时长
                scroller.setDuration(1000);
                mScroller.set(mViewPager, scroller);
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroller.setDuration(200);
                        try {
                            mScroller.set(mViewPager, scroller);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
                mViewPager.setCurrentItem(currentNum, true);
            }
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);		// 这句话的作用告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }

    class FixedSpeedScroller extends Scroller {
        private int mDuration = 1000;

        public void setDuration(int duration) {
            this.mDuration = duration;
        }

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }
}
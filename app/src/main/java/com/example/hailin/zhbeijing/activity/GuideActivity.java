package com.example.hailin.zhbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hailin.zhbeijing.R;
import com.example.hailin.zhbeijing.utils.PreferenceUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GuideActivity extends Activity {

    private static final int[] imageIds = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};

    private ArrayList<ImageView> mImageViewList;

    private ViewPager mGuideViewPager;
    private LinearLayout mPointGroup;
    private View mPointRed;
    private int mPointLeftWidth;
    private Button mButtonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        mGuideViewPager = (ViewPager) findViewById(R.id.viewPager_guide);
        mPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        mPointRed = (View) findViewById(R.id.view_point_red);
        mButtonStart = (Button) findViewById(R.id.button_guide_start);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.setBoolean(GuideActivity.this, "isUserGuideShowed", true);

                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        //为底部小红点设置参数
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(30, 30);
        mPointRed.setLayoutParams(rlParams);

        //绘制底部的小灰点
        for (int i = 0; i < imageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_guide_point_grey);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            if(i>0){
                params.leftMargin = 30;
            }
            point.setLayoutParams(params);

            mPointGroup.addView(point);
        }

        //获取试图树，对layout结束事件进行监听
        mPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //获取两点之间的距离
                mPointLeftWidth = mPointGroup.getChildAt(1).getLeft() - mPointGroup.getChildAt(0).getLeft();
            }
        });

        //为ViewPager初始化背景图片
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            mImageViewList.add(imageView);
        }
        //为ViewPager设置Adapter
        mGuideViewPager.setAdapter(new ViewPagerAdapter());

        mGuideViewPager.setOnPageChangeListener(new ViewPageChangeListener());
    }

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class ViewPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int len = (int) (mPointLeftWidth * positionOffset + mPointLeftWidth * position);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPointRed.getLayoutParams();
            params.leftMargin = len;
            mPointRed.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if(position==imageIds.length - 1){
                mButtonStart.setVisibility(View.VISIBLE);
            }else {
                mButtonStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

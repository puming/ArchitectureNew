package com.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.common.R;
import com.common.imageloader.glide.GlideApp;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pm
 * @date 18-1-13
 */

public class BannerView extends FrameLayout implements View.OnClickListener, View
        .OnLongClickListener
        , ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    /**
     * 是否需要重新测量
     */
    private boolean mRemeasure = true;
    /**
     * 指示器距离底部的距离
     */
    private static final int DOT_MARGIN_BOTTOM = 30;
    /**
     * 指示器水平间距
     */
    private static final int DOT_MARGIN_HORIZONTAL = 18;

    /**
     * 数据源
     */
    private List<ImageView> mDatas = new ArrayList<ImageView>(6);
    private List<String> mUrls;
    /**
     * 存放指示器的数组,注意数组的长度等于数据源的长度
     */
    private ImageView[] mDots;
    /**
     * 指示器的父级容器
     */
    private LinearLayout mDotLayout;
    private ViewPager mViewPager;


    private Context mContext;
    private BannerPagerAdapter mAdapter;
    private Handler mHandler;

    private View.OnLongClickListener mOnLongClickListener;
    private OnChildClickListener mOnClickListener;
    private OnBannerFocusChangeListener mOnBannerFocusChangeListener;
    private View mCurrentView;
    private boolean isItemFocus;

    private boolean mAttachedToWindow;
    /**
     * 数据已经加载
     */
    private boolean mAdded;
    private int mCurrentMaxPosition = -1;
    /**
     * 如果没有数据显示一张默认图
     */
    private ImageView mDefaultImage;
    private int sizeWidth = 1048;
    private int sizeHeight = 510;
    private RoundedCorners roundedCorners;
    private boolean mbindEnable = true;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyleAttr, 0);
//        mRemeasure = typedArray.getBoolean(R.styleable.BannerView_remeasure, true);
//        typedArray.recycle();
        //pager
        LayoutParams pagerParams = new LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager = new ViewPager(context);
        mViewPager.setLayoutParams(pagerParams);
        addView(mViewPager, pagerParams);
        //dot
        LayoutParams dotParams = new LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.bottomMargin = DOT_MARGIN_BOTTOM;
        dotParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mDotLayout = new LinearLayout(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDotLayout.setTranslationZ(20f);
            mDotLayout.setElevation(20f);
        }
        mDotLayout.setLayoutParams(dotParams);
        mDotLayout.setGravity(Gravity.CENTER);
        addView(mDotLayout, dotParams);
        //default image
        LayoutParams defaultImageParams = new LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDefaultImage = new ImageView(context);
        mDefaultImage.setLayoutParams(defaultImageParams);
        mDefaultImage.setVisibility(GONE);
        mDefaultImage.setImageResource(R.drawable.ic_default);
        mDefaultImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(mDefaultImage, defaultImageParams);

//        setPadding(left, top, right, bottom);
//        setOffset(1.03f,1.03f);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        mAdapter = new BannerPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mHandler = new BannerHandler(this);
        initScroller();
        registerListener();
//        roundedCorners = new RoundedCorners(getResources().getInteger(R.integer.radius));
        roundedCorners = new RoundedCorners(6);
    }

    /**
     * 通过反射替换掉ViewPager的mScroller属性。
     * 这样做是为了改变ViewPager的滚动速度。
     */
    private void initScroller() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            BannerScroller bannerScroller = new BannerScroller(mContext,
                    new AccelerateInterpolator());
            scroller.set(mViewPager, bannerScroller);
        } catch (Exception e) {
        }
    }

    private void registerListener() {
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setPageTransformer(false, this);
        this.setOnClickListener(this);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        int what = event.getAction();
        switch (what) {
            case MotionEvent.ACTION_HOVER_ENTER:
                setHovered(true);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                setHovered(false);
                break;
            default:
                break;
        }
        return true;

    }

    @Override
    public void onHoverChanged(boolean hovered) {
        // TODO Auto-generated method stub
        super.onHoverChanged(hovered);
        if (hovered) {
            requestFocusFromTouch();
        }

        if (mOnBannerFocusChangeListener != null) {
            mOnBannerFocusChangeListener.onBannerFocusChange(this, hovered);
            return;
        }
        if (mCurrentView != this) {
            mCurrentView = this;
        }
        if (isItemFocus != hovered) {
            isItemFocus = hovered;
        }
//        changeFocus(this, hovered);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        // TODO Auto-generated method stub
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        setHovered(gainFocus);
    }

    private void changeFocus(View v, boolean focus) {
//        if (focus) {
//            AnimationUtil.getInstance().scaleAnimation(this, 1.03f, 1.03f);
//            setBackgroundResource(R.drawable.hover_shadow);
//        } else {
//            AnimationUtil.getInstance().scaleAnimation(this, 1.0f, 1.0f);
//            setBackgroundResource(R.drawable.normal_shaow);
//        }
    }

    /**
     * 根据数据源长度创建轮播指示器
     *
     * @param layout   小圆点父容器
     * @param dotCount 小圆点的个数
     */
    private void createDots(LinearLayout layout, int dotCount) {
        mDots = new ImageView[dotCount];
        // step1:根据数据源的长度，创建小圆点的imageview
        for (int i = 0; i < dotCount; i++) {
            ImageView icon = new ImageView(mContext);


            // 设置一些imageview 的属性信息
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
            layoutParams.setMargins(DOT_MARGIN_HORIZONTAL / 2, 0, DOT_MARGIN_HORIZONTAL / 2, 0);
            icon.setLayoutParams(layoutParams);
            // step2:设置背景色：选择器：可以变灰色和白色
            icon.setBackgroundResource(R.drawable.bg_point_selector);
            // step3:将小圆点添加到linearlayout上。同时存入数组中
            layout.addView(icon);
            mDots[i] = icon;
            // step4:小圆点被点击，viwpager中切换相应的内容
            final int index = i;
            icon.setTag(i);

            icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                    // 第几个小圆点被点，就设置viewpager中显示第几个imageview

                    if (mDatas != null && mDatas.size() > 0) {
                        int currentItem = mViewPager.getCurrentItem();
                        int currentIndex = currentItem % mDatas.size();
                        Integer tag = (Integer) v.getTag();
                        //偏移量
                        int offset = 0;
                        if (currentIndex > tag) {
                            //向左滑动
                            offset = currentIndex - tag;
                            int tagItem = currentItem - offset;
                            mViewPager.setCurrentItem(tagItem, true);
                        } else if (currentIndex < tag) {
                            //向右滑动
                            offset = tag - currentIndex;
                            int tagItem = currentItem + offset;
                            if (tagItem > mCurrentMaxPosition) {
                                mViewPager.setCurrentItem(tagItem, true);
                            } else {
                                mViewPager.setCurrentItem(mCurrentMaxPosition, true);
                            }
                        }
                    }

                    mHandler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_IMAGE,
                            BannerHandler.MSG_DELAY);
                }
            });
        }
        // step5:默认第一个小圆点灰色的
        if (dotCount >= 0) {
            mDots[0].setEnabled(false);
        }
    }


    public void addData(List<String> list) {
        if (list != null && list.size() > 1) {
            if (!mAdded) {
                mUrls = list;
                List<ImageView> content = createBannerContent(list);
                mDatas.addAll(content);
                createDots(mDotLayout, mDatas.size());
                mAdapter.notifyDataSetChanged();
                //startTimer
                mHandler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_IMAGE, BannerHandler
                        .MSG_DELAY);
                mAdded = true;
            }
        } else if (list != null && list.size() == 1) {
            mDefaultImage.setVisibility(VISIBLE);
            GlideApp.with(mContext).load(list.get(0)).error(R.drawable.ic_default)
                    .transform(roundedCorners).into(mDefaultImage);
        } else {
            mDefaultImage.setVisibility(VISIBLE);
        }

    }

    private List<ImageView> createBannerContent(List<String> list) {
        int dataSize = list.size();
        List<ImageView> views = new ArrayList<>(dataSize);
        for (int i = 0; i < dataSize; i++) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setOnClickListener(this);
//            imageView.setOnLongClickListener(this);
            views.add(imageView);
        }
        return views;
    }

    public void setOnChildClickListener(@Nullable OnChildClickListener l) {
        mOnClickListener = l;
    }

    public void setBannerFocusChangeListener(@Nullable OnBannerFocusChangeListener l) {
        mOnBannerFocusChangeListener = l;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttachedToWindow = true;
        bindImage();
        sendMessage();
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttachedToWindow = false;
        mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
        bindImage();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        switch (visibility) {
            case VISIBLE:
                sendMessage();
                break;
            case GONE:
                mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                break;
            case INVISIBLE:
                mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        switch (visibility) {
            case VISIBLE:
                sendMessage();
                break;
            case GONE:
                mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                break;
            case INVISIBLE:
                mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            sendMessage();
        } else {
            mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
        }
    }

    private void sendMessage() {
        if (!mDatas.isEmpty()) {
            mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
            mHandler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_IMAGE, BannerHandler.MSG_DELAY);
        }
    }

    @Override
    public void onClick(View v) {
        int size = mDatas.size();
        if (size != 0) {
            int currentItem = mViewPager.getCurrentItem() % size;
            if (mOnClickListener != null) {
                mOnClickListener.onChildClick(v, currentItem);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        this.onClick(v);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        AILog.d("position=" + position + "index=" + position % mDatas.size());

        if (mCurrentMaxPosition < position) {
            mCurrentMaxPosition = position;
        }
        int length = mDatas.size();
        for (int i = 0; i < length; i++) {
            mDots[i].setEnabled(true);
        }
        //position的位置，灰色小点
        if (length != 0) {
            mDots[position % length].setEnabled(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
//                GlideApp.with(getContext()).resumeRequests();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
//                GlideApp.with(getContext()).pauseRequests();
                mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
//                GlideApp.with(getContext()).pauseRequests();
                sendMessage();
                break;
            default:
                break;
        }
    }

    @Override
    public void transformPage(View page, float position) {
//        transformPageAnim(page, position);
    }

    private void transformPageAnim(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        final float minScale = 0.85f;
        final float minAlpha = 0.5f;


        if (position < -1) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) {
            // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            float scaleFactor = Math.max(minScale, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(minAlpha + (scaleFactor - minScale)
                    / (1 - minScale) * (1 - minAlpha));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

    /**
     * 回收图片
     */
    private void recycleImage() {
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = mDatas.get(i);
            Drawable drawable = imageView.getDrawable();
            imageView.setImageResource(R.drawable.ic_default);
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
        }
    }

    /**
     * 绑定图片
     */
    private void bindImage() {
        for (int i = 0; i < mDatas.size(); i++) {
            glideLoad(mUrls.get(i), mDatas.get(i));
        }
    }

    private void glideLoad(Object object, ImageView imageView) {
        if (mContext == null) {
            return;
        }
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                return;
            }
            if (activity.isFinishing()) {
                return;
            }
        }
        GlideApp.with(mContext)
                .load(mbindEnable && mAttachedToWindow ? object : null)
                .override(sizeWidth, sizeHeight)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(roundedCorners)
                .into(imageView);
    }

    /**
     * 改变轮播图片是回收还是绑定的状态.
     *
     * @param bindEnable true重新绑定,false回收图片
     */
    public void changeImageState(boolean bindEnable) {
        mbindEnable = bindEnable;
        bindImage();
        if (bindEnable) {
            sendMessage();
        } else {
            mHandler.removeMessages(BannerHandler.MSG_UPDATE_IMAGE);
        }
    }

    private class BannerPagerAdapter extends PagerAdapter {
        public BannerPagerAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return mDatas.size() != 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int size = mDatas.size();
            if (size != 0) {
                position = position % mDatas.size();
            }
            if (position < 0) {
                position = size + position;
            }
            ImageView iv = mDatas.get(position);
            ViewParent viewParent = iv.getParent();
            if (viewParent != null) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(iv);
            }
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mDatas.size() > 2) {
                container.removeView(mDatas.get(position % mDatas.size()));
            }
        }

    }

    private static class BannerHandler extends Handler {
        static final int MSG_UPDATE_IMAGE = 0;
        static final long MSG_DELAY = 1000 * 3;
        private final WeakReference<BannerView> mWeakReference;

        private BannerHandler(BannerView bannerView) {
            mWeakReference = new WeakReference<BannerView>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            BannerView bannerView = mWeakReference.get();
            if (bannerView == null) {
                return;
            }
            if (msg.what == MSG_UPDATE_IMAGE) {
                bannerView.mHandler.removeMessages(MSG_UPDATE_IMAGE);
                //切换到下一页
                int currentItem = bannerView.mViewPager.getCurrentItem();
                int count = bannerView.mAdapter.getCount();
                int nextItem = currentItem + 1;
                if (nextItem < count) {
                    bannerView.mViewPager.setCurrentItem(nextItem, true);
                } else {
                    bannerView.mViewPager.setCurrentItem(0, false);
                }
                bannerView.mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }
        }
    }

    private class BannerScroller extends Scroller {

        private int mScrollDuration = 720;

        public BannerScroller(Context context) {
            super(context);
        }

        public BannerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public BannerScroller(Context context, Interpolator interpolator,
                              boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        public int getScrollDuration() {
            return mScrollDuration;
        }

        public void setScrollDuration(int scrollDuration) {
            this.mScrollDuration = scrollDuration;
        }
    }

    public interface OnChildClickListener {
        /**
         * 当点击轮播子view的时候回调
         *
         * @param v           被点击的子view
         * @param currentItem 被点击的子view下标
         */
        void onChildClick(View v, int currentItem);
    }

    public interface OnBannerFocusChangeListener {
        /**
         * 当前view焦点改变时回调
         *
         * @param v
         * @param hasFocus
         */
        void onBannerFocusChange(View v, boolean hasFocus);
    }
}
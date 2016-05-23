package com.solon.animationdrink;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;

/**
 * Created by xumin on 16/5/22.
 */
public class MyViewGroup1 extends ViewGroup {

    private static final int TOP_LEFT = 0 ;
    private static final int TOP_RIGHT = 1 ;
    private static final int BOTTOM_RIGHT = 2 ;
    private static final int BOTTOM_LEFT = 3 ;

    //画四个大圆
    private int count = 4;
    //当前位于中心的圆的编号
    private int currIndex = 0;
    //没有大圆的方向
    private int zeroIndex = TOP_LEFT ;

    private boolean isInit = false ;

    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0 ; i < count ; i++) {
            BigView view = new BigView(getContext());
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            view.setColor(Color.rgb(r, g, b));
            view.setLocation(i);
            addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = getChildCount();
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;

        if (!isInit) {
            isInit = true ;
            for (int i = 0 ; i < size ; i++){
                View child = getChildAt(i);
                if (child instanceof  BigView){
                    BigView view = (BigView) child;
                    view.setWidth(getMeasuredHeight());
                    view.setHeight(getMeasuredHeight());
                    if (view.getLocation() == 0) {
                        view.setPostionX((int) (width - view.getWidth() / 2));
                        view.setPostionY((int) (height - view.getHeight() / 2));
                    } else if (view.getLocation() == 1) {
                        view.setPostionX((int) (getWidth() + view.getWidth() / 4));
                        view.setPostionY((int) (view.getHeight() * 3/ 4 - view.getHeight()));
                    }else if (view.getLocation() == 2) {
                        view.setPostionX((int) (view.getWidth() * 3/ 4 - view.getWidth()));
                        view.setPostionY((int) (getHeight() + view.getHeight() / 4));
                    }else {
                        view.setPostionX((int) (getWidth() - view.getWidth() / 4 ));
                        view.setPostionY((int) (getHeight() - view.getHeight() / 4 ));
                    }
                }
            }
        }

        for (int i = 0 ; i < size ; i++){
            View view = getChildAt(i);
            if (view instanceof  BaseView){
                BaseView v = (BaseView) view;
//                int left = l + v.getPostionX();
//                int top = t + v.getPostionY();
//                int right = left + v.getMyWidth();
//                int bottom = top +  v.getMyHeight();

                int left = l + (v.getPostionX() - (v.getWidth() / 2));
                int top = t + (v.getPostionY() - (v.getHeight() / 2));
                int right = left + v.getMyWidth();
                int bottom = top +  v.getMyHeight();

                v.layout(left,top ,right ,bottom);
            }

        }
    }

    private double getRaduis (BigView view1 , BigView view2) {

        double x = Math.sqrt((double) ((view1.getPostionX() - view2.getPostionX())^2 + (view1.getPostionY() + view2.getPostionY())^2)) ;
        return x ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                BaseView baseView = (BaseView) getChildAt(1) ;
                ObjectAnimator animatorX = ObjectAnimator.ofInt(baseView,"postionX" , baseView.getPostionX(), getWidth() / 2 ) ;
                ObjectAnimator animatorY = ObjectAnimator.ofInt(baseView,"postionY" , baseView.getPostionY(), getHeight() / 2 ) ;

                AnimatorSet set = new AnimatorSet() ;
                set.play(animatorX).with(animatorY) ;
                set.setInterpolator(new AccelerateInterpolator());
                set.setDuration(500) ;
                set.start(); ;

//                animatorX.setDuration(500) ;
//                animatorY.setDuration(500);
//                animatorX.start();
//                animatorY.start();
                break;
            case MotionEvent.ACTION_MOVE:
                break;

        }



//        if (zeroIndex == TOP_LEFT) {
//            int size = getChildCount();
//            if (size > 2) {
//                View view0 = getChildAt(0);
//                View view1 = getChildAt(1);
//                while (view1.getPivotY() < view0.getPivotY()) {
//                    requestLayout();
//                }
//            }
//
//        }


        return true;
    }

}

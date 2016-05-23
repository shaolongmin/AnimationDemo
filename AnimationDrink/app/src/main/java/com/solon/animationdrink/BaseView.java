package com.solon.animationdrink;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 豪杰 on 2016/5/20.
 */
public abstract class BaseView extends View {

    private int positionX,positionY;

    private int width,height;

    private Paint paint;

    private int location;


    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        floatAnim(this, 200);
    }


    public void setColor(int color){
        paint.setColor(color);
    }

    protected void setMyWidth(int width){
        this.width = width;
    }

    protected void setMyHeight(int height){
        this.height = height;
    }

    public abstract void setWidth(int width);

    public abstract void setHeight(int width);

    public int getPostionX() {
        return positionX;
    }

    public int getMyWidth() {
        return width;
    }

    public int getMyHeight() {
        return height;
    }

    public void setPostionX(int postionX) {
        this.positionX = postionX;
        getParent().requestLayout();
        Log.e("TAG","positionX = " + postionX);
    }

    public int getPostionY() {
        return positionY;
    }

    public void setPostionY(int postionY) {
        this.positionY = postionY;
    }

    public void setLocation(int i){
        location = i;
    }
    public int getLocation(){return location;}

    private void floatAnim(View view,int delay){

        List<Animator> animators = new ArrayList<>();

        float offsetX = (float) (4 + (Math.random() * 8));
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -offsetX,offsetX,-offsetX);
        translationXAnim.setDuration(800 + (long) (Math.random() * 1000));
        translationXAnim.setRepeatCount(-1);//无限循环
        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//

//        DecelerateInterpolator overshootInterpolator = new DecelerateInterpolator ();
//        translationXAnim.setInterpolator(overshootInterpolator);

        translationXAnim.start();
        animators.add(translationXAnim);
        float offsetY = (float) (3 + (Math.random() * 6));
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -offsetY,offsetY,-offsetY);
        translationYAnim.setDuration(800 + (long) (Math.random() * 1000));
        translationYAnim.setRepeatCount(-1);
        translationYAnim.setRepeatMode(ValueAnimator.INFINITE);
//        translationYAnim.setInterpolator(overshootInterpolator);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int radius = width / 2;
        canvas.drawCircle(radius,radius,radius,paint);
    }
}

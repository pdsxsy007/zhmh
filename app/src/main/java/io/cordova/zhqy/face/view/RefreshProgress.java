package io.cordova.zhqy.face.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import io.cordova.zhqy.R;


/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class RefreshProgress extends ImageView {

    private Matrix m = new Matrix();
    //匀速加速器
    private LinearInterpolator lir = new LinearInterpolator();
    public RefreshProgress(Context context) {
        super(context);
    }

    public RefreshProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void changeAnimation(int num){
        m.reset();
        //
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.wai))
                .getBitmap();
        this.setImageBitmap(bitmap); //显示图像
        //
        m.setRotate(num);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,true);
        BitmapDrawable bd = new BitmapDrawable(newBitmap);
        this.setImageDrawable(bd); //显示新的图像

    }

    //控制动画
    public void Animation(){
        RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //默认为0，为-1时一直循环动画
        rotate.setRepeatCount(-1);
        //添加匀速加速器
        rotate.setInterpolator(lir);
        rotate.setDuration(2300);
        rotate.setFillAfter(true);
        this.startAnimation(rotate);
    }

    //控制动画
    public void Animation2(){
        RotateAnimation rotate = new RotateAnimation(720, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //默认为0，为-1时一直循环动画
        rotate.setRepeatCount(-1);
        //添加匀速加速器
        rotate.setInterpolator(lir);
        rotate.setDuration(2400);
        rotate.setFillAfter(true);
        this.startAnimation(rotate);
    }

}
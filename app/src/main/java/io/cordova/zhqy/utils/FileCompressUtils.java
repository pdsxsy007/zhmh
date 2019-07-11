package io.cordova.zhqy.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class FileCompressUtils {
    @SuppressLint("NewApi")
    public boolean compressImgFile(String path) {
        try {
            Bitmap bitmap = comp(BitmapFactory.decodeFile(path));
            //压缩结束后，将原来的原图删除
            new File(path).delete();
            //创建和将压缩后最新的bitmap写入保存
            File newFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            ////压缩好比例大小后再进行质量压缩继续压缩70%
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            fileOutputStream.close();
            bitmap.recycle();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @SuppressLint("NewApi")
    private Bitmap comp(Bitmap image) {
        //质量压缩法：
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            //重置baos即清空baos
            //这里压缩50%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        //图片按比例大小压缩方法（根据路径获取图片并压缩）：
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //这里设置高度为800f
        float hh = 800f;
        //这里设置宽度为480f
        float ww = 480f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;
        //be=1表示不缩放
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        //设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        //降低图片从ARGB888到RGB565 //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        try {
            isBm.close();

            baos.close();
            image.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}

package com.example.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.MaskFilter;
import android.graphics.Point;

/**
 * Created by Lee on 2017/4/25.
 */

public class PictrueUtils {
    public static Bitmap getScaledBitmap(String path, int destWith, int destHeight){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth=options.outWidth;
        float srcHight=options.outHeight;

        int inSampleSize=1;
        if((srcHight>destHeight)||(srcWidth>destWith)){
            if (srcWidth>srcHight){
                inSampleSize=Math.round(srcHight/destHeight);
            }else{
                inSampleSize= Math.round(srcWidth/destWith);
            }
        }

        options=new BitmapFactory.Options();
        options.inSampleSize=inSampleSize;

        return BitmapFactory.decodeFile(path,options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size=new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path,size.x,size.y);
    }

;}

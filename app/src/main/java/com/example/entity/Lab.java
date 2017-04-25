package com.example.entity;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/**
 * Created by jp on 4/25/17.
 */

public class Lab {
    private static Lab sLab;
    private Context mContext;

    public static Lab get(Context context){
        if(sLab==null){
            sLab=new Lab(context);
        }
        return sLab;
    }

    private Lab(Context context){
        mContext=context.getApplicationContext();
    }

    public File getPhotoFile(Book book){
        File externalFileDir=mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFileDir==null){
            return null;
        }
        return new File(externalFileDir,book.getPhotoFilename());
    }

}

package com.example.picbook;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.example.common.PictrueUtils;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by jp on 4/26/17.
 */

public class PicDrawActivity extends Activity implements OnClickListener,OnTouchListener {
    ImageView basicPic;
    String filePath;
    File photoFile;
    Bitmap basic;
    Bitmap draw;
    Canvas canvas;
    Paint paint;
    Matrix matrix;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_draw);
        basicPic=(ImageView)this.findViewById(R.id.basic_pic);
        filePath=(String)getIntent().getSerializableExtra("pic");
        photoFile=new File(filePath);

        basic=updatebasicPic();

        draw= Bitmap.createBitmap(basic.getWidth(),basic.getHeight(),basic.getConfig());

        canvas=new Canvas(draw);
        paint=new Paint();

        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        matrix =new Matrix();
        canvas.drawBitmap(basic,matrix,paint);

        basicPic.setImageBitmap(draw);
        basicPic.setOnTouchListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.complete:
                savePic();
                setResult(RESULT_OK);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v){
        
    }

    float downx=0;
    float downy=0;
    float upx=0;
    float upy;

    public boolean onTouch(View v, MotionEvent event){
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downx=event.getX();
                downy=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx=event.getX();
                upy=event.getY();
                canvas.drawLine(downx,downy,upx,upy,paint);
                basicPic.invalidate();
                downx=upx;
                downy=upy;
                break;
            case MotionEvent.ACTION_UP:
                upx=event.getX();
                upy=event.getY();
                canvas.drawLine(downx,downy,upx,upy,paint);
                basicPic.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }


    private Bitmap updatebasicPic(){
        if(filePath==null||!photoFile.exists()){
            basicPic.setImageDrawable(null);
        }else {
            Bitmap bitmap= PictrueUtils.getScaledBitmap(photoFile.getPath(),this);
            basicPic.setImageBitmap(bitmap);
            return bitmap;
        }
        return null;
    }

    void savePic(){
        if(draw!=null){
            Uri imageFileUri=Uri.fromFile(photoFile);
            try{
                OutputStream imageFileOut=getContentResolver().openOutputStream(imageFileUri);
                draw.compress(Bitmap.CompressFormat.JPEG,100,imageFileOut);
            }catch (Exception e){
                Log.v("FIEL not found",e.getMessage());
            }

        }
    }
}

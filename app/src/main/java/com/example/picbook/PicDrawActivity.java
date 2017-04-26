package com.example.picbook;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
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

/**
 * Created by jp on 4/26/17.
 */

public class PicDrawActivity extends Activity implements OnClickListener,OnTouchListener {
    ImageView basicPic;
    String filePath;
    File photoFile;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_draw);
        basicPic=(ImageView)this.findViewById(R.id.basic_pic);
        filePath=(String)getIntent().getSerializableExtra("pic");
        photoFile=new File(filePath);
        updatebasicPic();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.complete:
                setResult(RESULT_OK);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v){
        
    }

    public boolean onTouch(View v, MotionEvent event){
        int action=event.getAction();
        return true;
    }


    private void updatebasicPic(){
        if(filePath==null||!photoFile.exists()){
            basicPic.setImageDrawable(null);
        }else {
            Bitmap bitmap= PictrueUtils.getScaledBitmap(photoFile.getPath(),this);
            basicPic.setImageBitmap(bitmap);
        }
    }
}

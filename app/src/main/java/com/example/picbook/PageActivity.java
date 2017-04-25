package com.example.picbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import com.example.common.DataTemp;
import com.example.entity.Book;
import com.example.entity.Page;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
public class PageActivity extends FragmentActivity {
    private ArrayList<Page> mPages;///////page
    private UUID bookId;
    private Page page;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        bookId = (UUID) getIntent().getSerializableExtra("bookId");
        UUID pageId = (UUID) getIntent().getSerializableExtra("pageId");

        Book book = DataTemp.getBook(bookId);
        if (book != null) {
            mPages = book.getmPages();
            if (pageId != null) {
                page = book.getPage(pageId);
            }
        }
        for (int i = 0; i < mPages.size(); i++) {
            if (mPages.get(i).getId().equals(pageId)) {
                mViewPager.setCurrentItem(i);
            }
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Page p = mPages.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookId", bookId);
                bundle.putSerializable("pageId", p.getId());
                return PageFragment.newInstance(bundle);
            }

            @Override
            public int getCount() {
                return mPages.size();
            }
        });
    }

    public static void saveImage(Bitmap photo, String path) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            new DateFormat();
            String filename = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            try {
                OutputStream out=this.openFileOutput(filename, Context.MODE_PRIVATE);
                out.write(data.getExtras().getByte("data"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}

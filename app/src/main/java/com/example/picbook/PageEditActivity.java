package com.example.picbook;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Hasee on 2016/6/18.
 */
public class PageEditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID bookId=(UUID)getIntent().getSerializableExtra("bookId");
        return PageEditFragment.newInstance(bookId);
    }

}
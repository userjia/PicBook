package com.example.picbook;


import android.support.v4.app.Fragment;

import java.util.UUID;

public class PageListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID pagePos=(UUID)getIntent().getSerializableExtra("bookId");
        return PageListFragment.newInstance(pagePos);
    }
}

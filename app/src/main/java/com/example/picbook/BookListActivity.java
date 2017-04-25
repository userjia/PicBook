package com.example.picbook;

import android.support.v4.app.Fragment;

public class BookListActivity extends SingleFragmentActivity {//通过统一结构的fragment，单独实现不同的create方法，也处理与fragment的数据传递
    @Override
    protected Fragment createFragment() {
        return new BookListFragment();
    }
}

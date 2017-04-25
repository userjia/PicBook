package com.example.picbook;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class BookEditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID bookId = (UUID) getIntent().getSerializableExtra(BookEditFragment.EXTRA_BOOK_ID);
        return BookEditFragment.newInstance(bookId);
    }

}

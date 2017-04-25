package com.example.picbook;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class EditBookActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID bookId = (UUID) getIntent().getSerializableExtra(EditBookFragment.EXTRA_BOOK_ID);
        return EditBookFragment.newInstance(bookId);
    }

}

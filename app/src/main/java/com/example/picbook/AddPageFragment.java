package com.example.picbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.common.DataTemp;
import com.example.entity.Book;
import com.example.entity.Page;
import java.util.UUID;

public class AddPageFragment extends Fragment {
    private Page page;
    private EditText info;
    private EditText num;
    private EditText text;
    private UUID bookId;
    private UUID pageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("NewPage");
        bookId = (UUID) getArguments().getSerializable("bookId");
        Book book = DataTemp.getBook(bookId);
        if (book != null) {
            page = new Page();
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_page_suc, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.complete:
                if (bookId != null) {
                    DataTemp.addPage(bookId, page);
                }
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                return true;
            case R.id.add_page_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, parent, false);
        num = (EditText) v.findViewById(R.id.page_id);
        info = (EditText) v.findViewById(R.id.page_info);
        text = (EditText) v.findViewById(R.id.page_text);
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                page.setNum(num.getText().toString());
            }
        });

        info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                page.setInfo(info.getText().toString());
            }
        });
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                page.setText(text.getText().toString());
            }
        });
        return v;
    }

    public static AddPageFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable("bookId", id);
        AddPageFragment fragment = new AddPageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

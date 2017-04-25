package com.example.picbook;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.common.DataTemp;
import com.example.entity.Book;

import java.util.ArrayList;
import java.util.UUID;

public class EditBookFragment extends Fragment {
    private Book book;
    private ArrayList<Book> mBooks;
    private EditText title;
    private EditText author;
    private EditText info;
    private UUID bookId;
    public static final String EXTRA_BOOK_ID = "com.example.intent.book_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookId = (UUID) getArguments().getSerializable(EXTRA_BOOK_ID);
        if (bookId != null) {
            book = DataTemp.getBook(bookId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_book_fragment, parent, false);

        title = (EditText) v.findViewById(R.id.add_book_title);
        author = (EditText) v.findViewById(R.id.add_book_author);
        info = (EditText) v.findViewById(R.id.add_book_info);
        if (bookId != null) {
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            info.setText(book.getIntro());
        }
        Button button_add_book = (Button) v.findViewById(R.id.button_add_book);
        button_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText() != null) {
                    book = new Book();
                    book.setTitle(title.getText().toString());
                    book.setAuthor(author.getText().toString());
                    book.setIntro(info.getText().toString());
                    if (bookId != null) {
                        DataTemp.setBook(bookId, book);
                    } else {
                        DataTemp.addBook(book);
                    }

                }
                getActivity().setResult(Activity.RESULT_OK, null);
                getActivity().finish();
            }
        });
        return v;
    }

    public static EditBookFragment newInstance(UUID bookId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK_ID, bookId);
        EditBookFragment fragment = new EditBookFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

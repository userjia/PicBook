package com.example.picbook;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.common.DataTemp;
import com.example.entity.Book;
import com.example.entity.Lab;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BookEditFragment extends Fragment {
    private Book book;
    private EditText title;
    private EditText author;
    private EditText info;
    private UUID bookId;
    private File photoFile;
    ImageView photoView;
    public static final String EXTRA_BOOK_ID = "com.example.intent.book_id";
    private static final int REQUEST_PHOTO=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        bookId = (UUID) getArguments().getSerializable(EXTRA_BOOK_ID);
        if (bookId != null) {
            book = DataTemp.getBook(bookId);
            photoFile= Lab.get(getActivity()).getPhotoFile(book);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_edit_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_book_photo:
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 1);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_edit_fragment, parent, false);

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

        final Intent captureImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager=getActivity().getPackageManager();
        boolean canTakePhoto=photoFile!=null&&captureImage.resolveActivity(packageManager)!=null;
        if (canTakePhoto){
            Uri uri =Uri.fromFile(photoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }

        Button button_add_photo = (Button) v.findViewById(R.id.button_add_photo);
        button_add_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });
        photoView=(ImageView) v.findViewById(R.id.book_photo);

        return v;
    }

    public static BookEditFragment newInstance(UUID bookId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK_ID, bookId);
        BookEditFragment fragment = new BookEditFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

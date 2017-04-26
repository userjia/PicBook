package com.example.picbook;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.common.DataTemp;
import com.example.common.PictrueUtils;
import com.example.entity.Book;
import com.example.entity.Page;

import java.io.File;
import java.util.UUID;

public class PageEditFragment extends Fragment {
    private Page page;
    private EditText info;
    private EditText num;
    private EditText text;
    private Button takePhoto;
    private Button commit;
    private UUID bookId;
    private ImageView photoView;
    private UUID pageId;
    private static final int REQUEST_PHOTO=2;
    File photoFile;
    String filePath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bookId = (UUID) getArguments().getSerializable("bookId");
        pageId = (UUID) getArguments().getSerializable("pageId");
        Book book = DataTemp.getBook(bookId);
        if (book != null) {
            getActivity().setTitle(book.getTitle());
            if(pageId!=null){
                page = book.getPage(pageId);
            }else {
                page = new Page();
                getActivity().setTitle("NewPage");
            }

            if (page.ifNullPath()){///
                File externalFileDir=getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                filePath=externalFileDir+page.getPhotoFilename();
            }else {
                filePath= page.getFilePath();
            }
            photoFile=new File(filePath);

        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.page_edit_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.complete:
                savePage();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_edit_fragment, parent, false);
        num = (EditText) v.findViewById(R.id.page_id);
        info = (EditText) v.findViewById(R.id.page_info);
        text = (EditText) v.findViewById(R.id.page_text);
        takePhoto = (Button) v.findViewById(R.id.take_page_photo);
        commit = (Button) v.findViewById(R.id.commit_page);
        photoView=(ImageView) v.findViewById(R.id.page_photo);
        if (pageId != null) {
            num.setText(page.getNum());
            info.setText(page.getInfo());
            text.setText(page.getText());
        }

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

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePage();
            }
        });

        final Intent captureImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager=getActivity().getPackageManager();
        boolean canTakePhoto=photoFile!=null&&captureImage.resolveActivity(packageManager)!=null;
        if (canTakePhoto){
            Uri uri =Uri.fromFile(photoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }

        takePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });

        updatePhotoView();

        return v;
    }

    private void updatePhotoView(){
        if(photoFile==null||!photoFile.exists()){
            photoView.setImageDrawable(null);
        }else {
            Bitmap bitmap= PictrueUtils.getScaledBitmap(photoFile.getPath(),getActivity());
            photoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_PHOTO){
            updatePhotoView();
        }
    }


    public static PageEditFragment newInstance(Bundle args) {
        //args.putSerializable("bookId", id);
        PageEditFragment fragment = new PageEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    void savePage() {
        if (bookId != null) {
            page.setFilePath(filePath);
            if(pageId==null){
                DataTemp.addPage(bookId, page);
            }else {
                DataTemp.setPage(bookId,pageId,page);
            }

        }
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}

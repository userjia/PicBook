package com.example.picbook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.common.DataTemp;
import com.example.entity.Book;
import com.example.entity.Page;

import java.util.ArrayList;
import java.util.UUID;

public class PageListFragment extends ListFragment {
    private ArrayList<Page> mPages;
    private UUID bookId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bookId = (UUID) getArguments().getSerializable("bookId");
        Book book = DataTemp.getBook(bookId);

        if (book != null) {
            getActivity().setTitle(book.getTitle());
            if (book.getmPages() == null) {
                mPages = new ArrayList<Page>();
                book.setmPages(mPages);
            } else {
                mPages = book.getmPages();
            }
        } else {
            getActivity().finish();
        }

        PageAdapter pageAdapter = new PageAdapter(mPages);
        setListAdapter(pageAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.page_list_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_page:
                Intent intent = new Intent(getActivity(), PageEditActivity.class);
                intent.putExtra("bookId", bookId);
                startActivityForResult(intent, 1);
                return true;
            case R.id.menu_item_edit_book:
                Intent intent2 = new Intent(getActivity(), BookEditActivity.class);
                intent2.putExtra(BookEditFragment.EXTRA_BOOK_ID, bookId);
                startActivityForResult(intent2, 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Page b = (Page) (getListAdapter()).getItem(position);
        Log.d("PageList", b.getId() + " was clicked");

        Intent intent = new Intent(getActivity(), PageActivity.class);
        intent.putExtra("pageId", b.getId());
        intent.putExtra("bookId", bookId);
        intent.putExtra("type", "edit");
        startActivityForResult(intent, 3);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //mPages=book.getmPages();
        ((PageAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPages=book.getmPages();
        ((PageAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class PageAdapter extends ArrayAdapter<Page> {
        public PageAdapter(ArrayList<Page> Pages) {
            super(getActivity(), android.R.layout.simple_list_item_1, Pages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.page_list, null);
            }
            Page b = getItem(position);
            TextView textView1 = (TextView) convertView.findViewById(R.id.page_id);
            TextView textView2 = (TextView) convertView.findViewById(R.id.page_info);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.page_pic);
            textView1.setText(b.getNum());
            textView2.setText(b.getInfo());
            imageView.setImageBitmap(b.loadPic());
            return convertView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static PageListFragment newInstance(UUID pagePos) {
        Bundle args = new Bundle();
        args.putSerializable("bookId", pagePos);
        PageListFragment fragment = new PageListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

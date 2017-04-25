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
import java.util.ArrayList;

public class BookListFragment extends ListFragment {
    public ArrayList<Book> mBooks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Books");

        BookAdapter bookAdapter = new BookAdapter(DataTemp.getBooks());//适配器与列表匹配，一旦数据变化就要投射到视图
        setListAdapter(bookAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {//上方选项
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_setup, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {//菜单具体点击行为
        switch (item.getItemId()) {
            case R.id.menu_item_new_book:
                Intent intent = new Intent(getActivity(), EditBookActivity.class);
                //intent.putExtra(EditBookFragment.EXTRA_BOOK_ID, book.getId());
                //startActivityForResult(intent, 0);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {//监听列表
        Book b = (Book) (getListAdapter()).getItem(position);
        Log.d("BookList", b.getTitle() + " was clicked");

        Intent intent = new Intent(getActivity(), PageListActivity.class);
        intent.putExtra("bookId", b.getId());
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {//intent请求返回处理
        ((BookAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onResume() {//回到运行态时的数据提示显示处理
        super.onResume();
        ((BookAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class BookAdapter extends ArrayAdapter<Book> {//自定义适配器
        public BookAdapter(ArrayList<Book> books) {
            super(getActivity(), android.R.layout.simple_list_item_1, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.book_list, null);
            }
            Book b = getItem(position);
            TextView textView1 = (TextView) convertView.findViewById(R.id.book_title);
            TextView textView2 = (TextView) convertView.findViewById(R.id.book_author);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.book_cover);
            textView1.setText(b.getTitle());
            textView2.setText(b.getAuthor());
            imageView.setImageBitmap(b.getCover());

            return convertView;
        }
    }
}

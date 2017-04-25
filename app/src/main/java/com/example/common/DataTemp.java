package com.example.common;

import android.app.Application;
import android.util.Log;
import com.example.entity.Book;
import com.example.entity.Page;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class DataTemp extends Application{//存储全局信息，在程序结束时把信息放到json文件中，开始时读取

    private static DataTemp DataTemp = new DataTemp();
    public static DataTemp getDataTemp() {
        return DataTemp;
    }

    private static ArrayList<Book> books;
    private BookIntentJSONSerializer serializer;

    public static final String TAG="app";
    public static final String FILE_BOOKS="bookList.json";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        DataTemp =this;

        serializer=new BookIntentJSONSerializer(this,FILE_BOOKS);
        try {
            books=serializer.loadBooks();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            books=new ArrayList<Book>();
        }
    }
    @Override
    public void onTerminate() {//结束时存储
        Log.d(TAG, "onTerminate");
        super.onTerminate();

        try {
            serializer.saveBooks(books);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addBook(Book b) {
        books.add(b);
    }

    public static ArrayList<Book> getBooks(){
        return books;
    }

    public static Book getBook(UUID id) {//获取书，存入书，页等等
        for (Book b : books) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public static void setBook(UUID id,Book ib){
        int i=0;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                books.set(i,ib);
            }
            i++;
        }
    }

    public static void setPage(UUID bid,UUID pid,Page ip){
        int i=0;
        for (Book b : books) {
            if (b.getId().equals(bid)) {
                books.get(i).setPage(pid,ip);
            }
            i++;
        }
    }
    public static void addPage(UUID bid,Page ip){
        int i=0;
        for (Book b : books) {
            if (b.getId().equals(bid)) {
                books.get(i).addPage(ip);
            }
            i++;
        }
    }

    public static void addPages(UUID bid,ArrayList<Page> ps){
        int i=0;
        for (Book b : books) {
            if (b.getId().equals(bid)) {
                books.get(i).setmPages(ps);
            }
            i++;
        }
    }

    @Override
    public void onTrimMemory(int level) {//内存清理时存储
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);

        try {
            serializer.saveBooks(books);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }
}

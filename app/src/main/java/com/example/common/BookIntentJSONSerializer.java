package com.example.common;

import android.content.Context;

import com.example.entity.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Hasee on 2016/6/16.
 */
public class BookIntentJSONSerializer {

    private Context context;
    private String filename;

    public BookIntentJSONSerializer(Context c,String f) {
        context=c;
        filename=f;
    }

    public ArrayList<Book> loadBooks() throws IOException, JSONException {//从本地文件加载书
        ArrayList<Book> books=new ArrayList<Book>();
        BufferedReader reader=null;
        try{
            InputStream in=context.openFileInput(filename);
            reader =new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line=null;
            while ((line=reader.readLine())!=null) {
                jsonString.append(line);
            }
            JSONArray array=(JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            for (int i=0;i<array.length();i++) {
                Book nb= new Book(array.getJSONObject(i));
                books.add(nb);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
                if (reader!=null) {
                reader.close();
                }
        }
        return books;
    }

    public void saveBooks(ArrayList<Book> books) throws JSONException, IOException {//存储到本地文件
        JSONArray array=new JSONArray();
        for (Book b:books) {
            array.put(b.toJSON());
        }

        Writer writer=null;
        try{
            OutputStream outputStream=context.openFileOutput(filename,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(outputStream);
            writer.write(array.toString());
        }finally {
            if (writer!=null) {
                writer.close();
            }
        }

    }
}

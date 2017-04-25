package com.example.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.picbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

/**
 * Created by Hasee on 2016/6/15.
 */
public class Page {
    private UUID id;
    private String num;
    private String info;
    private String text;
    private boolean mark;
    private String pic;

    public Page() {
        id = UUID.randomUUID();
    }

    public Page(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString("id"));
        num = json.getString("num");
        info = json.getString("info");
        text = json.getString("text");
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        if (id!=null){
            json.put("id",id.toString());
        }else {
            json.put("id"," ");
        }
        if(num!=null){
            json.put("title", num);
        }else {
            json.put("title", " ");
        }
        if (info!=null){
            json.put("author", info);
        }else {
            json.put("author", " ");
        }
        if (text!=null){
            json.put("author", text);
        }else {
            json.put("author", " ");
        }
        return json;
    }

    public Bitmap loadPic(){
        Bitmap imageBitmap=null;
        if (pic!=null) {
            imageBitmap = BitmapFactory.decodeFile("data/data/PicBook/files"+pic);
        }
        return imageBitmap;
    }

    public String getInfo() {
        return info;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}

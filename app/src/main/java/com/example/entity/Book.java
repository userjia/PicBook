package com.example.entity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Book {
    private UUID id;
    private Bitmap cover;
    private String author;
    private String title;
    private String intro;
    private ArrayList<Page> mPages;
    private String FILENAME;

    public String toString() {
        return title;
    }

    public Book() {
        id = UUID.randomUUID();
        FILENAME = id.toString() + ".json";

    }

    public Book(JSONObject json) throws JSONException {//json转为类
        id = UUID.fromString(json.getString("id"));
        title = json.getString("title");
        author = json.getString("author");
        if (json.has("page")){
            JSONArray jsonArray = json.getJSONArray("page");
            if (jsonArray != null) {
                mPages = new ArrayList<Page>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPage = jsonArray.getJSONObject(i);
                    Page p = new Page(jsonPage);
                    mPages.add(p);
                }
            }
        }
    }

    public JSONObject toJSON() throws JSONException {//具体类转为json的方法
        JSONObject json = new JSONObject();
        json.put("id", id.toString());
        json.put("title", title);
        json.put("author", author);
        //json.put("cover", "pic");
        if (mPages!=null) {
            JSONArray jsonArray = new JSONArray();
            for (Page p : mPages) {
                jsonArray.put(p.toJSON());
            }
            json.put("page", jsonArray);
        }

        return json;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Page> getmPages() {
        if (mPages != null) {
            return mPages;
        } else {
            return null;
        }

    }


    public Page getPage(UUID id) {
        for (Page b : mPages) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public void setPage(UUID id, Page p) {
        int i = 0;
        for (Page b : mPages) {
            if (b.getId().equals(id)) {
                mPages.set(i, p);
            }
            i++;
        }
    }
    public void addPage(Page p) {
        mPages.add(p);
    }


    public void setmPages(ArrayList<Page> mPages) {
        this.mPages = mPages;
    }

    public Book(Bitmap c, String t, String i) {
        super();
        id = UUID.randomUUID();
        this.cover = c;
        this.title = t;
        this.intro = i;
    }/**/

    public String getPhotoFilename(){
        return "IMG"+getId().toString()+".jpg";
    }

}

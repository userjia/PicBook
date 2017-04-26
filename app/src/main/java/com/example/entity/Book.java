package com.example.entity;

import android.app.Activity;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Book {
    private UUID id;
    private Bitmap cover;
    private String author;
    private String title;
    private String intro;
    private ArrayList<Page> mPages;
    private String FILENAME;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private File photoFile;
    private String filePath;

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
        filePath=json.getString("cover");
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
        if (id!=null){
            json.put("id",id.toString());
        }else {
            json.put("id"," ");
        }
        if(title!=null){
            json.put("title", title);
        }else {
            json.put("title", " ");
        }
        if (author!=null){
            json.put("author", author);
        }else {
            json.put("author", " ");
        }

        json.put("cover", filePath);
        if (mPages!=null) {
            JSONArray jsonArray = new JSONArray();
            for (Page p : mPages) {
                jsonArray.put(p.toJSON());
            }
            json.put("page", jsonArray);
        }

        return json;
    }

    //JSONObject putInJson(JSONObject jsonObject, Map<String,String> content) throws JSONException{}

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

    public File getPhotoFile() {

        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
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


    public boolean ifNullPath(){
        if (this.filePath==null){
            return true;
        }else {
            return false;
        }
    }

}

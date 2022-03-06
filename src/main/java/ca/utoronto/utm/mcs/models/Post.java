package ca.utoronto.utm.mcs.models;

import org.json.JSONArray;

import java.util.ArrayList;

public class Post {
    private String _id;
    private String title;
    private String author;
    private String content;
    private ArrayList<String> tags;

    //setter methods
    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    // getters
    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}

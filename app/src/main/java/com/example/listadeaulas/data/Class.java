package com.example.listadeaulas.data;

import java.io.Serializable;

public class Class implements Serializable {
    private int id;
    private String title;
    private String matter;
    private String url;

    public Class (int id, String name, String matter, String url){
        this.id = id;
        this.title = name;
        this.matter = matter;
        if (url.startsWith("http://") || url.startsWith("https://")){
            this.url = url;
        }
        else{
            this.url = "http://" +url;
        }
    }

    public Class (String name, String matter, String url){
        this.title = name;
        this.matter = matter;
        if (url.startsWith("http://") || url.startsWith("https://")){
            this.url = url;
        }
        else{
            this.url = "http://" +url;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

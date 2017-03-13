package com.raul.bupt.dataobject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Article implements Serializable {

    //文章标题
    private String title;

    //文章内容
    private String content;

    //文章发表日期
    private String date;

    //文章对应链接
    private String link;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return this.title + "\t" + this.date + "\t" + this.content + "\t" + this.link;
    }
}
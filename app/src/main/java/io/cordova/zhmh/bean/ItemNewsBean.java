package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/22 0022.
 */

public class ItemNewsBean {
    private String newsTitle;

    private String newsHref;

    private String newsDate;

    private String newsId;

    public void setNewsTitle(String newsTitle){
        this.newsTitle = newsTitle;
    }
    public String getNewsTitle(){
        return this.newsTitle;
    }
    public void setNewsHref(String newsHref){
        this.newsHref = newsHref;
    }
    public String getNewsHref(){
        return this.newsHref;
    }
    public void setNewsDate(String newsDate){
        this.newsDate = newsDate;
    }
    public String getNewsDate(){
        return this.newsDate;
    }
    public void setNewsId(String newsId){
        this.newsId = newsId;
    }
    public String getNewsId(){
        return this.newsId;
    }
}

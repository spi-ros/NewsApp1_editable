package com.example.android.newsapp1;


public class NewsModel {

    private String articleTitle;
    private String sectionName;
    private String authorName;
    private String dateOfCreate;
    private String myUrl;

    NewsModel(String articleTitle, String sectionName, String authorName, String dateOfCreate, String url) {
        this.articleTitle = articleTitle;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.dateOfCreate = dateOfCreate;
        this.myUrl = url;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }

    public String getUrl() { return myUrl; }
}

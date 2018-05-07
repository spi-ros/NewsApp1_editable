package com.example.android.newsapp1;


public class NewsModel {

    private String articleTitle;
    private String sectionName;
    private String authorName;
    private String dateOfCreate;
    //private String myUrl;

    NewsModel(String articleTitle, String sectionName, String authorName, String dateOfCreate, String url) {
        this.articleTitle = articleTitle;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.dateOfCreate = dateOfCreate;
        //this.myUrl = url;
    }

    // Get the title of article
    public String getArticleTitle() {
        return articleTitle;
    }
    // Get the section of the article
    public String getSectionName() {
        return sectionName;
    }
    // Get the author of the article
    public String getAuthorName() {
        return authorName;
    }
    // Get the date of the article
    public String getDateOfCreate() {
        return dateOfCreate;
    }
    // Get the url of the article
   // public String getUrl() { return myUrl; }
}

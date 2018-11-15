package com.gogoteam.wintecpathways;


public class StudentProductsActivity {
    private String id;
    private String title;
    private String shortdesc;
    private String button;
    //private int image;

    public StudentProductsActivity(String id, String title, String shortdesc, String button) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.button = button;
        //this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getButton() {
        return button;
    }


    //public int getImage() {
        //return image;

}
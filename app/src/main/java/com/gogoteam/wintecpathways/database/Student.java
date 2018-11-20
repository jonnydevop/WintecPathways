package com.gogoteam.wintecpathways.database;

import android.media.Image;

import java.util.List;
import java.util.LinkedList;

public class Student {
    private	String SID;
    private	String SName;
    private	String Email;
    private	String Specialisation;
    private	String Programme;
    private String Date_Enrolled;
    private List<StudentModule> studentModules;
    private String SImage;

    //private int image;

    public Student() {
        studentModules = new LinkedList<>();
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(String specialisation) {
        Specialisation = specialisation;
    }

    public String getProgramme() {
        return Programme;
    }

    public void setProgramme(String programme) {
        Programme = programme;
    }

    public String getDate_Enrolled() {
        return Date_Enrolled;
    }

    public void setDate_Enrolled(String date_Enrolled) {
        Date_Enrolled = date_Enrolled;
    }

    public List<StudentModule> getModules() {
        return studentModules;
    }

    public void setModules(List<StudentModule> modules) {
        this.studentModules = modules;
    }

    public void setSImage(String SImage) {
        this.SImage = SImage;
    }

    public String getSImage() {
        return SImage;
    }
}

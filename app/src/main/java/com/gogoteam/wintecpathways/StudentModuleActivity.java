package com.gogoteam.wintecpathways;


public class StudentModuleActivity {
    private String id;
    private	String MID;
    private	String MName;
    private	String description;
    private	String level;
    private	String preMID_1;
    private	String preMID_2;
    private	String preMID_3;
    private	String pathway_1;
    private	String pathway_2;
    private	String pathway_3;
    private	String classification;
    private String credits;
    private String year;
    private String semester;
    private String button;
    private int image;

    public StudentModuleActivity() { }


    public StudentModuleActivity(String MID, String MName, String description, String level, String preMID_1, String preMID_2,
                                 String preMID_3, String pathway_1, String pathway_2, String pathway_3, String classification,
                                 String credits, String year, String semester, String button) {

        this.MID = MID;
        this.MName = MName;
        this.description = description;
        this.level = level;
        this.preMID_1 = preMID_1;
        this.preMID_2 = preMID_2;
        this.preMID_3 = preMID_3;
        this.pathway_1 = pathway_1;
        this.pathway_2 = pathway_2;
        this.pathway_3 = pathway_3;
        this.classification = classification;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.button = button;
        //this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPreMID_1() {
        return preMID_1;
    }

    public void setPreMID_1(String preMID_1) {
        this.preMID_1 = preMID_1;
    }

    public String getPreMID_2() {
        return preMID_2;
    }

    public void setPreMID_2(String preMID_2) {
        this.preMID_2 = preMID_2;
    }

    public String getPreMID_3() {
        return preMID_3;
    }

    public void setPreMID_3(String preMID_3) {
        this.preMID_3 = preMID_3;
    }

    public String getPathway_1() {
        return pathway_1;
    }

    public void setPathway_1(String pathway_1) {
        this.pathway_1 = pathway_1;
    }

    public String getPathway_2() {
        return pathway_2;
    }

    public void setPathway_2(String pathway_2) {
        pathway_2 = pathway_2;
    }

    public String getPathway_3() {
        return pathway_3;
    }

    public void setPathway_3(String pathway_3) {
        pathway_3 = pathway_3;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
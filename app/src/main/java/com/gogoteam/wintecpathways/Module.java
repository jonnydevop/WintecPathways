package com.gogoteam.wintecpathways;

public class Module {
    private	String MID;
    private	String MName;
    private	String Description;
    private	String Level;
    private	String PreMID_1;
    private	String PreMID_2;
    private	String PreMID_3;
    private	String Pathway_1;
    private	String Pathway_2;
    private	String Pathway_3;
    private	String Classification;
    private String Credits;
    private String Year;
    private String Semester;

    public Module() {

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

    public String getPreMID_1() {
        return PreMID_1;
    }

    public void setPreMID_1(String preMID_1) {
        PreMID_1 = preMID_1;
    }

    public String getPreMID_2() {
        return PreMID_2;
    }

    public void setPreMID_2(String preMID_2) {
        PreMID_2 = preMID_2;
    }

    public String getPreMID_3() {
        return PreMID_3;
    }

    public void setPreMID_3(String preMID_3) {
        PreMID_3 = preMID_3;
    }

    public String getPathway_1() {
        return Pathway_1;
    }

    public void setPathway_1(String pathway_1) {
        Pathway_1 = pathway_1;
    }

    public String getPathway_2() {
        return Pathway_2;
    }

    public void setPathway_2(String pathway_2) {
        Pathway_2 = pathway_2;
    }

    public String getPathway_3() {
        return Pathway_3;
    }

    public void setPathway_3(String pathway_3) {
        Pathway_3 = pathway_3;
    }

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String classification) {
        Classification = classification;
    }

    public String getCredits() {
        return Credits;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}

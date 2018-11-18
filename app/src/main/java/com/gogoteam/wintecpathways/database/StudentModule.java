package com.gogoteam.wintecpathways.database;

public class StudentModule {
    private	String SMID;
    private	String SID;
    private	String MID;
    private	String Completion;

    public StudentModule(String SID, String MID, String Completion) {
        this.SID = SID;
        this.MID = MID;
        this.Completion = Completion;
    }

    public String getSMID() {
        return SMID;
    }

    public void setSMID(String SMID) {
        this.SMID = SMID;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) { this.MID = MID; }

    public String getCompletion() {
        return Completion;
    }

    public void setCompletion(String completion) {
        Completion = completion;
    }
}

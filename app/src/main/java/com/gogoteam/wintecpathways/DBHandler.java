package com.gogoteam.wintecpathways;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wintecpathways.db";
    private static final String TABLE_NAME = "Student";
    private static final String COLUMN_SID = "SID";
    private static final String COLUMN_SName = "SName";
    private static final String COLUMN_Email = "Email";
    private static final String COLUMN_Specialisation = "Specialisation";
    private static final String COLUMN_Programme = "Programme";
    private static final String COLUMN_Date_Enrolled = "Date_Enrolled";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_SID + " TEXT, " + COLUMN_SName + " TEXT, " + COLUMN_Email + " TEXT," +
                COLUMN_Specialisation + " TEXT, " + COLUMN_Programme + " TEXT, " +
                COLUMN_Date_Enrolled + " TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add a new row to the database
    public void addStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SID, student.getSID());
        values.put(COLUMN_SName, student.getSName());
        values.put(COLUMN_Email, student.getEmail());
        values.put(COLUMN_Specialisation, student.getSpecialisation());
        values.put(COLUMN_Programme, student.getProgramme());
        values.put(COLUMN_Date_Enrolled, student.getDate_Enrolled());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    //Delete table from the database
    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        //db.close();
    }

    //Print out the database as a string
    public Student[] searchStudent() {
        //counter for loop
        int count = 0;
        //Top 3 records
        Student[] returnList = new Student[3];
        Student student = new Student();

        //DB connection
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY SID";
        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast() && count < 3) {
            if (c.getString(c.getColumnIndex("SID")) != null) {
                student.setSID(c.getString(c.getColumnIndex("SID")));
                student.setSName(c.getString(c.getColumnIndex("SName")));
                returnList[count] = student;
                c.moveToNext();
            }
            count++;
        }
        db.close();
        return returnList;
    }
}

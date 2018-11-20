package com.gogoteam.wintecpathways.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    //Database and Tables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wintecpathways.db";
    private static final String TABLE_STUDENT = "Student";
    private static final String TABLE_MODULE = "Module";
    private static final String TABLE_STUDENT_MODULE = "StudentModule";

    //Common column
    private static final String COLUMN_SID = "SID";
    private static final String COLUMN_MID = "MID";

    //Student table column
    private static final String COLUMN_SName = "SName";
    private static final String COLUMN_Email = "Email";
    private static final String COLUMN_Specialisation = "Specialisation";
    private static final String COLUMN_Programme = "Programme";
    private static final String COLUMN_Date_Enrolled = "Date_Enrolled";
    private static final String COLUMN_SImage = "SImage";

    //StudentModule table column
    private static final String COLUMN_SMID = "SMID";
    private static final String COLUMN_Completion = "Completion";

    //Module table column
    private static final String COLUMN_MName = "MName";
    private static final String COLUMN_Description = "Description";
    private static final String COLUMN_Level = "Level";
    private static final String COLUMN_PreMID_1 = "PreMID_1";
    private static final String COLUMN_PreMID_2 = "PreMID_2";
    private static final String COLUMN_PreMID_3 = "PreMID_3";
    private static final String COLUMN_Pathway_1 = "Pathway_1";
    private static final String COLUMN_Pathway_2 = "Pathway_2";
    private static final String COLUMN_Pathway_3 = "Pathway_3";
    private static final String COLUMN_Classification = "Classification";
    private static final String COLUMN_Credits = "Credits";
    private static final String COLUMN_Year = "Year";
    private static final String COLUMN_Semester = "Semester";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_SID + " TEXT, "
                + COLUMN_SName + " TEXT, "
                + COLUMN_Email + " TEXT,"
                + COLUMN_Specialisation + " TEXT, "
                + COLUMN_Programme + " TEXT, "
                + COLUMN_Date_Enrolled + " TEXT,"
                + COLUMN_SImage + " BLOB" + ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_MODULE + "("
                + COLUMN_MID + " TEXT, "
                + COLUMN_MName + " TEXT, "
                + COLUMN_Description + " TEXT, "
                + COLUMN_Level + " TEXT, "
                + COLUMN_PreMID_1 + " TEXT,"
                + COLUMN_PreMID_2 + " TEXT,"
                + COLUMN_PreMID_3 + " TEXT,"
                + COLUMN_Pathway_1 + " TEXT, "
                + COLUMN_Pathway_2 + " TEXT, "
                + COLUMN_Pathway_3 + " TEXT, "
                + COLUMN_Classification + " TEXT, "
                + COLUMN_Credits + " TEXT, "
                + COLUMN_Year + " TEXT, "
                + COLUMN_Semester + " TEXT" + ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_STUDENT_MODULE + "("
                + COLUMN_SMID + " TEXT, "
                + COLUMN_SID + " TEXT, "
                + COLUMN_MID + " TEXT,"
                + COLUMN_Completion + " TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_MODULE);
        onCreate(db);
    }

    /////////////////////////Student method begins here////////////////////////////

    //Add a student
    public boolean addStudent(Student student) {
        //Duplicate check
        Log.i("chrisita", "DB addStudent 00 ");
        if (isSIDDuplicate(student.getSID())) {return false;}

        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();

        //Add Student
        values = new ContentValues();
        values.put(COLUMN_SID, student.getSID());
        values.put(COLUMN_SName, student.getSName());
        values.put(COLUMN_Email, student.getEmail());
        values.put(COLUMN_Specialisation, student.getSpecialisation());
        values.put(COLUMN_Programme, student.getProgramme());
        values.put(COLUMN_Date_Enrolled, student.getDate_Enrolled());
        values.put(COLUMN_SImage, student.getSImage());
        db.insert(TABLE_STUDENT, null, values);

        //Add Student Module
        if (student.getModules() != null) { addStudentModule(student.getModules()); }

        db.close();
        return true;
    }

    //Add student module
    public void addStudentModule(List<StudentModule> smList) {
        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();

        for (StudentModule sm : smList) {
            values = new ContentValues();
            values.put(COLUMN_SID, sm.getSID());
            values.put(COLUMN_MID, sm.getMID());
            values.put(COLUMN_Completion, sm.getCompletion());
            db.insert(TABLE_STUDENT_MODULE, null, values);
        }

        db.close();
    }

    //Update student
    public void updateStudent(Student student) {
        Log.i("chrisita", "DB updateStudent 00 " + student.getSpecialisation());
        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = new String[1];
        String whereClause;
        //Update student
        values = new ContentValues();
        values.put(COLUMN_SName, student.getSName());
        values.put(COLUMN_Email, student.getEmail());
        values.put(COLUMN_Specialisation, student.getSpecialisation());
        values.put(COLUMN_Programme, student.getProgramme());
        values.put(COLUMN_Date_Enrolled, student.getDate_Enrolled());
        values.put(COLUMN_SImage, student.getSImage());
        whereArgs[0] = student.getSID();
        Log.i("chrisita", "DB updateStudent getSID; " + student.getSID());

        whereClause = COLUMN_SID + "=?";
        db.update(TABLE_STUDENT, values,whereClause , whereArgs);
        Log.i("chrisita", "DB updateStudent 11 " + student.getSpecialisation());

        //Update Student Module
        if (student.getModules() != null)
        {
            updateStudentModule(student.getSID(), student.getModules());
        }

        db.close();
    }

    //Update student module
    public void updateStudentModule(String SID, List<StudentModule> smList) {
        //Delete by SID
        deleteStudentModule(SID);
        //Add student module
        addStudentModule(smList);
    }

    //Delete a student
    public boolean deleteStudent(String SID) {
        SQLiteDatabase db = getWritableDatabase();
        boolean delFlag = true;

        if (SID.equalsIgnoreCase("ALL")) {
            db.execSQL("DELETE FROM " + TABLE_STUDENT);
            deleteStudentModule(SID);
        } else {
            if (isEnrolled(SID)) {
                delFlag = false;
            } else {
                db = getWritableDatabase();
                db.execSQL("DELETE FROM " + TABLE_STUDENT + " WHERE SID = '" + SID + "'");
                deleteStudentModule(SID);
            }
        }
        db.close();
        return delFlag;
    }

    //Delete student module
    public void deleteStudentModule(String SID) {
        SQLiteDatabase db = getWritableDatabase();

        if (SID.equalsIgnoreCase("ALL")) {
            db.execSQL("DELETE FROM " + TABLE_STUDENT_MODULE);
        } else {
            db.execSQL("DELETE FROM " + TABLE_STUDENT_MODULE + " WHERE SID = '" + SID + "'");
        }
        db.close();
    }

    //Search Student
    //Available search conditions: SID/SName/Date_Enrolled
    public List<Student> searchStudent(Student conditions) {
        //DB connection
        SQLiteDatabase db = getWritableDatabase();
        //Cursor point to a location in results
        Cursor c;
        //Search result list
        List<Student> studentList = new LinkedList<>();
        //Module list
        List<StudentModule> smList = new LinkedList<>();
        Student student;
        StudentModule sm;

        String query = "SELECT * FROM " + TABLE_STUDENT + " WHERE 1=1";
        if (conditions.getSID() != null) {
            query = query + " AND " + COLUMN_SID + " = '" + conditions.getSID() + "'";
        }
        if (conditions.getSName() != null) {
            query = query + " AND " + COLUMN_SName + " = '" + conditions.getSName() + "'";
        }
        if (conditions.getDate_Enrolled() != null) {
            query = query + " AND " + COLUMN_Date_Enrolled+ " != ''";
        }
        query = query + " ORDER BY " + COLUMN_SID;
        c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            //student information
            student = new Student();
            student.setSID(c.getString(c.getColumnIndex("SID")));
            student.setSName(c.getString(c.getColumnIndex("SName")));
            student.setEmail(c.getString(c.getColumnIndex("Email")));
            student.setSpecialisation(c.getString(c.getColumnIndex("Specialisation")));
            student.setProgramme(c.getString(c.getColumnIndex("Programme")));
            student.setDate_Enrolled(c.getString(c.getColumnIndex("Date_Enrolled")));
            student.setSImage(c.getString(c.getColumnIndex("SImage")));

            //student module information
            student.setModules(searchStudentModule(c.getString(c.getColumnIndex("SID"))));

            //set to result list
            studentList.add(student);
            c.moveToNext();
        }
        db.close();
        return studentList;
    }

    //Search StudentModule
    //Available search conditions: SID
    public List<StudentModule> searchStudentModule(String SID) {
        //DB connection
        SQLiteDatabase db = getWritableDatabase();
        //Cursor point to a location in results
        Cursor c;
        //Search result list
        List<StudentModule> smList = new LinkedList<>();
        StudentModule sm;

        String query = "SELECT * FROM " + TABLE_STUDENT_MODULE
                + " WHERE " + COLUMN_SID + " = '" + SID + "'"
                + " ORDER BY SID";
        c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            sm = new StudentModule(c.getString(c.getColumnIndex("SID")),
                    c.getString(c.getColumnIndex("MID")),
                    c.getString(c.getColumnIndex("Completion")));
            smList.add(sm);
            c.moveToNext();
        }

        db.close();
        return smList;
    }

    //Check duplication of student ID before insert
    public boolean isSIDDuplicate(String SID) {
        boolean returnValue = false;
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * "
                + " FROM " + TABLE_STUDENT
                + " WHERE " + COLUMN_SID + " = '" + SID + "'";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if (!c.isAfterLast()) { returnValue = true; }
        db.close();
        return returnValue;
    }

    //Check if the student is enrolled
    public boolean isEnrolled(String SID) {
        boolean returnValue = false;
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT " + COLUMN_Date_Enrolled
                + " FROM " + TABLE_STUDENT
                + " WHERE " + COLUMN_SID + " = '" + SID + "'";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            if (!c.getString(c.getColumnIndex("Date_Enrolled")).equals("") &&
                    !c.getString(c.getColumnIndex("Date_Enrolled")).equals("None")) { returnValue = true; }
        }
        db.close();
        return returnValue;
    }

    /////////////////////////Student method ends here////////////////////////////

    /////////////////////////Module method begins here////////////////////////////

    //Add a new module
    public boolean addModule(Module module) {
        //Duplicate check
        if (isMIDDuplicate(module.getMID())) {return false;}

        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();

        values = new ContentValues();
        values.put(COLUMN_MID, module.getMID());
        values.put(COLUMN_MName, module.getMName());
        values.put(COLUMN_Description, module.getDescription());
        values.put(COLUMN_Level, module.getLevel());
        values.put(COLUMN_PreMID_1, module.getPreMID_1());
        values.put(COLUMN_PreMID_2, module.getPreMID_2());
        values.put(COLUMN_PreMID_3, module.getPreMID_3());
        values.put(COLUMN_Pathway_1, module.getPathway_1());
        values.put(COLUMN_Pathway_2, module.getPathway_2());
        values.put(COLUMN_Pathway_3, module.getPathway_3());
        values.put(COLUMN_Classification, module.getClassification());
        values.put(COLUMN_Credits, module.getCredits());
        values.put(COLUMN_Year, module.getYear());
        values.put(COLUMN_Semester, module.getSemester());
        db.insert(TABLE_MODULE, null, values);

        db.close();
        return true;
    }

    //Update a module
    public void updateModule(Module module) {
        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = new String[1];
        String whereClause;

        values = new ContentValues();
        values.put(COLUMN_MName, module.getMName());
        values.put(COLUMN_Description, module.getDescription());
        values.put(COLUMN_Level, module.getLevel());
        values.put(COLUMN_PreMID_1, module.getPreMID_1());
        values.put(COLUMN_PreMID_2, module.getPreMID_2());
        values.put(COLUMN_PreMID_3, module.getPreMID_3());
        values.put(COLUMN_Pathway_1, module.getPathway_1());
        values.put(COLUMN_Pathway_2, module.getPathway_2());
        values.put(COLUMN_Pathway_3, module.getPathway_3());
        values.put(COLUMN_Classification, module.getClassification());
        values.put(COLUMN_Credits, module.getCredits());
        values.put(COLUMN_Year, module.getYear());
        values.put(COLUMN_Semester, module.getSemester());
        whereArgs[0] = module.getMID();
        whereClause = COLUMN_MID + "=?";
        db.update(TABLE_MODULE, values, whereClause, whereArgs);

        db.close();
    }

    //Delete a module
    public void deleteModule(String MID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MODULE + " WHERE " + COLUMN_MID + " = '" + MID +"'");
        db.execSQL("DELETE FROM " + TABLE_STUDENT_MODULE + " WHERE " + COLUMN_MID + " = '" + MID +"'");
        db.close();
    }

    //Search module
    //Available search conditions: MID/MName/Pathway/Year
    public List<Module> searchModule(Module conditions) {
        //DB connection
        SQLiteDatabase db = getWritableDatabase();
        //Cursor point to a location in results
        Cursor c;
        //Search result list
        List<Module> moduleList = new LinkedList<>();
        //Module instance
        Module module = new Module();

        //Get search results
        String query = "SELECT * FROM " + TABLE_MODULE + " WHERE 1=1";
        //Search by MID
        if (conditions.getMID() != null) {
            query = query + " AND " + COLUMN_MID + " = '" + conditions.getMID() + "'";
        }
        //Search by MName
        if (conditions.getMName() != null) {
            query = query + " AND " + COLUMN_MName + " = '" + conditions.getMName() + "'";
        }
        //Search by Pathway
        if (conditions.getPathway_1() != null) {
            query = query + " AND (" + COLUMN_Pathway_1 + " = '" + conditions.getPathway_1() + "'"
                    + " OR " + COLUMN_Pathway_2 + " = '" + conditions.getPathway_1() + "'"
                    + " OR " + COLUMN_Pathway_3 + " = '" + conditions.getPathway_1() + "'"
                    + " OR " + COLUMN_Pathway_1 + " = '')";
        }
        //Search by Year
        if (conditions.getYear() != null) {
            query = query + " AND " + COLUMN_Year + " = '" + conditions.getYear() + "'";
        }
        query = query + " ORDER BY " + COLUMN_Year + "," + COLUMN_Semester;
        c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            module = new Module();

            module.setMID(c.getString(c.getColumnIndex("MID")));
            module.setMName(c.getString(c.getColumnIndex("MName")));
            module.setDescription(c.getString(c.getColumnIndex("Description")));
            module.setLevel(c.getString(c.getColumnIndex("Level")));
            module.setPreMID_1(c.getString(c.getColumnIndex("PreMID_1")));
            module.setPreMID_2(c.getString(c.getColumnIndex("PreMID_2")));
            module.setPreMID_3(c.getString(c.getColumnIndex("PreMID_3")));
            module.setPathway_1(c.getString(c.getColumnIndex("Pathway_1")));
            module.setPathway_2(c.getString(c.getColumnIndex("Pathway_2")));
            module.setPathway_3(c.getString(c.getColumnIndex("Pathway_3")));
            module.setClassification(c.getString(c.getColumnIndex("Classification")));
            module.setCredits(c.getString(c.getColumnIndex("Credits")));
            module.setYear(c.getString(c.getColumnIndex("Year")));
            module.setSemester(c.getString(c.getColumnIndex("Semester")));

            moduleList.add(module);
            c.moveToNext();
        }

        db.close();
        return moduleList;
    }
    //Available search conditions: Pathway
    public List<Module> retrievePathway(String pathway) {
        //DB connection
        SQLiteDatabase db = getWritableDatabase();
        //Cursor point to a location in results
        Cursor c;
        //Search result list
        List<Module> moduleList = new LinkedList<>();
        //Module instance
        Module module = new Module();

        //Get search results
        String query = "SELECT * FROM " + TABLE_MODULE + " WHERE 1=1";

        //Search by Pathway
        if (pathway != null) {
            query = query + " AND (" + COLUMN_Pathway_1 + " = '" + pathway + "'"
                    + " OR " + COLUMN_Pathway_2 + " = '" + pathway + "'"
                    + " OR " + COLUMN_Pathway_3 + " = '" + pathway + "'"
                    + " OR " + COLUMN_Pathway_1 + " = '')";
        }
        query = query + " ORDER BY " + COLUMN_Year + "," + COLUMN_Semester;
        c = db.rawQuery(query,null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            module = new Module();

            module.setMID(c.getString(c.getColumnIndex("MID")));
            module.setMName(c.getString(c.getColumnIndex("MName")));
            module.setDescription(c.getString(c.getColumnIndex("Description")));
            module.setLevel(c.getString(c.getColumnIndex("Level")));
            module.setPreMID_1(c.getString(c.getColumnIndex("PreMID_1")));
            module.setPreMID_2(c.getString(c.getColumnIndex("PreMID_2")));
            module.setPreMID_3(c.getString(c.getColumnIndex("PreMID_3")));
            module.setPathway_1(c.getString(c.getColumnIndex("Pathway_1")));
            module.setPathway_2(c.getString(c.getColumnIndex("Pathway_2")));
            module.setPathway_3(c.getString(c.getColumnIndex("Pathway_3")));
            module.setClassification(c.getString(c.getColumnIndex("Classification")));
            module.setCredits(c.getString(c.getColumnIndex("Credits")));
            module.setYear(c.getString(c.getColumnIndex("Year")));
            module.setSemester(c.getString(c.getColumnIndex("Semester")));

            moduleList.add(module);
            c.moveToNext();
        }

        db.close();
        return moduleList;
    }

    //Check duplication of module ID
    public boolean isMIDDuplicate(String MID) {
        boolean returnValue = false;
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * "
                + " FROM " + TABLE_MODULE
                + " WHERE " + COLUMN_MID + " = '" + MID + "'";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if (!c.isAfterLast()) { returnValue = true; }
        db.close();
        return returnValue;
    }

    //Load Data
    public void loadData() {

        loadModule();
        loadStudent();

        //Student student = new Student();
        //List<Student> studentList = new LinkedList<>();
        //studentList = searchStudent(student);

        //Module module = new Module();
        //module.setYear("3");
        //List<Module> moduleList = searchModule(module);
        //List<Module> moduleList = retrievePathway("Database");
        //return moduleList.size();
    }

    //Load Student
    public void loadStudent() {
        Student student;
        List<StudentModule> moduleList;

        //Delete table
        deleteStudent("ALL");
        //Add Student
        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000000");
        student.setSName("Juan");
        student.setEmail("Juan@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("20170101");
        moduleList.add(new StudentModule("16000000","COMP501","Yes"));
        moduleList.add(new StudentModule("16000000","INFO601","Yes"));
        moduleList.add(new StudentModule("16000000","INFO704","Yes"));
        student.setModules(moduleList);
        addStudent(student);

        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000001");
        student.setSName("Nancy");
        student.setEmail("Nancy@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("20170102");
        moduleList.add(new StudentModule("16000001","COMP501","Yes"));
        moduleList.add(new StudentModule("16000001","INFO703","No"));
        student.setModules(moduleList);
        addStudent(student);

        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000002");
        student.setSName("Crisita");
        student.setEmail("Crisita@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("20170103");
        moduleList.add(new StudentModule("16000002","COMP502","Yes"));
        student.setModules(moduleList);
        addStudent(student);

        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000003");
        student.setSName("Venkate");
        student.setEmail("Venkate@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("20170104");
        moduleList.add(new StudentModule("16000003","COMP502","Yes"));
        student.setModules(moduleList);
        addStudent(student);

        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000004");
        student.setSName("Chris");
        student.setEmail("Chris@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("20170105");
        moduleList.add(new StudentModule("16000004","COMP502","Yes"));
        student.setModules(moduleList);
        addStudent(student);

        student = new Student();
        moduleList = new LinkedList<>();
        student.setSID("16000005");
        student.setSName("Chrissss");
        student.setEmail("Chrissss@student.wintec.nz.co");
        student.setSpecialisation("Software Engineering");
        student.setProgramme("Bachelor of Applied IT");
        student.setDate_Enrolled("None");
        moduleList.add(new StudentModule("16000005","COMP502","No"));
        student.setModules(moduleList);
        addStudent(student);
    }

    //Load module
    public void loadModule() {
        Module module;

        //Delete table
        deleteModule("");

        //Level 5: 8 modules
        module = new Module();
        module.setMID("COMP501");
        module.setMName("IT Operations");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("1");
        module.setDescription("To enable students to apply fundamental IT technical support concepts and practice, and configure and administer systems and applications to meet organisational requirements.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("COMP502");
        module.setMName("Fundamentals of Programming and Problem Solving");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("1");
        module.setDescription("To enable students to apply the principles of software development to create simple working applications and use problem-solving and decision-making techniques to provide innovative and timely Information Technology outcomes.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("INFO501");
        module.setMName("Professional Practice");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("1");
        module.setDescription("To enable students to apply professional, legal, and ethical principles and practices in a socially responsible manner as an emerging IT professional, and apply communication, personal and interpersonal skills to enhance effectiveness in an IT role.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("INFO502");
        module.setMName("Business Systems Analysis & Design");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("1");
        module.setDescription("The student will be able to apply the fundamentals of information systems concepts and practice to support and enhance organisational processes and systems; and apply the fundamentals of interaction design concepts and practice to enhance interface design.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("COMP503");
        module.setMName("Introduction to Networks (Cisco 1)");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("2");
        module.setDescription("To enable students to apply a broad operational knowledge of networking, and associated services and technologies to meet typical organisational requirements.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("COMP504");
        module.setMName("Operating Systems & Systems Support");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("2");
        module.setDescription("To enable students to select, install, and configure IT hardware and systems software and use graphical (GUI) and command line interfaces (CLI) to meet organisational requirements.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("INFO503");
        module.setMName("Database Principles");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("2");
        module.setDescription("To enable students to apply a broad operational knowledge of database administration to meet typical organisational data storage and retrieval requirements, and apply conceptual knowledge of cloud services and virtualisation.");
        module.setLevel("5");
        addModule(module);

        module = new Module();
        module.setMID("INFO504");
        module.setMName("Technical Support");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("1");
        module.setSemester("2");
        module.setDescription("To enable students to demonstrate an operational knowledge and understanding of IT service management, identify common issues related to IT security, and troubleshoot and resolve a range of common system problems.");
        module.setLevel("5");
        addModule(module);

        //Level 6: 13 modules
        module = new Module();
        module.setMID("COMP601");
        module.setMName("Object Oriented Programming");
        module.setPreMID_1("COMP502");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("3");
        module.setDescription("To enable students to gain the skills to develop software applications using Object Oriented Programming techniques. Students will enrich their programming and problem solving skills by applying classes, objects, constructors, methods and properties, inheritance and polymorphism to develop programming applications.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP602");
        module.setMName("Web Development");
        module.setPreMID_1("COMP502");
        module.setPreMID_2("INFO502");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("3");
        module.setDescription("To enable students to gain the in depth knowledge and skills required to be able to write programs in web programming languages that solve various web programming tasks.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("INFO601");
        module.setMName("Data-modelling and SQL");
        module.setPreMID_1("INFO503");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("3");
        module.setDescription("To enable students to apply an indepth understanding of database modelling, database design and SQL concepts.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("MATH601");
        module.setMName("Mathematics for IT");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("3");
        module.setDescription("To enable students to gain mathematical skills and acquire in-depth understanding of mathematics as applied to Information Technology.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP603");
        module.setMName("The Web Environment");
        module.setPreMID_1("COMP602");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to examine the working environment and applications that are used in the web industry.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("INFO602");
        module.setMName("Business, Interpersonal Communications & Technical Writing");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to develop an understanding of the principles and processes involved in effective interpersonal communication and technical writing used in managing client relationships.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP605");
        module.setMName("Data Structures and algorithms");
        module.setPreMID_1("COMP601");
        module.setPreMID_2("MATH601");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to apply programming and analytical skills to implement and analyze common data structures for computer programs. Students will cover a wide range of computer programming algorithms.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("MATH602");
        module.setMName("Mathematics for programming");
        module.setPreMID_1("MATH601");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to obtain the mathematical skills to facilitate an in-depth understanding of advanced programming techniques. Students will be able to solve problems in recurrence functions, asymptotic functions, algorithmic puzzles, combinatorics and graph theory and advanced topics in probability and statistics.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP606");
        module.setMName("Web Programming");
        module.setPreMID_1("COMP602");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("Multimedia and Web Development");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to gain the in depth knowledge and skills required to be able to write programs in web programming languages that solve various web programming tasks.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("INFO604");
        module.setMName("Database Systems");
        module.setPreMID_1("INFO503");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to understand and discuss database systems, concepts, modelling, design and administration, and to apply theoretical and practical administrative tasks in a database administrator's role.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("INFO603");
        module.setMName("Systems Administration");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to gain the skills and knowledge required to effectively plan, install and administer a Microsoft Windows Server.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP604");
        module.setMName("Routing and Switching Essentials");
        module.setPreMID_1("COMP503");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to configure, troubleshoot and understand the operation of Routers, Routing Protocols, Switches and VLANs in a networking environment, and complete the Cisco Certified Network Associate 2 (CCNA2) Curriculum.");
        module.setLevel("6");
        addModule(module);

        module = new Module();
        module.setMID("COMP607");
        module.setMName("Visual Effects and Animation");
        module.setPreMID_1("COMP602");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Multimedia and Web Development");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("2");
        module.setSemester("4");
        module.setDescription("To enable students to develop the knowledge and skills required to design and develop effective graphics and animation, and to apply various visual effects for static graphics as well as create 3D models and produce 2D and 3D animation.");
        module.setLevel("6");
        addModule(module);

        //Level 7: 21 modules
        module = new Module();
        module.setMID("INFO701");
        module.setMName("Project Management (Prince 2)");
        module.setPreMID_1("INFO502");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to understand and apply the theory of project management with particular emphasis on Information Technology projects.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("BIZM701");
        module.setMName("Business Essentials for IT Professionals");
        module.setPreMID_1("INFO602");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Mandatory");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to develop an understanding of the common principles of business practice whilst focussing on the theoretical and practical concepts of accounting, marketing and management in order to understand the business context within which Information Technology solutions are developed.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP706");
        module.setMName("Games Development");
        module.setPreMID_1("COMP601");
        module.setPreMID_2("COMP605");
        module.setPreMID_3("MATH602");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("Multimedia and Web Development");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to understand supporting theories and principles of game design and apply these to the art and science of game design, development and programming.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO703");
        module.setMName("Big Data and Analytics");
        module.setPreMID_1("COMP605");
        module.setPreMID_2("MATH602");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to gain the practical knowledge and skills required to store, manage and analyse large amounts of data, using appropriate algorithms.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO706");
        module.setMName("Database Front-End Applications");
        module.setPreMID_1("INFO601");
        module.setPreMID_2("INFO604");
        module.setPreMID_3("");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to understand and apply various front end applications and their interfaces with supporting databases.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO707");
        module.setMName("Cloud Server Databases");
        module.setPreMID_1("INFO601");
        module.setPreMID_2("INFO604");
        module.setPreMID_3("");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to gain an in-depth knowledge of cloud server database concepts, fundamentals and essentials. They will apply practical skills to install, setup, configure, manage and maintain a cloud server database and deploy cloud database services to support database applications.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP702");
        module.setMName("Scaling Networks");
        module.setPreMID_1("COMP604");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to gain an understanding of the architecture, components, and operations of routers and switches in larger and more complex networks.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP704");
        module.setMName("Network Security");
        module.setPreMID_1("COMP604");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to understand and configure the components, and operation of Virtual Private Networks, firewalls and network security, which will enable them to complete the Cisco Certified Network Associate Security curriculum.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO708");
        module.setMName("Data Visualisation");
        module.setPreMID_1("COMP606");
        module.setPreMID_2("COMP607");
        module.setPreMID_3("");
        module.setPathway_1("Multimedia and Web Development");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("5");
        module.setDescription("To enable students to study and apply visual techniques that transform data into a format efficient for human perception, cognition, and communication, thus allowing for extraction of meaningful information and insight. Students will investigate data visualisation techniques, human visual systems and cognitive perception, and design, construct, and evaluate data visualisations.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP707");
        module.setMName("Principles of Software Testing");
        module.setPreMID_1("COMP605");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("Students will gain comprehensive knowledge of software testing methodologies and software testing tools used in industry and apply fundamental aspects of software testing incorporating system requirements, quality assurance, testing processes, automation, testing types and testing levels. This forms the third part of the Software Engineering Capstone Project.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP715 ");
        module.setMName("Software Engineering Project ");
        module.setPreMID_1("COMP605");
        module.setPreMID_2("MATH602");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("Students will gain advanced software development skills. They will be able to provide an in depth analysis of prototyping, performance, correctness, software reusability, scalability and quality and maintenance and appropriate documentation. This module is the Software Engineering capstone project.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP709");
        module.setMName("Mobile Apps Development");
        module.setPreMID_1("COMP601");
        module.setPreMID_2("COMP605");
        module.setPreMID_3("MATH602");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("Database Architecture");
        module.setPathway_3("Multimedia and Web Development");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to design, develop and implement mobile applications on a given platform.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO704");
        module.setMName("Data-Warehousing and Business Intelligence");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Software Engineering");
        module.setPathway_2("Database Architecture");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to examine the main components of data warehousing and apply it to business intelligence applications, enabling them to provide solutions which incorporate extracting data from different sources, storing data in a data warehouse and developing applications for business decision-making.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP710");
        module.setMName("Web Apps Development");
        module.setPreMID_1("COMP601");
        module.setPreMID_2("COMP605");
        module.setPreMID_3("MATH602");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to apply practical knowledge of Model View Controller (MVC) frameworks to plan, design and implement web applications. The core focus will be on architecture design and implementation of a web application that will meet a set of functional requirements and user interface requirements, and address business models.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP607");
        module.setMName("Database Architecture Project");
        module.setPreMID_1("INFO601");
        module.setPreMID_2("INFO604");
        module.setPreMID_3("");
        module.setPathway_1("Database Architecture");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to develop the knowledge and skills required to design and develop effective graphics and animation, and to apply various visual effects for static graphics as well as create 3D models and produce 2D and 3D animation.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP701");
        module.setMName("Advanced Networking");
        module.setPreMID_1("INFO603");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to investigate and configure advanced system administration tools, advanced network virtualisation and wireless networking technologies. Students will also research emerging networking technologies.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP714");
        module.setMName("Network Engineering Project");
        module.setPreMID_1("COMP701");
        module.setPreMID_2("COMP702 ");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to develop a Computer networking solution from a set of requirements documents. This module is the Network Engineering Capstone Project.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("COMP705");
        module.setMName("Connecting Networks");
        module.setPreMID_1("COMP702");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to gain an understanding of Wide Area Network (WAN) technologies and network services required by converged applications in a complex network.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO702");
        module.setMName("Cyber-Security");
        module.setPreMID_1("COMP504");
        module.setPreMID_2("COMP601");
        module.setPreMID_3("");
        module.setPathway_1("Network Engineering");
        module.setPathway_2("Multimedia and Web Development");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to investigate computer system attacks and vulnerabilities in relation to operating systems (OS), applications, networking and websites, and investigate the security techniques for protecting a computer system from such attacks.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO709");
        module.setMName("Human Computer Interaction");
        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");
        module.setPathway_1("Multimedia and Web Development");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to understand the supporting theories and principles of user interface design with respect to human computer interaction. They will investigate applications in human computer interaction and apply usability best practices and processes.");
        module.setLevel("7");
        addModule(module);

        module = new Module();
        module.setMID("INFO708");
        module.setMName("Web Development Project");
        module.setPreMID_1("COMP606");
        module.setPreMID_2("COMP607");
        module.setPreMID_3("");
        module.setPathway_1("Multimedia and Web Development");
        module.setPathway_2("");
        module.setPathway_3("");
        module.setClassification("Optional");
        module.setCredits("15");
        module.setYear("3");
        module.setSemester("6");
        module.setDescription("To enable students to study and apply visual techniques that transform data into a format efficient for human perception, cognition, and communication, thus allowing for extraction of meaningful information and insight. Students will investigate data visualisation techniques, human visual systems and cognitive perception, and design, construct, and evaluate data visualisations.");
        module.setLevel("7");
        addModule(module);
    }
}

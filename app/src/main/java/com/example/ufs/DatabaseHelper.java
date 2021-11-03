package com.example.ufs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ufs.data.model.StudentModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constants for student / customer table
    public static final String STUDENT_TABLE = "STUDENT_TABLE";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_UNIVERSITY_ID = "university_id";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "ufs.db", null, 1);
    }

    // will be called the first time db is accessed
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create tables here
        String createStudentTable = "CREATE TABLE " + STUDENT_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT NOT NULL, " +
                COLUMN_STUDENT_UNIVERSITY_ID + " TEXT NOT NULL" +
        ")";

        sqLiteDatabase.execSQL(createStudentTable);
    }

    // will be called  if the DB version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean addStudent(StudentModel studentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_STUDENT_NAME, studentModel.getName());
        cv.put(COLUMN_STUDENT_UNIVERSITY_ID, studentModel.getUniversityID());

        // commit data to DB
        long insert_status = db.insert(STUDENT_TABLE, null, cv);

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }
}

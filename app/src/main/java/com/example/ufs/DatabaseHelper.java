package com.example.ufs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ufs.data.model.UserModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constants for student / customer table
//    public static final String STUDENT_TABLE = "STUDENT_TABLE";
//    public static final String COLUMN_STUDENT_NAME = "name";
//    public static final String COLUMN_STUDENT_UNIVERSITY_ID = "university_id";

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_UNIVERSITY_ID = "university_id";
    public static final String COLUMN_USER_IS_STUDENT = "is_student";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "ufs.db", null, 1);
    }

    // will be called the first time db is accesse
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create tables here
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT NOT NULL, " +
            COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
            COLUMN_USER_UNIVERSITY_ID + " TEXT, " +
            COLUMN_USER_IS_STUDENT + " INTEGER NOT NULL" + // sqlite does not have boolean
        ")";

        // TODO: create tables
        //  Restaurant
        //  Order
        //  Rating
        //  MenuItem
        //  Favorite
        //  Advertisement

//        sqLiteDatabase.execSQL(createStudentTable);
        sqLiteDatabase.execSQL(createUserTable);
    }

    // will be called  if the DB version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop student table
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_UNIVERSITY_ID, userModel.getUniversityID());
        cv.put(COLUMN_USER_IS_STUDENT, userModel.getIsStudent());

        // commit data to DB
        long insert_status = db.insert(USER_TABLE, null, cv);

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }



    // TODO: add more methods
    //  addOrder(), getOrder(), editOrder()
    //  addRestaurant(), getRestaurant()
    //  addRating(), getRating()
    //  addMenuItem(), getMenuItem()
    //  addAdvertisement(), getAdvertisement()
}

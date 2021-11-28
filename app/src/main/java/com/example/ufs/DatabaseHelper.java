package com.example.ufs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ufs.data.model.AdvertisementModel;
import com.example.ufs.data.model.FavoriteMenuItemModel;
import com.example.ufs.data.model.FavoriteRestaurantModel;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.data.model.OrderModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.data.model.ReviewModel;
import com.example.ufs.data.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import kotlin.NotImplementedError;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "==== DatabaseHelper";

    // Constants for user table
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_UNIVERSITY_ID = "university_id";
    public static final String COLUMN_USER_IS_STUDENT = "is_student";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Order Table
    public static final String ORDER_TABLE = "ORDER_TABLE";
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_USER_ID = "user_id";
    public static final String COLUMN_ORDER_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
    public static final String COLUMN_ORDER_PAYMENT_OPTION = "payment_option";
    public static final String COLUMN_ORDER_IS_DELIVERED = "is_delivered";
    public static final String COLUMN_ORDER_IS_PICKUP = "is_pickup";
    public static final String COLUMN_ORDER_TIMESTAMP = "timestamp";
    public static final String COLUMN_ORDER_ADDRESS = "address";

    // Restaurant Table
    private static final String RESTAURANT_TABLE = "RESTAURANT_TABLE";
    private static final String COLUMN_RESTAURANT_ID = "id";
    private static final String COLUMN_RESTAURANT_NAME = "name";
    private static final String COLUMN_RESTAURANT_LOCATION = "location";
    private static final String COLUMN_RESTAURANT_USER_ID = "user_id";

    // Menu Item
    private static final String MENU_ITEM_TABLE = "MENU_ITEM_TABLE";
    private static final String COLUMN_MENU_ITEM_ID = "id";
    private static final String COLUMN_MENU_ITEM_NAME = "name";
    private static final String COLUMN_MENU_ITEM_PRICE = "price";
    private static final String COLUMN_MENU_ITEM_RESTAURANT_ID = "restaurant_id";

    // Favorite Restaurant
    private static final String FAVORITE_RESTAURANT_TABLE = "FAVORITE_RESTAURANT_TABLE";
    private static final String COLUMN_FAVORITE_RESTAURANT_ID = "id";
    private static final String COLUMN_FAVORITE_RESTAURANT_USER_ID = "user_id";
    private static final String COLUMN_FAVORITE_RESTAURANT_RESTAURANT_ID = "restaurant_id";

    // Favorite Menu Item
    private static final String FAVORITE_MENU_ITEM_TABLE = "FAVORITE_MENU_ITEM_TABLE";
    private static final String COLUMN_FAVORITE_MENU_ITEM_ID = "id";
    private static final String COLUMN_FAVORITE_MENU_ITEM_USER_ID = "user_id";
    private static final String COLUMN_FAVORITE_MENU_ITEM_MENU_ITEM_ID = "menu_item_id";

    // Review
    private static final String REVIEW_TABLE = "REVIEW_TABLE";
    private static final String COLUMN_REVIEW_ID = "id";
    private static final String COLUMN_REVIEW_RATING = "rating";
    private static final String COLUMN_REVIEW_REVIEW = "review";
    private static final String COLUMN_REVIEW_USER_ID = "user_id";
    private static final String COLUMN_REVIEW_RESTAURANT_ID = "restaurant_id";

    // Advertisement
    private static final String AD_TABLE = "ADVERTISEMENT_TABLE";
    private static final String COLUMN_AD_ID = "id";
    private static final String COLUMN_AD_COMPANY_NAME = "company_name";
    private static final String COLUMN_AD_MESSAGE = "message";
    private static final String COLUMN_AD_USER_ID = "user_id";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "ufs.db", null, 1);
    }

    // will be called the first time db is accessed. this is where
    // the database tables are created
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create tables here

        // =============== USER TABLE
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_USER_LAST_NAME + " TEXT NOT NULL, " +
            COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
            COLUMN_USER_UNIVERSITY_ID + " TEXT, " +
            COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_USER_IS_STUDENT + " INTEGER NOT NULL" + // sqlite does not have boolean
        ")";

        // =============== ORDER TABLE
        String createOrderTable = "CREATE TABLE " + ORDER_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ORDER_TOTAL_PRICE + " REAL NOT NULL, " +
            COLUMN_ORDER_IS_DELIVERED + " INTEGER NOT NULL, " +
            COLUMN_ORDER_IS_PICKUP + " INTEGER NOT NULL, " + // sqlite does not have boolean
            // 1 - Meal Plan
            // 2 - Credit Card
            // 3 - Cash
            COLUMN_ORDER_PAYMENT_OPTION + " INTEGER NOT NULL, " +
            COLUMN_ORDER_TIMESTAMP + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
            COLUMN_ORDER_ADDRESS + " TEXT, " +
            // Foreign keys
            COLUMN_ORDER_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_ORDER_RESTAURANT_ID + " INTEGER NOT NULL, " +

            // Foreign key relating order to user and restaurant
            "FOREIGN KEY(restaurant_id) REFERENCES " + RESTAURANT_TABLE + "(id), " +
            "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(id)" +
        ")";

        // =============== RESTAURANT TABLE
        String createRestaurantTable = "CREATE TABLE " + RESTAURANT_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RESTAURANT_LOCATION + " TEXT NOT NULL, " +
            COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, " +
            COLUMN_RESTAURANT_USER_ID + " INTEGER NOT NULL, " +

            // Foreign key relating user and restaurant
            "FOREIGN KEY(id) REFERENCES " + USER_TABLE + "(id)" +
        ")";

        // =============== MENU_ITEM TABLE
        String createMenuItemTable = "CREATE TABLE " + MENU_ITEM_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MENU_ITEM_NAME + " TEXT NOT NULL, " +
            COLUMN_MENU_ITEM_PRICE + " REAL NOT NULL, " +
            COLUMN_MENU_ITEM_RESTAURANT_ID + " INTEGER NOT NULL, " +

            // Foreign key relating menu_item and restaurant
            "FOREIGN KEY(id) REFERENCES " + RESTAURANT_TABLE + "(id)" +
        ")";


        // =============== REVIEW TABLE
        String createReviewTable = "CREATE TABLE " + REVIEW_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_REVIEW_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_REVIEW_RESTAURANT_ID + " INTEGER NOT NULL, " +

            COLUMN_REVIEW_RATING + " INTEGER NOT NULL, " +
            COLUMN_REVIEW_REVIEW + " TEXT NOT NULL, " +

            // Foreign key mapping user and reviewed restaurant
            "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(id), " +
            "FOREIGN KEY(restaurant_id) REFERENCES " + RESTAURANT_TABLE + "(id)" +
        ")";


        // =============== FAVORITE RESTAURANT
        String createFavoriteRestaurantTable = "CREATE TABLE " + FAVORITE_RESTAURANT_TABLE + " (" +
            // may not need this ID col
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FAVORITE_RESTAURANT_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_FAVORITE_RESTAURANT_RESTAURANT_ID + " INTEGER NOT NULL, " +

            // Foreign key mapping user to favorite restaurant
            "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(id), " +
            "FOREIGN KEY(restaurant_id) REFERENCES " + RESTAURANT_TABLE + "(id)" +
        ")";
//
        // =============== FAVORITE MENU ITEM
        String createFavoriteMenuItemTable = "CREATE TABLE " + FAVORITE_MENU_ITEM_TABLE + " (" +
            // may not need this ID col
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FAVORITE_MENU_ITEM_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_FAVORITE_MENU_ITEM_MENU_ITEM_ID + " INTEGER NOT NULL, " +

            // Foreign key relating menu_item and restaurant
            "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(id), " +
            "FOREIGN KEY(menu_item_id) REFERENCES " + MENU_ITEM_TABLE + "(id)" +
        ")";

        String createAdvertisementTable = "CREATE TABLE " + AD_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AD_COMPANY_NAME + " TEXT NOT NULL, " +
                COLUMN_AD_MESSAGE + " TEXT NOT NULL, " +
                COLUMN_AD_USER_ID + " INTEGER NOT NULL, " +

                // Foreign key relating menu_item and restaurant
                "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(id)" +
                ")";

        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createOrderTable);
        sqLiteDatabase.execSQL(createRestaurantTable);
        sqLiteDatabase.execSQL(createMenuItemTable);

        sqLiteDatabase.execSQL(createReviewTable);

        sqLiteDatabase.execSQL(createFavoriteRestaurantTable);
        sqLiteDatabase.execSQL(createFavoriteMenuItemTable);

        sqLiteDatabase.execSQL(createAdvertisementTable);
    }

    // will be called  if the DB version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MENU_ITEM_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVORITE_RESTAURANT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVORITE_MENU_ITEM_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AD_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_USER_FIRST_NAME, userModel.getFirstName());
        cv.put(COLUMN_USER_LAST_NAME, userModel.getLastName());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_UNIVERSITY_ID, userModel.getUniversityID());
        cv.put(COLUMN_USER_IS_STUDENT, userModel.getIsStudent());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());

        // commit data to DB
        long insert_status = db.insert(USER_TABLE, null, cv);
        db.close();

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }

    public UserModel getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Columns to return to client
        //String[] returned_cols = {
        //    COLUMN_USER_ID,
        //    COLUMN_USER_FIRST_NAME,
        //    COLUMN_USER_LAST_NAME,
        //    COLUMN_USER_EMAIL,
        //    COLUMN_USER_UNIVERSITY_ID,
        //    COLUMN_USER_IS_STUDENT
        //};

        //String selection = COLUMN_USER_EMAIL + " = ?";

        // Condition of rows to select
        String[] selectionArgs = { email + "", password + "" };

        // User Data
        String userFName = "";
        String userLName = "";
        String userEmail = "";
        int userID = -1;
        boolean userIsStudent;

        try {
            // get data from db
            //Cursor query = db.query(returned_cols, selection, selectionArgs, null, null, null);
            cursor = db.rawQuery(
                "SELECT " + COLUMN_USER_FIRST_NAME + ", " +
                        COLUMN_USER_ID + ", " +
                        COLUMN_USER_LAST_NAME + ", " +
                        COLUMN_USER_EMAIL + ", " +
                        COLUMN_USER_IS_STUDENT +
                    " FROM " + USER_TABLE +
                    " WHERE email = ? AND password = ?",
                selectionArgs
            );

            // If user is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userFName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME));
                userLName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME));
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
                userID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                userIsStudent = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_STUDENT)) == 1;
            } else {
                // if no user is found
                return null;
            }

            return new UserModel(userID, userFName, userLName, userEmail, userIsStudent);
        } finally {
            assert cursor != null;
            cursor.close();
        }
    }

    // =============== RESTAURANTS:
    public boolean addRestaurant(RestaurantModel restaurantModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_RESTAURANT_NAME, restaurantModel.getName());
        cv.put(COLUMN_RESTAURANT_LOCATION, restaurantModel.getLocation());
        cv.put(COLUMN_FAVORITE_MENU_ITEM_USER_ID, restaurantModel.getUserID());

        // commit data to DB
        long insert_status = db.insert(RESTAURANT_TABLE, null, cv);
        db.close();

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }

    // Will get restaurant belonging to user. Returns null if user does not own restaurant
    public RestaurantModel getRestaurantByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selectionArgs = { userId + "" };
        String name, location;

        String queryString = "SELECT * FROM " + RESTAURANT_TABLE +
                " WHERE user_id = ?";

        try {
            // get data from db
            //Cursor query = db.query(returned_cols, selection, selectionArgs, null, null, null);
            cursor = db.rawQuery( queryString, selectionArgs );

            // If restaurant is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_NAME));
                location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_LOCATION));
            } else {
                // if no restaurant is found
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }
        db.close();

        return new RestaurantModel(name, location, userId);
    }

    // TODO - test
    public RestaurantModel getRestaurantById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selectionArgs = { id + "" };
        String name, location;
        int userId;

        String queryString = "SELECT * FROM " + RESTAURANT_TABLE +
                " WHERE id = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, selectionArgs );

            // If restaurant is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_NAME));
                location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_LOCATION));
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_USER_ID));
            } else {
                // if no restaurant is found
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }
        db.close();

        return new RestaurantModel(name, location, userId);
    }

    // TODO - test
    public RestaurantModel getRestaurantByName(String searchName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String name, location;
        int userId;

        String queryString = "SELECT * FROM " + RESTAURANT_TABLE +
                " WHERE name = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, new String[] {searchName} );

            // If restaurant is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_NAME));
                location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_LOCATION));
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_USER_ID));
            } else {
                // if no restaurant is found
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }
        db.close();

        return new RestaurantModel(name, location, userId);
    }

    // TODO - test
    public List<RestaurantModel> getAllRestaurants() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String name, location;
        int userId;
        List<RestaurantModel> restaurants = new ArrayList<>();

        String queryString = "SELECT * FROM " + RESTAURANT_TABLE;

        try {
            // get data from db
            cursor = db.rawQuery(queryString, null);

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_NAME));
                    location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_LOCATION));
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_USER_ID));

                    RestaurantModel restaurant = new RestaurantModel(name, location, userId);
                    restaurants.add(restaurant);
                    Log.i(TAG, restaurant.toString());

                } while(cursor.moveToNext());
            } else {
                // if no restaurant is found
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        db.close();

        return restaurants;
    }

    // TODO - test
    public boolean removeRestaurant(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // commit data to DB
        long insert_status = db.delete(USER_TABLE, "WHERE id = ?", new String[]{id + ""});

        // if positive then deletion was successful
        // if negative then deletion was a failure
        return insert_status > 0;
    }

    // TODO - test
    public boolean editRestaurant(int id, String newName, String newLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_RESTAURANT_NAME, newName);
        values.put(COLUMN_RESTAURANT_LOCATION, newLocation);

        //int status = db.rawQuery(queryString, selectionArgs);
        long status = db.update( RESTAURANT_TABLE, values, "id=?", new String[]{Integer.toString(id)});
        return status > 0;
    }

    // =============== ORDERS
    //  TODO:
    //    student orders - addOrder(), getStudentOrder(id), getAllStudentOrders(), editOrder(id),
    //    restaurant orders - getAllRestaurantOrders(), getRestaurantOrder(id)
    // TODO: differentiate between an order a user made
    //  and an order a restaurant received

    // ---- Student orders
    // TODO - test
    public boolean addOrder(OrderModel orderModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_ORDER_TOTAL_PRICE, orderModel.getTotalPrice());
        cv.put(COLUMN_ORDER_IS_DELIVERED, orderModel.getIsDelivered());
        cv.put(COLUMN_USER_EMAIL, orderModel.getIsPickup());
        cv.put(COLUMN_USER_UNIVERSITY_ID, orderModel.getPaymentOption());
        cv.put(COLUMN_USER_IS_STUDENT, orderModel.getTimestamp());
        cv.put(COLUMN_USER_PASSWORD, orderModel.getAddress());
        cv.put(COLUMN_USER_PASSWORD, orderModel.getUserID());
        cv.put(COLUMN_USER_PASSWORD, orderModel.getRestaurantID());

        // commit data to DB
        long insert_status = db.insert(ORDER_TABLE, null, cv);

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }

    // TODO - test
    // get specific order a student has made
    public OrderModel getStudentOrderById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selectionArgs = { id + "" };

        float totalPrice;
        boolean isDelivered, isPickup;
        String address;
        int paymentOption, userId, restaurantID;
        String timestamp;

        String queryString = "SELECT * FROM " + ORDER_TABLE +
                " WHERE id = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, selectionArgs );

            // If order is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                totalPrice = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                isDelivered = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_DELIVERED)) == 1;
                isPickup = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_PICKUP)) == 1;
                address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ADDRESS));
                paymentOption = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PAYMENT_OPTION));
                restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_RESTAURANT_ID));
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_USER_ID));
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIMESTAMP));
            } else {
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        OrderModel fetchedOrder = new OrderModel(totalPrice, isDelivered, isPickup, address, paymentOption, restaurantID, userId);
        fetchedOrder.setTimestamp(timestamp);
        return fetchedOrder;
    }

    // TODO - test
    // get all orders that a student made
    public List<OrderModel> getAllStudentOrders(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selectionArgs = { studentId + "" };

        float totalPrice;
        boolean isDelivered, isPickup;
        String address;
        int paymentOption, userId, restaurantID;
        String timestamp;
        List<OrderModel> studentOrders = new ArrayList<>();

        String queryString = "SELECT * FROM " + ORDER_TABLE + " WHERE user_id = ?";

        try {
            // get data from db
            cursor = db.rawQuery(queryString, selectionArgs);

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    totalPrice = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                    isDelivered = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_DELIVERED)) == 1;
                    isPickup = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_PICKUP)) == 1;
                    address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ADDRESS));
                    paymentOption = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PAYMENT_OPTION));
                    restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_RESTAURANT_ID));
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_USER_ID));
                    timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIMESTAMP));

                    OrderModel fetchedOrder = new OrderModel(totalPrice, isDelivered, isPickup, address, paymentOption, restaurantID, userId);
                    fetchedOrder.setTimestamp(timestamp);
                    studentOrders.add(fetchedOrder);

                } while(cursor.moveToNext());
            } else {
                // if student has no orders
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return studentOrders;
    }

    // TODO - test
    public boolean editOrder(
        int id,
        float totalPrice,
        boolean isDelivered,
        boolean isPickup,
        String address,
        int paymentOption,
        int restaurantID,
        int userID
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(COLUMN_ORDER_IS_DELIVERED, isDelivered);
        values.put(COLUMN_ORDER_IS_PICKUP, isPickup);
        values.put(COLUMN_ORDER_ADDRESS, address);
        values.put(COLUMN_ORDER_PAYMENT_OPTION, paymentOption);
        values.put(COLUMN_ORDER_RESTAURANT_ID, restaurantID);
        values.put(COLUMN_ORDER_USER_ID, userID);

        long status = db.update( ORDER_TABLE, values, "id=?", new String[]{Integer.toString(id)});
        return status > 0;
    }

    // ---- Restaurant orders

    // TODO - test
    // get all orders a restaurant has received
    public List<OrderModel> getAllRestaurantOrders(int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selectionArgs = { Integer.toString(restaurantId) };

        float totalPrice;
        boolean isDelivered, isPickup;
        String address;
        int paymentOption, userId, restaurantID;
        String timestamp;
        List<OrderModel> restaurantOrders = new ArrayList<>();

        String queryString = "SELECT * FROM " + ORDER_TABLE + " WHERE restaurant_id = ?";

        try {
            // get data from db
            cursor = db.rawQuery(queryString, selectionArgs);

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    totalPrice = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                    isDelivered = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_DELIVERED)) == 1;
                    isPickup = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_IS_PICKUP)) == 1;
                    address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ADDRESS));
                    paymentOption = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_PAYMENT_OPTION));
                    restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_RESTAURANT_ID));
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_USER_ID));
                    timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIMESTAMP));

                    OrderModel fetchedOrder = new OrderModel(totalPrice, isDelivered, isPickup, address, paymentOption, restaurantID, userId);
                    fetchedOrder.setTimestamp(timestamp);
                    restaurantOrders.add(fetchedOrder);

                } while(cursor.moveToNext());
            } else {
                // if student has no orders
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return restaurantOrders;
    }

    // get specific order restaurant has received
    //public OrderModel getRestaurantOrderById(int id) { return null; }

    // TODO - test
    // a restaurant can only change the isDelivered status
    public boolean editRestaurantOrder(int id, boolean isDelivered) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(COLUMN_ORDER_IS_DELIVERED, isDelivered);
        //values.put(COLUMN_ORDER_IS_PICKUP, isPickup);
        //values.put(COLUMN_ORDER_ADDRESS, address);
        // values.put(COLUMN_ORDER_PAYMENT_OPTION, paymentOption);
        // values.put(COLUMN_ORDER_RESTAURANT_ID, restaurantID);
        // values.put(COLUMN_ORDER_USER_ID, userID);

        long status = db.update( ORDER_TABLE, values, "id=?", new String[]{Integer.toString(id)});
        return status > 0;
    }



    // ================= MENU ITEMS
    //  TODO:
    public boolean addMenuItem(MenuItemModel menuItemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_MENU_ITEM_NAME, menuItemModel.getName());
        cv.put(COLUMN_MENU_ITEM_PRICE, menuItemModel.getPrice());
        cv.put(COLUMN_MENU_ITEM_RESTAURANT_ID, menuItemModel.getRestaurantId());

        // commit data to DB
        long status = db.insert(MENU_ITEM_TABLE, null, cv);
        return status > 0;
    }

    public MenuItemModel getMenuItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String name;
        float price;
        int restaurantID;

        String queryString = "SELECT * FROM " + MENU_ITEM_TABLE +
                " WHERE id = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, new String[] {Integer.toString(id)} );

            // If menu item is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_NAME));
                price = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_PRICE));
                restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_RESTAURANT_ID));
            } else {
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return new MenuItemModel(name, price, restaurantID);
    }

    public MenuItemModel getMenuItemByName(String searchName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String name;
        float price;
        int restaurantID;

        String queryString = "SELECT * FROM " + MENU_ITEM_TABLE +
                " WHERE name = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, new String[] { searchName } );

            // If menu item is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_NAME));
                price = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_PRICE));
                restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_RESTAURANT_ID));
            } else {
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return new MenuItemModel(name, price, restaurantID);
    }

    public List<MenuItemModel> getAllRestaurantMenuItems(int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String name;
        float price;
        int restaurantID;
        List<MenuItemModel> restaurantMenuItems = new ArrayList<>();

        String queryString = "SELECT * FROM " + MENU_ITEM_TABLE + " WHERE restaurant_id = ?";

        try {
            // get data from db
            cursor = db.rawQuery(queryString, new String[] {Integer.toString(restaurantId)});

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_NAME));
                    price = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_PRICE));
                    restaurantID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM_RESTAURANT_ID));

                    MenuItemModel fetchedMenuItem = new MenuItemModel(name, price, restaurantID);
                    restaurantMenuItems.add(fetchedMenuItem);

                } while(cursor.moveToNext());
            } else {
                // if student has no orders
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return restaurantMenuItems;
    }

    public boolean editMenuItem(int id, String newName, String newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MENU_ITEM_NAME, newName);
        values.put(COLUMN_MENU_ITEM_PRICE, newPrice);

        //int status = db.rawQuery(queryString, selectionArgs);
        long status = db.update( MENU_ITEM_TABLE, values, "id=?", new String[]{Integer.toString(id)});
        return status > 0;
    }

    public boolean removeMenuItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long insert_status = db.delete(MENU_ITEM_TABLE, "WHERE id = ?", new String[]{ Integer.toString(id) });
        return insert_status > 0;
    }

    // ================= REVIEWS

    // TODO - test
    public boolean addReview(ReviewModel reviewModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // add values to table
        cv.put(COLUMN_REVIEW_RATING, reviewModel.getRating());
        cv.put(COLUMN_REVIEW_REVIEW, reviewModel.getMessage());
        cv.put(COLUMN_REVIEW_USER_ID, reviewModel.getUserId());
        cv.put(COLUMN_REVIEW_RESTAURANT_ID, reviewModel.getRestaurantId());

        // commit data to DB
        long status = db.insert(REVIEW_TABLE, null, cv);
        return status > 0;
    }

    // TODO - test
    public ReviewModel getReviewById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        int rating ;
        String message;
        int userId, restaurantId;

        String queryString = "SELECT * FROM " + REVIEW_TABLE +
                " WHERE id = ?";

        try {
            // get data from db
            cursor = db.rawQuery( queryString, new String[] {Integer.toString(id)} );

            // If menu item is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                rating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING));
                message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_REVIEW));
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_USER_ID));
                restaurantId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RESTAURANT_ID));
            } else {
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return new ReviewModel(rating, message, userId, restaurantId);
    }

    // TODO
    public List<ReviewModel> getAllStudentReviews(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        int rating ;
        String message;
        int restaurantId;
        List<ReviewModel> studentReviews = new ArrayList<>();

        String queryString = "SELECT * FROM " + REVIEW_TABLE + " WHERE userId = ?";

        try {
            // get data from db
            cursor = db.rawQuery(queryString, new String[] {Integer.toString(userId)});

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    rating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING));
                    message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_REVIEW));
                    //userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_USER_ID));
                    restaurantId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RESTAURANT_ID));

                    ReviewModel fetchedReview = new ReviewModel(rating, message, userId, restaurantId);
                    studentReviews.add(fetchedReview);

                } while(cursor.moveToNext());
            } else {
                // if student has no orders
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return studentReviews;
    }

    public List<ReviewModel> getAllRestaurantReviews(int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        int rating ;
        String message;
        int userId;
        List<ReviewModel> restaurantReviews = new ArrayList<>();

        String queryString = "SELECT * FROM " + REVIEW_TABLE + " WHERE restaurantId = ?";

        try {
            // get data from db
            cursor = db.rawQuery(queryString, new String[] {Integer.toString(restaurantId)});

            // If restaurant is found
            //if(cursor.getCount() > 0)
            if(cursor.moveToFirst()) {
                //cursor.moveToFirst();
                do{
                    rating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING));
                    message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_REVIEW));
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_USER_ID));
                    //restaurantId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RESTAURANT_ID));

                    ReviewModel fetchedReview = new ReviewModel(rating, message, userId, restaurantId);
                    restaurantReviews.add(fetchedReview);

                } while(cursor.moveToNext());
            } else {
                // if student has no orders
                return null;
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }

        return restaurantReviews;
    }

    // ================= ADVERTISEMENTS
    // TODO
    public AdvertisementModel addAdvertisement(AdvertisementModel advertisementModel) {
        throw new NotImplementedError("addAdvertisement is not implemented");
    }
    public AdvertisementModel getAdvertisementById(int id) {
        throw new NotImplementedError("getAdvertisementById is not implemented");
    }
    public AdvertisementModel editAdvertisement(int id) {
        throw new NotImplementedError("editAdvertisement is not implemented");
    }
    public AdvertisementModel getAllAdvertisements() {
        throw new NotImplementedError("getAllAdvertisements is not implemented");
    }

    // ================= FAVORITES
    // TODO
    public boolean addFavoriteRestaurant(FavoriteRestaurantModel favoriteModel) {
       throw new NotImplementedError("addFavoriteRestaurant is not implemented");
    }
    public boolean addFavoriteMenuItem(FavoriteRestaurantModel favoriteModel) {
        throw new NotImplementedError("addFavoriteMenuItem is not implemented");
    }

    // TODO
    public FavoriteRestaurantModel getFavoriteRestaurantById(int id) {
        throw new NotImplementedError("getFavoriteRestaurantById is not implemented");
    }
    public FavoriteMenuItemModel getFavoriteMenuItemById(int id) {
        throw new NotImplementedError("getFavoriteMenuItemById is not implemented");
    }

    // TODO
    public List<FavoriteRestaurantModel> getAllFavoriteRestaurants() {
        throw new NotImplementedError("getAllFavoriteRestaurants is not implemented");
    }
    public List<FavoriteMenuItemModel> getAllFavoriteMenuItems() {
        throw new NotImplementedError("getAllFavoriteMenuItems is not implemented");
    }

    // TODO
    public FavoriteRestaurantModel removeFavoriteRestaurant(int id) {
        throw new NotImplementedError("removeFavoriteRestaurant is not implemented");
    }
    public FavoriteMenuItemModel removeFavoriteMenuItem(int id) {
        throw new NotImplementedError("removeFavoriteMenuItem is not implemented");
    }


}

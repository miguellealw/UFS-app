package com.example.ufs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ufs.data.model.AdvertisementModel;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.data.model.OrderModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.data.model.ReviewModel;
import com.example.ufs.data.model.UserModel;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

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

    // Advertisement
    private static final String AD_TABLE = "ADVERTISEMENT_TABLE";
    private static final String COLUMN_AD_ID = "id";
    private static final String COLUMN_AD_COMPANY_NAME = "company_name";
    private static final String COLUMN_AD_MESSAGE = "message";
    private static final String COLUMN_AD_USER_ID = "user_id";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "ufs.db", null, 1);
    }

    // will be called the first time db is accessed
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
            COLUMN_ORDER_TOTAL_PRICE + " TEXT NOT NULL, " +
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
            COLUMN_MENU_ITEM_PRICE + " TEXT NOT NULL, " +
            COLUMN_MENU_ITEM_RESTAURANT_ID + " INTEGER NOT NULL, " +

            // Foreign key relating menu_item and restaurant
            "FOREIGN KEY(id) REFERENCES " + RESTAURANT_TABLE + "(id)" +
        ")";


        // =============== REVIEW TABLE
        String createReviewTable = "CREATE TABLE " + REVIEW_TABLE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FAVORITE_RESTAURANT_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_FAVORITE_RESTAURANT_RESTAURANT_ID + " INTEGER NOT NULL, " +

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

        // if positive then insertion was successful
        // if negative then insertion was a failure
        return insert_status > 0;
    }

    public UserModel getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columns to return to client
        String[] returned_cols = {
            COLUMN_USER_ID,
            COLUMN_USER_FIRST_NAME,
            COLUMN_USER_LAST_NAME,
            COLUMN_USER_EMAIL,
            COLUMN_USER_UNIVERSITY_ID,
            COLUMN_USER_IS_STUDENT
        };

        // Condition of rows to select
        Cursor cursor = null;
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email + "", password + "" };

        // User Data
        String userFName = "";
        int userID = -1;
        boolean userIsStudent;

        try {
            // get data from db
            //Cursor query = db.query(returned_cols, selection, selectionArgs, null, null, null);
            cursor = db.rawQuery(
                "SELECT " + COLUMN_USER_FIRST_NAME + ", " + COLUMN_USER_ID + ", " + COLUMN_USER_IS_STUDENT + " FROM " + USER_TABLE + " WHERE email = ? AND password = ?",
                selectionArgs
            );

            // If user is found
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userFName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME));
                userID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                userIsStudent = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_STUDENT)));
                Log.i("===DBO getUser", "userID: " + userIsStudent);
            } else {
                // if no user is found
                return null;
            }

            return new UserModel(userID, userFName, userIsStudent);
        } finally {
            assert cursor != null;
            cursor.close();
        }
    }

    // Orders
    //  TODO:
    //    student orders - addOrder(), getStudentOrder(id), getAllUserOrders(), editOrder(id),
    //    restaurant orders - getAllRestaurantOrders(), getRestaurantOrder(id)
    // TODO: differentiate between an order a user made
    //  and an order a restaurant received
    // Student orders
    public OrderModel addOrder(OrderModel orderModel) { return null; }
    // get specific order a student has made
    public OrderModel getStudentOrder(int id) { return null; }
    // get all orders that a student made
    public List<OrderModel> getAllStudentOrders() { return null; }
    public OrderModel editOrder(int id) { return null; }

    // Restaurant orders
    // get specific order restaurant has received
    public OrderModel getRestaurantOrder(int id) { return null; }
    // get all orders a restaurant has received
    public List<OrderModel> getAllRestaurantOrders() { return null; }

    // Restaurants
    //  TODO: addRestaurant(), getRestaurant(id), getAllRestaurants()
    public RestaurantModel addRestaurant(RestaurantModel restaurantModel) { return null; }
    public RestaurantModel getRestaurant(int id) { return null; }
    public List<RestaurantModel> getAllRestaurants() { return null; }

    // Reviews
    //  TODO: addReview(), getReview(id), getAllReviews()
    public ReviewModel addReview(ReviewModel reviewModel) { return null; }
    public ReviewModel getReview(int id) { return null; }
    public List<ReviewModel> getAllReviews() { return null; }

    // MenuItems
    //  TODO: addMenuItem(), getMenuItem(id), getAllMenuItems(), removeMenuItem()
    public MenuItemModel addMenuItem(MenuItemModel menuItemModel) { return null; }
    public MenuItemModel getMenuItem(int id) { return null; }
    public List<MenuItemModel> getAllMenuItems() { return null; }

    // Advertisement
    //  TODO: addAdvertisement(), getAdvertisement()
    public AdvertisementModel addAdvertisement(AdvertisementModel advertisementModel) { return null; }
    public AdvertisementModel getAdvertisement(int id) { return null; }

    // TODO: Figure out how to do favorites for menu items and restaurants
    //  addFavorite(), getFavorite(id), getAllFavorites(), removeFavorite()
}

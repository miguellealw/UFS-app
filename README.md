# View Database Data in Client
1. Get a database client like [TablePlus](https://www.tableplus.io/) or [SQLite browser](https://sqlitebrowser.org/)
2. Open app and create a user or perform other DB interaction
3. Open Device File Controller in Android Studio
4. Go to `data > data > com.example.ufs > databases`
5. Right click on `databases` folder and click `Synchronize` to make sure the databse file is updated.
5. Right click on `ufs.db` file and `Save as`. Save to computer
6. Open db file in database client downloaded in step 1

## If database is updated
If data is added, edited or removed from the database through the app you will need to perform steps **2 - 6** above.

## If a database table is changed
If database schema is changed like table name, table column name or anything else do the following:
1. Go to `data > data > com.example.ufs > database` and **delete** the `ufs.db` file (note: this will delete the whole database including any users and other data)
2. Open app and create a user or perform other DB interaction
3. Go to `data > data > com.example.ufs > databases`
4. Right click on `databases` folder and click `Synchronize` to make sure the databse file is updated.
5. Right click on `ufs.db` file and `Save as`. Save to computer
6. Open db file in database client


---

# Add data to database with code
All of the methods to access data from database are in `DataBaseHelper.java`. You will need to create an instance of the database helper.

Example: if you want to create a user in the registration activity
```java
...
UserModel newUser = new UserModel("first name", "last name", "email" "university id", "password", 1);

// DatabaseHelper takes in the context 
DatabaseHelper dbo = new DatabaseHelper(Registration.this);
boolean u_success = db.addUser(newUser);

if(u_sucess) { // successful creation }
else { // failed creation }

...
```

Look through the `DataBaseHelper.java` file for the rest of the methods available.

---

# Get isLoggedIn boolean
Sometimes you need to check if user is logged in
```java
...

SP_LocalStorage sp = new SP_LocalStorage(<activity context here>);
boolean isLoggedIn = sp.getIsLoggedIn();
...

```

Example in MainActivity.java
```java
...

SP_LocalStorage sp = new SP_LocalStorage(MainActivity.this);
boolean isLoggedIn = sp.getIsLoggedIn();
...

```

---

# Get the ID of the logged in user
You will need to get the id of the logged in user to relate the user to other tables in the database like the restaurants or orders.
```java
...
SP_LocalStorage sp = new SP_LocalStorage(<context here>);
int loggedInUserId = sp.getLoggedInUserId();
...
```

Example: If in main activity or any activity
```java
...
// MainActivity should be replaced with the name of the activity you are in
SP_LocalStorage sp = new SP_LocalStorage(MainActivity.this);
int loggedInUserId = sp.getLoggedInUserId();
...
```

Example: If in fragment
```java
...
// Inside the onCreateView method

Context ctx = getActivity().getApplicationContext();
SP_LocalStorage sp = new SP_LocalStorage(ctx);
int loggedInUserId = sp.getLoggedInUserId();

...

```


# Database design
![db design](./ufs-db.jpg)

/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.model.User;

/**
 * UserDB is the sql lite database of user information
 * @author Mehgan Cook and Tony Zullo
 */
public class UserDB {
    /**Database version number*/
    public static final int DB_VERSION = 1;
    /**Database name*/
    public static final String DB_NAME = "User.db";
    /**User table name*/
    private static final String USER_TABLE = "User";
    /**database helper for easier access of the database*/
    private UserDBHelper mUserDBHelper;
    /**the sql lite database*/
    private SQLiteDatabase mSQLiteDatabase;

    /**Constructor ofr UserDB
     * @param context the context.
     * */
    public UserDB(Context context) {
        mUserDBHelper = new UserDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mUserDBHelper.getWritableDatabase();
    }


    /**
     * Inserts the user into the local sqlite table. Returns true if successful, false otherwise.
     * @param email is the user email
     * @return true or false
     */
    public boolean insertUser(String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    /**
     * Close the database
     * */

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /**
     * Delete all the data from the USER_TABLE
     */
    public void deleteUsers() {
        mSQLiteDatabase.delete(USER_TABLE, null, null);
    }


    /**
     * Returns the list of users from the local User table.
     * @return list
     */
    public List<User> getUsers() {

        String[] columns = {
                "email"
        };

        Cursor c = mSQLiteDatabase.query(
                USER_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<User> list = new ArrayList<>();
        for (int i=0; i<c.getCount(); i++) {
            String email = c.getString(0);
            User user = new User(null,email, null, null);
            list.add(user);
            c.moveToNext();
        }

        return list;
    }



/**
 * UserDB helper class
 * */
    class UserDBHelper extends SQLiteOpenHelper {
        /**create table sql statement*/
        private static final String CREATE_USER_SQL =
                "CREATE TABLE IF NOT EXISTS User "
                        + "(email TEXT PRIMARY KEY)";
     /**drop table sql statement*/
        private static final String DROP_USER_SQL =
                "DROP TABLE IF EXISTS User";
       /**
        * Constructor for UserDBHelper
        * @param context the context
        * @param factory sqllite cursor factory
        * @param name string name
        * @param version the version umber
        * */
        public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
         /**
          * onCreate
          * @param sqLiteDatabase the sql lite database
          * */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_USER_SQL);
        }

        /**
         * onUpgrade
         * @param sqLiteDatabase the sql lite database
         * @param i integer
         * @param i1 integer
         * */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_USER_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}

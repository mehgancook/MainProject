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
 * Created by Mehgan on 4/26/2016.
 */
public class UserDB {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "User.db";
    private static final String USER_TABLE = "User";

    private UserDBHelper mUserDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public UserDB(Context context) {
        mUserDBHelper = new UserDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mUserDBHelper.getWritableDatabase();
    }


    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * @param email
     * @return true or false
     */
    public boolean insertUser(String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /**
     * Delete all the data from the COURSE_TABLE
     */
    public void deleteUsers() {
        mSQLiteDatabase.delete(USER_TABLE, null, null);
    }


    /**
     * Returns the list of courses from the local Course table.
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




    class UserDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_USER_SQL =
                "CREATE TABLE IF NOT EXISTS User "
                        + "(email TEXT PRIMARY KEY)";

        private static final String DROP_USER_SQL =
                "DROP TABLE IF EXISTS User";

        public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_USER_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_USER_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}

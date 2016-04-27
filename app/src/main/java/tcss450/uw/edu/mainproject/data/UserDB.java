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
    private static final String COURSE_TABLE = "User";

    private CourseDBHelper mCourseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public UserDB(Context context) {
        mCourseDBHelper = new CourseDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mCourseDBHelper.getWritableDatabase();
    }


    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * @param username
     * @param email
     * @param password
     * @param userid
     * @return true or false
     */
    public boolean insertCourse(String username, String email, String password, String userid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("userid", userid);

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
        mSQLiteDatabase.delete(COURSE_TABLE, null, null);
    }


    /**
     * Returns the list of courses from the local Course table.
     * @return list
     */
    public List<User> getUsers() {

        String[] columns = {
                "username", "email", "password", "userid"
        };

        Cursor c = mSQLiteDatabase.query(
                COURSE_TABLE,  // The table to query
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
            String username = c.getString(0);
            String email = c.getString(1);
            String password = c.getString(2);
            String userid = c.getString(3);
            User user = new User(username, email, password, userid);
            list.add(user);
            c.moveToNext();
        }

        return list;
    }




    class CourseDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_USER_SQL =
                "CREATE TABLE IF NOT EXISTS User "
                        + "(username TEXT, email TEXT, password TEXT, userid INT PRIMARY KEY)";

        private static final String DROP_USER_SQL =
                "DROP TABLE IF EXISTS User";

        public CourseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

package Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pp2019.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES_GAME =
            "CREATE TABLE " + DatabaseMaster.Game.TABLE_NAME + " (" +
                    DatabaseMaster.Game._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Game.COLUMN_NAME_GAMENAME + " TEXT," +
                    DatabaseMaster.Game.COLUMN_NAME_GAMEYEAR + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_GAME =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Game.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_COMMENT =
            "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                    DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Comments.COLUMN_NAME_GAMENAME + " TEXT," +
                    DatabaseMaster.Comments.COLUMN_NAME_COMMENT + " TEXT," +
                    DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_COMMENT =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Comments.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + DatabaseMaster.User.TABLE_NAME + " (" +
                    DatabaseMaster.User._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.User.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseMaster.User.COLUMN_NAME_PASSWORD + " TEXT," +
                    DatabaseMaster.User.COLUMN_NAME_USERTYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + DatabaseMaster.User.TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_USER);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_COMMENT);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_USER);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_COMMENT);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_GAME);
        onCreate(sqLiteDatabase);
    }

    public long registerUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseMaster.User.COLUMN_NAME_USERNAME, username);
        contentValues.put(DatabaseMaster.User.COLUMN_NAME_PASSWORD, password);

        long val = db.insert(DatabaseMaster.User.TABLE_NAME, null, contentValues);
        return val;
    }

    public int loginUser(String username, String password) {
        String[] projection = {
                DatabaseMaster.User._ID,
                DatabaseMaster.User.COLUMN_NAME_USERNAME,
                DatabaseMaster.User.COLUMN_NAME_PASSWORD
        };

        SQLiteDatabase db = getReadableDatabase();

        //Selection Criteria
        String selection = DatabaseMaster.User.COLUMN_NAME_USERNAME + " LIKE ?";

        String[] selectionArgs = {username};
        String sortOrder = DatabaseMaster.User._ID + " ASC";

        Cursor cursor = db.query(DatabaseMaster.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        String uName = "", pWord = "";

        while(cursor.moveToNext()) {
            uName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.User.COLUMN_NAME_USERNAME));
            pWord = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.User.COLUMN_NAME_PASSWORD));
        }
        cursor.close();

        if (uName == "" || pWord == "") {
            return 0;
        }
        if (uName.equals(username) && pWord.equals(password)) {
            if (uName.equals("admin")) {
                return 2;
            }
            else {
                return 1;
            }
        }
        else {
            return 3;
        }
    }

    public boolean addGame(String name, int year) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Game.COLUMN_NAME_GAMENAME, name);
        values.put(DatabaseMaster.Game.COLUMN_NAME_GAMEYEAR, year);

        long val = db.insert(DatabaseMaster.Game.TABLE_NAME, null, values);

        if (val > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public List viewGames() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                DatabaseMaster.Game._ID,
                DatabaseMaster.Game.COLUMN_NAME_GAMENAME,
                DatabaseMaster.Game.COLUMN_NAME_GAMEYEAR
        };

        String sortOrder = DatabaseMaster.Game._ID + " DESC";

        Cursor cursor = db.query(
                DatabaseMaster.Game.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List gameList = new ArrayList<>();
        while(cursor.moveToNext()) {
            DatabaseMaster.Game game = new DatabaseMaster.Game();
            game.setGameName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Game.COLUMN_NAME_GAMENAME)));
            game.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Game.COLUMN_NAME_GAMEYEAR))));

            gameList.add(game);
        }
        cursor.close();
        db.close();

        return gameList;
    }

    public long insertComments(String gName, String comments, int rating) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.COLUMN_NAME_GAMENAME, gName);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_COMMENT, comments);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING, rating);

        long val = db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
        return val;
    }

    public List viewComments(String gameName) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                DatabaseMaster.Comments._ID,
                DatabaseMaster.Comments.COLUMN_NAME_GAMENAME,
                DatabaseMaster.Comments.COLUMN_NAME_COMMENT,
                DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING
        };
        String selection = DatabaseMaster.Comments.COLUMN_NAME_GAMENAME + " LIKE ?";

        String[] selectionArgs = {gameName};
        String sortOrder = DatabaseMaster.Comments._ID + " DESC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List commentList = new ArrayList<>();

        while(cursor.moveToNext()) {
            DatabaseMaster.Comments comment = new DatabaseMaster.Comments();
            comment.setComment(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_NAME_COMMENT)));

            commentList.add(comment);
        }
        cursor.close();
        db.close();

        return commentList;
    }

    public float calcAvgRating(String gameName) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                DatabaseMaster.Comments._ID,
                DatabaseMaster.Comments.COLUMN_NAME_GAMENAME,
                DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING,
                DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING
        };

        String selection = DatabaseMaster.Comments.COLUMN_NAME_GAMENAME + " LIKE ?";
        String[] selectionArgs = {gameName};

        String sortOrder = DatabaseMaster.Comments._ID + " DESC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        float Total = 0;
        int count = 0;

        while(cursor.moveToNext()) {
            int rate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_NAME_COMMENTRATING));
            Total += rate;
            count++;
        }
        cursor.close();
        db.close();

        return (float)(Total/count);
    }
}

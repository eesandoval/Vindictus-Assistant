package com.rayziken.vindictusassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.rayziken.vindictusassistant/databases/";
    private static final String DB_NAME = "VindictusAssistant.db";
    private SQLiteDatabase DB;
    private final Context myContext;
    private final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            this.close();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void forceCreateDataBase() throws IOException {
        this.getReadableDatabase();
        this.close();
        try {
            this.close();
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            //String myPath = myContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e) {
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        DB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(DB != null)
            DB.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            forceCreateDataBase();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Cursor getColumnsFromTable(String table, String columns[]) {
        Cursor cursor = DB.query(table, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getItemFromTable(String table, String name) {
        String sql = "SELECT * FROM " + table + " WHERE name= ?";
        Cursor cursor = DB.rawQuery(sql, new String[] {name});
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getCraftFromTable(int craftId) {
        String sql = "SELECT * FROM Craft WHERE craft_id= " + craftId;
        Cursor cursor = DB.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor;
    }
}

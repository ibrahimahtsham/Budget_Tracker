package com.siamax.budgettracker;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    Context context;
    public static final String DATABASE_NAME = "budget_tracker.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TRANSACTIONS_TABLE = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "transaction_id";
    public static final String COLUMN_TRANSACTION_USER_ID_AK = "user_id_ak";
    public static final String COLUMN_TRANSACTION_LABEL = "transaction_label";
    public static final String COLUMN_TRANSACTION_AMOUNT = "transaction_amount";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "transaction_description";

    public static final String USER_TABLE = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTransactionsTable = "CREATE TABLE " + TRANSACTIONS_TABLE +
                " (" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                COLUMN_TRANSACTION_USER_ID_AK + " INTEGER, " +
                COLUMN_TRANSACTION_LABEL + " TEXT, " +
                COLUMN_TRANSACTION_AMOUNT + " DOUBLE, " +
                COLUMN_TRANSACTION_DESCRIPTION + " TEXT);";
        db.execSQL(queryCreateTransactionsTable);

        String queryCreateUserTable = "CREATE TABLE " + USER_TABLE +
                " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT);";
        db.execSQL(queryCreateUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

    }

    void addTransaction(int user_id_ak, String transaction_label,
                        Double transaction_amount, String transaction_description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TRANSACTION_USER_ID_AK, user_id_ak);
        contentValues.put(COLUMN_TRANSACTION_LABEL, transaction_label);
        contentValues.put(COLUMN_TRANSACTION_AMOUNT, transaction_amount);
        contentValues.put(COLUMN_TRANSACTION_DESCRIPTION, transaction_description);

        long result = db.insert(TRANSACTIONS_TABLE, null, contentValues);
        if (result == -1) {
            System.out.println(result);
            Toast.makeText(context, "Failed to put transaction in database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
        }
    }

    void updateTransaction(int id, String transaction_label,
                        Double transaction_amount, String transaction_description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TRANSACTION_LABEL, transaction_label);
        contentValues.put(COLUMN_TRANSACTION_AMOUNT, transaction_amount);
        contentValues.put(COLUMN_TRANSACTION_DESCRIPTION, transaction_description);

        long result = db.update(TRANSACTIONS_TABLE, contentValues,
                COLUMN_TRANSACTION_ID + "=?", new String[]{id+""});
        if (result == -1) {
            System.out.println(result);
            Toast.makeText(context, "Failed to update transaction in database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    void addUser(String user_name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER_NAME, user_name);

        long result = db.insert(USER_TABLE, null, contentValues);
        if (result == -1) {
            System.out.println(result);
            Toast.makeText(context, "Failed to put user in database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added user to database", Toast.LENGTH_SHORT).show();
        }

    }

    boolean checkIfUserExistInDB(){
        SharedPreferences preferences = context.getSharedPreferences("PREFS", MODE_PRIVATE);
        String pUser = preferences.getString("userName", "");

        SQLiteDatabase db = this.getReadableDatabase();

        String queryCheckIfUserInDB = "SELECT " + COLUMN_USER_NAME + " FROM " + USER_TABLE +
                " WHERE " + COLUMN_USER_NAME + " = " + pUser + ";";

        Cursor resultSet = db.rawQuery(queryCheckIfUserInDB, null);

        if (resultSet.getCount()>0)
            return true;
        return false;
    }

    int getUserIDForAK(){
        SharedPreferences preferences = context.getSharedPreferences("PREFS", MODE_PRIVATE);
        String pUser = preferences.getString("userName", "");

        SQLiteDatabase db = this.getReadableDatabase();

        String queryGetUserIDForAK = "SELECT " + COLUMN_USER_ID + " FROM " + USER_TABLE +
                " WHERE " + COLUMN_USER_NAME + " = " + pUser + ";";

        Cursor resultSet = db.rawQuery(queryGetUserIDForAK, null);
        resultSet.moveToFirst();

        return resultSet.getInt(resultSet.getColumnIndexOrThrow(COLUMN_USER_ID));
    }

    Cursor readAllDataFromTransactionsTable(){

        String queryReadDataFromTransactionsTable = "SELECT * FROM " + TRANSACTIONS_TABLE + " WHERE " + COLUMN_TRANSACTION_USER_ID_AK + " = " + getUserIDForAK() + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(queryReadDataFromTransactionsTable, null);
        }
        return cursor;
    }

    String getLabelForUpdate(int id){
        String queryGetLabelForUpdate = "SELECT "+ COLUMN_TRANSACTION_LABEL +" FROM " + TRANSACTIONS_TABLE + " WHERE " + COLUMN_TRANSACTION_ID + " = " + id + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultSet = db.rawQuery(queryGetLabelForUpdate, null);
        resultSet.moveToFirst();

        return resultSet.getString(resultSet.getColumnIndexOrThrow(COLUMN_TRANSACTION_LABEL)).toString();
    }
    int getAmountForUpdate(int id){
        String queryGetAmountForUpdate = "SELECT "+ COLUMN_TRANSACTION_AMOUNT +" FROM " + TRANSACTIONS_TABLE + " WHERE " + COLUMN_TRANSACTION_ID + " = " + id + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultSet = db.rawQuery(queryGetAmountForUpdate, null);
        resultSet.moveToFirst();

        return resultSet.getInt(resultSet.getColumnIndexOrThrow(COLUMN_TRANSACTION_AMOUNT));
    }
    String getDescriptionForUpdate(int id){
        String queryGetDescriptionForUpdate = "SELECT "+ COLUMN_TRANSACTION_DESCRIPTION +" FROM " + TRANSACTIONS_TABLE + " WHERE " + COLUMN_TRANSACTION_ID + " = " + id + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultSet = db.rawQuery(queryGetDescriptionForUpdate, null);
        resultSet.moveToFirst();

        return resultSet.getString(resultSet.getColumnIndexOrThrow(COLUMN_TRANSACTION_DESCRIPTION)).toString();
    }

}

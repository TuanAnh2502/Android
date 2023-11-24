package com.example.dembuocchan;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dembuocchan.KeHoach.KeHoach;

import java.util.ArrayList;
import java.util.List;

public class KeHoachHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kehoach_db";
    private static final int DATABASE_VERSION = 1;

    // table plans
    private static final String TABLE_PLANS = "plans";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RESOURCE_ID = "resource_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_TIME = "time";

    public KeHoachHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PLANS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_RESOURCE_ID + " INTEGER," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_LEVEL + " TEXT," +
                COLUMN_TIME + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  Xử lý khi có sự thay đổi phiên bản CSDL
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANS);
        onCreate(db);
    }

    // Lay danh sach ke hoach
    public List<KeHoach> getAllPlans() {
        List<KeHoach> planList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLANS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") int resourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String level = cursor.getString(cursor.getColumnIndex(COLUMN_LEVEL));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));

                KeHoach plan = new KeHoach(id, resourceId, title, level, time);
                planList.add(plan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return planList;
    }

    // Them ke hoach
    public long addPlan(KeHoach plan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE_ID, plan.getResourceId());
        values.put(COLUMN_TITLE, plan.getTitle());
        values.put(COLUMN_LEVEL, plan.getLevel());
        values.put(COLUMN_TIME, plan.getTime());

        long id = db.insert(TABLE_PLANS, null, values);
        db.close();

        return id;
    }
    // Xoa ke hoach
    public void deletePlan(KeHoach plan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANS, COLUMN_ID + " = ?", new String[]{String.valueOf(plan.getId())});
        db.close();
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANS, null, null);
        db.close();
    }

}

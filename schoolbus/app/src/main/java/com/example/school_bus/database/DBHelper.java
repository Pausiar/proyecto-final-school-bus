package com.example.school_bus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // ===== DATOS BD =====
    private static final String DB_NAME = "schoolbus.db";
    private static final int DB_VERSION = 2;

    // ===== TABLAS =====
    public static final String TABLE_USERS = "users";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_BUSES = "buses";
    public static final String TABLE_STOPS = "stops";
    public static final String TABLE_NOTIFICATIONS = "notifications";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // ================= CREACIÓN BD =======================
    @Override
    public void onCreate(SQLiteDatabase db) {

        // ---- USUARIOS ----
        db.execSQL(
                "CREATE TABLE " + TABLE_USERS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "surname TEXT NOT NULL," +
                        "email TEXT UNIQUE NOT NULL," +
                        "password TEXT NOT NULL," +
                        "role TEXT NOT NULL)"
        );

        // ---- AUTOBUSES ----
        db.execSQL(
                "CREATE TABLE " + TABLE_BUSES + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "plate TEXT NOT NULL," +
                        "driver_name TEXT NOT NULL)"
        );

        // ---- PARADAS ----
        db.execSQL(
                "CREATE TABLE " + TABLE_STOPS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL)"
        );

        // ---- ESTUDIANTES ----
        db.execSQL(
                "CREATE TABLE " + TABLE_STUDENTS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "user_id INTEGER," +
                        "bus_id INTEGER," +
                        "stop_id INTEGER," +
                        "FOREIGN KEY(user_id) REFERENCES users(id)," +
                        "FOREIGN KEY(bus_id) REFERENCES buses(id)," +
                        "FOREIGN KEY(stop_id) REFERENCES stops(id))"
        );

        // ---- NOTIFICACIONES ----
        db.execSQL(
                "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT NOT NULL," +
                        "message TEXT NOT NULL," +
                        "date TEXT)"
        );
    }


    // ================= ACTUALIZACIÓN =====================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ================= USUARIOS ==========================

    public long insertUser(String name, String surname, String email, String password, String role) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("surname", surname);
        values.put("email", email);
        values.put("password", password);
        values.put("role", role);
        return db.insert(TABLE_USERS, null, values);
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );
        boolean ok = c.moveToFirst();
        c.close();
        return ok;
    }

    public Cursor getUserByEmail(String email) {
        // Obtener usuario por email
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM users WHERE email = ?",
                new String[]{email}
        );
    }

    // ================= AUTOBUSES =========================
    public long insertBus(String plate, String driverName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("plate", plate);
        values.put("driver_name", driverName);
        return db.insert(TABLE_BUSES, null, values);
    }

    public Cursor getAllBuses() {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM buses",
                null
        );
    }

    // ================= PARADAS ===========================
    public long insertStop(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        return db.insert(TABLE_STOPS, null, values);
    }

    public Cursor getAllStops() {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM stops",
                null
        );
    }

    // ================= ESTUDIANTES =======================
    public long insertStudent(String name, int userId, int busId, int stopId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("user_id", userId);
        values.put("bus_id", busId);
        values.put("stop_id", stopId);
        return db.insert(TABLE_STUDENTS, null, values);
    }

    public Cursor getAllStudents() {
        return getReadableDatabase().rawQuery(
                "SELECT s.id, s.name, b.plate, st.name AS stop " +
                        "FROM students s " +
                        "LEFT JOIN buses b ON s.bus_id=b.id " +
                        "LEFT JOIN stops st ON s.stop_id=st.id",
                null
        );
    }

    // ================= NOTIFICACIONES ====================
    public long insertNotification(String title, String message, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("message", message);
        values.put("date", date);
        return db.insert(TABLE_NOTIFICATIONS, null, values);
    }

    public Cursor getAllNotifications() {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM notifications ORDER BY id DESC",
                null
        );
    }
}
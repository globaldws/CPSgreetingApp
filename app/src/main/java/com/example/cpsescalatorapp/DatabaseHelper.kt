package com.example.cpsescalatorapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                FIRST_NAME_COL + " TEXT," +
                LAST_NAME_COL + " TEXT," +
                EMAIL_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addPerson(firstName: String, lastName: String, email: String) {
        val values = ContentValues()
        values.put(FIRST_NAME_COL, firstName)
        values.put(LAST_NAME_COL, lastName)
        values.put(EMAIL_COL, email)
        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Method to get all data from the database
    fun getAllPersons(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private const val DATABASE_NAME = "PERSONS_LIST"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "gfg_table"
        const val ID_COL = "id"
        const val FIRST_NAME_COL = "first_name"
        const val LAST_NAME_COL = "last_name"
        const val EMAIL_COL = "email"
    }
}

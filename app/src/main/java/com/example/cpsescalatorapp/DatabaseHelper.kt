package com.example.cpsescalatorapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.UsersTable.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UsersTable.TABLE_NAME)
        onCreate(db)
    }

    fun addPerson(firstName: String, lastName: String, email: String, selectedOptions: String) {
        val values = ContentValues().apply {
            put(DatabaseContract.UsersTable.COLUMN_FIRST_NAME, firstName)
            put(DatabaseContract.UsersTable.COLUMN_LAST_NAME, lastName)
            put(DatabaseContract.UsersTable.COLUMN_EMAIL, email)
            put(DatabaseContract.UsersTable.COLUMN_SELECTED_OPTIONS, selectedOptions)
        }
        val db = writableDatabase

        db.insert(DatabaseContract.UsersTable.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPersons(): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM ${DatabaseContract.UsersTable.TABLE_NAME}", null)
    }

    companion object {
        private const val DATABASE_NAME = "user_data.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "gfg_table"
        const val ID_COL = "id"
        const val FIRST_NAME_COL = "first_name"
        const val LAST_NAME_COL = "last_name"
        const val EMAIL_COL = "email"
        const val COLUMN_SELECTED_OPTIONS = "selected_options"
    }
}

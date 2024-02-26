package com.example.cpsescalatorapp

object DatabaseContract {
    const val DATABASE_NAME = "user_data.db"

    object UsersTable {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_EMAIL = "email"

        const val CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL" +
                ")"
    }
}

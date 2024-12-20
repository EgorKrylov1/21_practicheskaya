package com.example.a21practicheskaya


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, SCHEMA) {

    companion object {
        const val DATABASE_NAME = "userstore.db" // Название базы данных
        const val SCHEMA = 1 // Версия базы данных
        const val TABLE = "users" // Название таблицы
        const val COLUMN_ID = "_id" // Столбец ID
        const val COLUMN_NAME = "name" // Столбец имени
        const val COLUMN_YEAR = "year" // Столбец года
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом создании базы данных
        db.execSQL("CREATE TABLE $TABLE ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_YEAR INTEGER);")
        // Добавляем начальные данные
        db.execSQL("INSERT INTO $TABLE ($COLUMN_NAME, $COLUMN_YEAR) VALUES ('Tom Smith', 1981);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Удаляем старую таблицу и создаем новую при обновлении схемы
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }
}
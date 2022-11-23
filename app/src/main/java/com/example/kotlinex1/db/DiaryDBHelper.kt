package com.example.kotlinex1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kotlinex1.static.DBName

class DiaryDBHelper(private val context: Context) : SQLiteOpenHelper(context, DBName.DB_NAME, null, 2) {

    companion object {
        const val TABLE_NAME = "diary"
        const val COL_ID = "_id"
        const val COL_image = "image"
        const val COL_TITLE = "title"
        const val COL_CONTENT = "content"
        const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table if not exists $TABLE_NAME(" +
                "$COL_ID integer not null primary key autoincrement," +
                "$COL_image text," +
                "$COL_TITLE text not null," +
                "$COL_CONTENT text not null," +
                "$COL_DATE integer not null" +
                ");"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME;"
        db?.execSQL(sql)
        onCreate(db)
    }

}
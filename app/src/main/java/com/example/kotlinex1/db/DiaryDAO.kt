package com.example.kotlinex1.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.kotlinex1.nav_Fragments.diary.DiaryVO
import com.example.kotlinex1.static.DBName
import java.util.*
import kotlin.collections.ArrayList

class DiaryDAO private constructor(private val context: Context) {

    companion object {
        fun newInstance(context: Context) = DiaryDAO(context)
    }

    fun selectAll(): ArrayList<DiaryVO> {
        val db = DiaryDBHelper(context).writableDatabase
        val sql = "select * from ${DiaryDBHelper.TABLE_NAME} order by date desc;"
        val cursor = db.rawQuery(sql, null)

        val result: ArrayList<DiaryVO> = arrayListOf()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val image = cursor.getString(1)
            val title = cursor.getString(2) ?: continue
            val content = cursor.getString(3) ?: continue
            val date = cursor.getLong(4)
            val dao = DiaryVO(id, image, title, content, date)
            result.add(dao)
        }
        db.close()

        Log.e("select", Arrays.toString(result.toTypedArray()))

        return result
    }

    fun select(_id: Int): DiaryVO? {
        val db = DiaryDBHelper(context).writableDatabase
        val sql = "select * from ${DiaryDBHelper.TABLE_NAME} where _id = ?;"
        val cursor = db.rawQuery(sql, arrayOf(_id.toString()))

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val image = cursor.getString(1)
            val title = cursor.getString(2) ?: continue
            val content = cursor.getString(3) ?: continue
            val date = cursor.getLong(4)
            db.close()
            return DiaryVO(id, image, title, content, date)
        }
        db.close()

        return null
    }

    fun insert(diaryVO: DiaryVO) {
        val db = DiaryDBHelper(context).writableDatabase

        val contentValues = ContentValues()
        contentValues.put("image", diaryVO.image)
        contentValues.put("title", diaryVO.title)
        contentValues.put("content", diaryVO.content)
        contentValues.put("date", diaryVO.date)

        db.insert(DiaryDBHelper.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun delete(_id: Int) {
        val db = DiaryDBHelper(context).writableDatabase
        val sql = "delete from ${DiaryDBHelper.TABLE_NAME} where _id = ?;"
        db.execSQL(sql, arrayOf(_id.toString()))
    }

    fun update(diaryVO: DiaryVO) {
        val db = DiaryDBHelper(context).writableDatabase
        val sql = "update ${DiaryDBHelper.TABLE_NAME} set image = ?, title = ?, content = ? where _id = ?;"
        db.execSQL(sql, arrayOf(diaryVO.image, diaryVO.title, diaryVO.content, diaryVO._id))
        db.close()
    }

}
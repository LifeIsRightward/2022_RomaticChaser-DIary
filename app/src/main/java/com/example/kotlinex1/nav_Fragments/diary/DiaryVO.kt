package com.example.kotlinex1.nav_Fragments.diary

import com.google.gson.Gson

data class DiaryVO(
    val _id: Int,
    var image: String?,
    var title: String,
    var content: String,
    val date: Long
): Comparable<DiaryVO> {//gson

    companion object {

        fun convertFromJson(json: String): DiaryVO {
            return Gson().fromJson(json, DiaryVO::class.java)
        }

    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun compareTo(other: DiaryVO): Int {
        return title.split(" ").last().toInt() - other.title.split(" ").last().toInt()
    }


}
package com.example.kotlinex1.Fragments.diary

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.RowCardTemplateBinding

class DiaryViewHolder(private val binding: RowCardTemplateBinding) : RecyclerView.ViewHolder(binding.root) {

    var onCardClickListener: (() -> Unit)? = null
    var onAction1ClickListener: ((View) -> Unit)? = null
    var onAction2ClickListener: ((View) -> Unit)? = null

    fun bind(data: DiaryVO) {
        binding.imgRowTemp.setImageResource(data.image)

        binding.txtRowTempTitle.text = data.title
        binding.txtRowTempContent.text = data.content

        binding.cardRowTemplate.setOnClickListener {
            onCardClickListener?.let { it() }
        }

        binding.btnRowTempAction1.setOnClickListener { v ->
            onAction1ClickListener?.let { it(v) }
        }

        binding.btnRowTempAction2.setOnClickListener { v ->
            onAction2ClickListener?.let { it(v) }
        }
    }

}
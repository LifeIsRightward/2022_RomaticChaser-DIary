package com.example.kotlinex1.nav_Fragments.diary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.databinding.RowCardTemplateBinding

class DiaryViewHolder(private val binding: RowCardTemplateBinding) : RecyclerView.ViewHolder(binding.root) {

    var onCardClickListener: (() -> Unit)? = null
    var onAction1ClickListener: ((View) -> Unit)? = null
    var onAction2ClickListener: ((View) -> Unit)? = null

    fun bind(data: DiaryVO) {
        if (data.image != 0) {
            binding.imgRowTemp.setImageResource(data.image)
        } else {
            binding.imgRowTemp.visibility = View.GONE
        }

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
package com.example.kotlinex1.nav_Fragments.diary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.RowCardTemplateBinding
import com.example.kotlinex1.tool.ImageManager

class DiaryViewHolder(private val binding: RowCardTemplateBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val VIEW_TYPE = R.layout.row_card_template
    }

    var onCardClickListener: (() -> Unit)? = null
    var onCardLongClickListener: (() -> Unit)? = null

    fun bind(data: DiaryVO) {
        if (data.image != null) {
            binding.imgRowTemp.setImageBitmap(ImageManager(binding.root.context).read(data.image!!))
            binding.imgRowTemp.visibility = View.VISIBLE
        } else {
            binding.imgRowTemp.visibility = View.GONE
        }

        binding.txtRowTempTitle.text = data.title
        binding.txtRowTempContent.text = data.content

        binding.cardRowTemplate.setOnClickListener {
            onCardClickListener?.let { it() }
        }

        binding.cardRowTemplate.setOnLongClickListener {
            onCardLongClickListener?.let { it() }
            true
        }
    }

}
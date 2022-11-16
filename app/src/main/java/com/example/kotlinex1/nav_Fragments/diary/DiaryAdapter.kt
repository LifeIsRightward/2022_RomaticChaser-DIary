package com.example.kotlinex1.nav_Fragments.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.RowCardTemplateBinding


class DiaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<DiaryVO>()
    private var layoutType = 0

    var onCardClickListener: ((position: Int) -> Unit)? = null
    var onAction1ClickListener: ((View, position: Int) -> Unit)? = null
    var onAction2ClickListener: ((View, position: Int) -> Unit)? = null

    // ViewHolder = Row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiaryViewHolder(
            RowCardTemplateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    // RecyclerView
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DiaryViewHolder) {
            holder.bind(data[position])

            holder.onCardClickListener = {
                onCardClickListener?.let { it (position) }
            }
            holder.onAction1ClickListener = { v ->
                onAction1ClickListener?.let { it(v, position) }
            }
            holder.onAction2ClickListener = { v ->
                onAction2ClickListener?.let { it(v, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }//리사이클러 뷰 에서 로우를 몇개를 보여줄지. ㅇㅇ

    // 어떤 뷰홀더를 쓸거냐
    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return R.layout.row_card_template
    }


}